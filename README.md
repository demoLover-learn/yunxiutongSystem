# 云修通 · 上门维修工单调度系统

面向上门维修场景的工单调度平台，覆盖 **管理员端 / 用户端 / 工人端** 三套角色的完整业务闭环：用户下单 → 管理员派单或工人抢单 → 上门服务 → 完成 → 评价。内置基于 **Spring AI + pgvector** 的智能客服与平台规则知识库。

- 后端：Spring Boot 3.5.14 + MyBatis + MySQL 8 + Redis + JWT
- AI：Spring AI 1.x（OpenAI 兼容协议）+ Tika + TokenTextSplitter + PgVectorStore
- 部署：Dockerfile + docker-compose（MySQL / Redis / App）

---

## 目录

- [功能概览](#功能概览)
- [技术栈](#技术栈)
- [系统架构](#系统架构)
- [目录结构](#目录结构)
- [数据库设计](#数据库设计)
- [核心设计](#核心设计)
- [接口清单](#接口清单)
- [快速开始](#快速开始)
- [配置说明](#配置说明)
- [常见问题](#常见问题)
- [后续规划](#后续规划)

---

## 功能概览

### 用户端

| 模块 | 能力 |
|---|---|
| 账号 | 注册、登录、退出（JWT） |
| 服务浏览 | 服务分类、服务项目详情 |
| 订单 | 下单、支付、取消、订单列表/详情、评价 |
| 地址 | 上门地址增删改查 |
| 个人中心 | 资料修改 |
| AI 客服 | 平台规则问答 + 查询本人订单（Function Calling） |

### 工人端

| 模块 | 能力 |
|---|---|
| 账号 | 注册、登录、退出 |
| 接单 | 抢单（Redisson 互斥锁）、待接单列表 |
| 履约 | 开始服务、完成服务、订单列表/详情 |
| 工作台 | 收益与接单统计 |
| 个人中心 | 资料修改、接单状态（接单中/休息中） |

### 管理端

| 模块 | 能力 |
|---|---|
| 账号 | 登录、退出 |
| 订单管理 | 订单列表/详情、派单、取消订单 |
| 服务管理 | 分类与项目的增删改查、上下架 |
| 人员管理 | 用户、工人列表与状态管理、可用工人查询 |
| 评价管理 | 删除违规评价 |
| 数据统计 | 订单量、状态分布、工人排行 |
| 知识库 | 上传平台规则文档（docx/pdf/txt/html），供 AI 客服检索 |

---

## 技术栈

| 分类 | 技术 |
|---|---|
| 语言 / 运行时 | Java 17 |
| 框架 | Spring Boot 3.5.14、Spring MVC、MyBatis 3 |
| 数据库 | MySQL 8（业务）、PostgreSQL + pgvector（向量） |
| 缓存 / 锁 | Redis、Redisson（分布式锁） |
| 鉴权 | JWT（管理员 / 用户 / 工人三套密钥）+ 拦截器 + ThreadLocal |
| AI | Spring AI：ChatClient、Function Calling、QuestionAnswerAdvisor、MessageWindowChatMemory、TikaDocumentReader、TokenTextSplitter、PgVectorStore |
| 文档 | Knife4j（OpenAPI 3） |
| 部署 | Docker、docker-compose、Maven |

---

## 系统架构

```
┌─────────────────────────────────────────────────────────────┐
│  前端：管理端 / 用户端 / 工人端（独立部署，通过 REST 调用）      │
└───────────────────────────┬─────────────────────────────────┘
                            │  HTTP + JWT（三套 Token）
┌───────────────────────────▼─────────────────────────────────┐
│  Controller 层  admin / user / worker / ai / common          │
│    拦截器：JWT 校验 → 角色隔离 → BaseContext(ThreadLocal)      │
├─────────────────────────────────────────────────────────────┤
│  Service 层                                                  │
│    状态机（7 个流转入口）+ CAS 更新 + Redisson 锁              │
│    超时工单自动取消（@Scheduled）                              │
├─────────────────────────────────────────────────────────────┤
│  Mapper 层（MyBatis XML）                                     │
├──────────────┬──────────────────────┬───────────────────────┤
│  MySQL 8     │  Redis               │  PostgreSQL + pgvector │
│  业务数据     │  登录态 / 缓存        │  平台规则知识库向量      │
└──────────────┴──────────────────────┴───────────────────────┘
                            │
                  ┌─────────▼──────────┐
                  │  Spring AI ChatClient│
                  │  + Function Calling  │
                  │  + RAG 检索          │
                  └─────────┬──────────┘
                            │ OpenAI 兼容协议
                  ┌─────────▼──────────────────────┐
                  │  LLM：deepseek-flash            │
                  │  Embedding：qwen3.7-text-embedding│
                  └────────────────────────────────┘
```

---

## 目录结构

```
yunxiutongDispatchSystem
├── init/
│   └── yunxiutong.sql                 # 建表 + 初始化数据（10 张表、193 条 INSERT）
├── src/main/java/org/example/
│   ├── controller/
│   │   ├── admin/                     # 管理端 9 个 Controller
│   │   ├── user/                      # 用户端 7 个
│   │   ├── worker/                    # 工人端 7 个
│   │   ├── ai/                        # AI 客服（用户问答 + 知识库上传）
│   │   └── common/                    # 通用（文件上传）
│   ├── service/                       # 业务逻辑（Impl 下为实现）
│   ├── mapper/                        # MyBatis Mapper
│   ├── entity/ dto/ vo/               # 数据模型
│   ├── configration/                  # 配置：AI、向量库、安全、拦截器
│   ├── Intercepter/                   # JWT 拦截器
│   ├── aitools/                       # Function Calling 工具 + RAG 入库
│   ├── context/                       # BaseContext（ThreadLocal 登录态）
│   ├── handler/                       # 全局异常处理
│   └── util/ properties/ Result/      # 工具类、配置属性、统一返回
├── src/main/resources/
│   ├── application.yml                # 主配置（数据源、AI、JWT）
│   ├── application-dev.yml            # 本地开发配置（已被 .gitignore 忽略）
│   └── mapper/                        # MyBatis XML
├── docker-compose.yml                 # MySQL + Redis + App
├── Dockerfile
└── pom.xml
```

---

## 数据库设计

业务库 `yunxiutong`，共 10 张表：

| 表 | 说明 |
|---|---|
| `user` | 用户账号与资料 |
| `user_address` | 用户地址 |
| `worker` | 工人账号、接单状态、评分 |
| `employee` | 管理员/员工账号 |
| `service_category` | 服务分类 |
| `service_item` | 服务项目（价格、时长、上下架） |
| `service_order` | 工单主表（状态、金额、预约时间、工人） |
| `service_order_log` | 工单状态变更日志（操作人、原/新状态） |
| `service_order_comment` | 订单评价 |
| `grab_order_record` | 抢单记录（谁抢了哪一单、结果） |

初始化：`init/yunxiutong.sql` 在 MySQL 容器首次启动时自动执行（compose 已挂载到 `/docker-entrypoint-initdb.d`）。

---

## 核心设计

### 1. 工单状态机 + CAS 更新

派单、抢单、开始服务、完成服务、用户取消、管理员取消、超时取消 **7 个流转入口**全部收口到 `OrderStatusServiceImpl.transition`，合法迁移只有下面几条，非法迁移直接拒绝：

| 迁移 | 触发者 | 说明 |
| --- | --- | --- |
| `0 → 1` | 工人抢单 / 管理员派单 | 仅**已支付**工单可抢、可派 |
| `1 → 2` | 工人开始服务 | 同时把工人置为「服务中」，阻断继续接单 |
| `2 → 3` | 工人完成服务 | 工人回到「可接单」 |
| `0 → 4` | 用户取消 / 管理员取消 / 超时取消 | 未支付取消，`pay_status` 保持未支付 |
| `1 → 4` | 用户取消 / 管理员取消 | 已接单未开始，取消后释放工人归属 |

所有状态流转都用 **CAS 更新**（`UPDATE ... WHERE id = ? AND order_status = 旧状态`），影响行数为 0 直接抛异常回滚。这样即使「用户取消」和「工人抢单」并发到达，也只有一个能成功，不会出现「已取消的订单被接走」。

### 2. 分布式锁防重复派单/抢单

派单、抢单基于 **Redisson 互斥锁** 串行化，解锁通过 `TransactionSynchronization` 注册到事务提交后回调 —— 避免「锁已释放、事务还没提交」时后一个请求读到未提交数据。

### 3. 超时工单自动取消

`OrderTimeTask` 按分钟扫描两类超时工单：**下单 15 分钟未支付**、**已支付 30 分钟无人接单**。循环内每条工单**独立事务 + 异常隔离**，单条失败不影响整批；取消同样走状态机，因此与抢单/派单并发时由 CAS 兜底，不会把刚被接走的单取消掉。

### 4. 取消释放工人归属 + 接单历史

取消已接单工单时把 `worker_id` 置空，避免「已取消的单还挂在工人名下」。为了不丢历史，抢单/派单成功时向 `grab_order_record` 写一条记录，工人查「已取消」列表时用 `exists` 子查询召回 —— 既释放了归属字段，又保留可追溯性。

同时工人状态回写做了保护：只有**手上没有服务中工单**时才把工人置回「可接单」，避免「取消 A 单」误改正在服务 B 单的工人状态。

### 5. 业务异常与系统异常分层

所有可预期的业务失败（状态非法、越权、未支付、归属校验失败）统一抛 `BusinessException`，由 `GlobalExceptionHandler` 原样返回提示文案；其余未捕获异常记录完整堆栈后统一返回「服务器内部异常，请联系管理员」，不向前端泄漏堆栈或框架类名。

### 6. 三端权限隔离

管理员、用户、工人三套 JWT 密钥分别签发 Token，拦截器解析后写入 `BaseContext`（ThreadLocal）；接口级角色隔离，并在订单详情、取消、评价等接口校验 **资源归属**，防止横向越权。

### 7. AI 智能客服（RAG + Function Calling）

- **RAG 知识库**：管理员上传平台规则文档 → Tika 解析 → `TokenTextSplitter(200 token)` 切块 → embedding 向量化 → pgvector 存储（1024 维、余弦距离、HNSW 索引、独立连接池）；检索走 `QuestionAnswerAdvisor`（topK 10、相似度阈值 0.5）。
- **Function Calling**：`UserOrderTools` 暴露「查我的订单列表 / 查某一单状态」两个工具，**userId 取自登录态而不是模型入参**，并二次校验订单归属，从机制上杜绝越权查询。
- **会话记忆**：`conversationId = user:{userId}`，`MessageWindowChatMemory` 保留最近 20 条消息，按用户隔离。

---

## 接口清单

共 **57 个接口**。统一返回结构 `{ code, msg, data }`，除登录/注册外均需在 Header 携带 `token`。

<details>
<summary>展开完整接口列表</summary>

### 管理端 `/api/admin`

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/admin/auth/login` | 管理员登录 |
| POST | `/api/admin/logout` | 管理员退出 |
| GET | `/api/admin/orders` | 订单列表（分页/筛选） |
| GET | `/api/admin/orders/{id}` | 订单详情 |
| PUT | `/api/admin/orders/{id}/assign` | 派单 |
| PUT | `/api/admin/orders/{id}/cancel` | 取消订单 |
| GET | `/api/admin/service-categories/all` | 全部分类 |
| PUT | `/api/admin/service-categories/{id}` | 修改分类 |
| PUT | `/api/admin/service-categories/{id}/status` | 分类上下架 |
| DELETE | `/api/admin/service-categories/{id}` | 删除分类 |
| GET | `/api/admin/service-items` | 服务项目列表 |
| POST | `/api/admin/service-items` | 新增服务项目 |
| PUT | `/api/admin/service-items/{id}` | 修改服务项目 |
| PUT | `/api/admin/service-items/{id}/status` | 项目上下架 |
| DELETE | `/api/admin/service-items/{id}` | 删除服务项目 |
| DELETE | `/api/admin/order-comments/{id}` | 删除评价 |
| GET | `/api/admin/statistics/order-count` | 订单总量 |
| GET | `/api/admin/statistics/order-status-count` | 状态分布 |
| GET | `/api/admin/statistics/worker-rank` | 工人排行 |
| GET | `/api/admin/users` | 用户列表 |
| PUT | `/api/admin/users/{id}/status` | 禁用/启用用户 |
| GET | `/api/admin/workers` | 工人列表 |
| PUT | `/api/admin/workers/{id}/status` | 工人状态 |
| GET | `/api/admin/workers/available` | 可用工人 |
| POST | `/api/admin/knowledgeUpload/upload` | 上传知识库文档 |

### 用户端 `/api/user`

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/user/register` | 注册 |
| POST | `/api/user/login` | 登录 |
| POST | `/api/user/logout` | 退出 |
| GET | `/api/user/categories` | 服务分类 |
| GET | `/api/user/services` | 服务项目列表 |
| GET | `/api/user/services/{id}` | 服务项目详情 |
| POST | `/api/user/orders` | 下单 |
| GET | `/api/user/orders` | 我的订单 |
| GET | `/api/user/orders/{id}` | 订单详情 |
| PUT | `/api/user/orders/{id}/pay` | 支付 |
| PUT | `/api/user/orders/{id}/cancel` | 取消订单 |
| POST | `/api/user/orders/{id}/comment` | 评价 |
| GET | `/api/user/addresses` | 地址列表 |
| POST | `/api/user/addresses` | 新增地址 |
| PUT | `/api/user/addresses/{id}` | 修改地址 |
| DELETE | `/api/user/addresses/{id}` | 删除地址 |
| PUT | `/api/user/profile` | 修改资料 |
| GET | `/api/user/ai/chat` | AI 客服（规则问答 + 查我的订单） |

### 工人端 `/api/worker`

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/worker/register` | 注册 |
| POST | `/api/worker/login` | 登录 |
| POST | `/api/worker/logout` | 退出 |
| GET | `/api/worker/orders/pending` | 待抢单列表 |
| GET | `/api/worker/orders/{id}` | 待抢单详情 |
| PUT | `/api/worker/orders/{id}/grab` | 抢单 |
| GET | `/api/worker/orders` | 我的工单 |
| PUT | `/api/worker/orders/{id}/start` | 开始服务 |
| PUT | `/api/worker/orders/{id}/complete` | 完成服务 |
| GET | `/api/worker/workbench` | 工作台统计 |
| GET | `/api/worker/profile` | 个人资料 |
| PUT | `/api/worker/profile` | 修改资料 |
| PUT | `/api/worker/service-status` | 切换接单状态 |

### 通用 `/api/common`

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/common/upload` | 文件上传 |

</details>

---

## 快速开始

### 方式一：docker-compose（推荐，一键起 MySQL + Redis + 应用）

```bash
git clone https://github.com/demoLover-learn/yunxiutongSystem.git
cd yunxiutongSystem

# 启动（AI 所需的密钥通过环境变量注入，见「配置说明」）
docker compose up -d --build

# 查看日志
docker compose logs -f app
```

启动后：

- 应用：`http://localhost:8081`（容器内 8080，宿主映射 8081）
- MySQL：`localhost:3307`（容器内 3306，root / 123456）
- Redis：`localhost:6379`
- 接口文档：`http://localhost:8081/doc.html`

> ⚠️ compose 未包含 PostgreSQL，AI 客服需要额外启动 pgvector（见下）。

### 方式二：本地开发

```bash
# 1. MySQL 8：导入 init/yunxiutong.sql
mysql -uroot -p123456 -e "CREATE DATABASE yunxiutong DEFAULT CHARSET utf8mb4;"
mysql -uroot -p123456 yunxiutong < init/yunxiutong.sql

# 2. Redis
docker run -d --name redis -p 6379:6379 redis:latest

# 3. PostgreSQL + pgvector（AI 功能需要）
docker run -d --name pgvector \
  -e POSTGRES_PASSWORD=123456 \
  -e POSTGRES_DB=yunxiutong_ai \
  -p 5432:5432 \
  pgvector/pgvector:pg16

# 4. 配置并启动
mvn spring-boot:run
```

### 5. 验证

```bash
# 管理员登录
curl -X POST http://localhost:8080/api/admin/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'

# AI 客服（需要用户 Token）
curl "http://localhost:8080/api/user/ai/chat?message=我的订单到哪了" \
  -H "token: <用户Token>"
```

---

## 配置说明

### 环境变量（application.yml 中的占位符）

| 环境变量 | 说明 | 示例 |
|---|---|---|
| `YXT_DATASOURCE_HOST` | MySQL 主机 | `localhost` |
| `YXT_DATASOURCE_PORT` | MySQL 端口 | `3306` |
| `YXT_DATASOURCE_DATABASE` | 库名 | `yunxiutong` |
| `YXT_DATASOURCE_USERNAME` | 用户名 | `root` |
| `YXT_DATASOURCE_PASSWORD` | 密码 | `123456` |
| `STYX_DATA_REDIS_HOST` | Redis 主机 | `localhost` |
| `STYX_DATA_REDIS_PORT` | Redis 端口 | `6379` |
| `JWT_ADMIN_SECRET` / `JWT_USER_SECRET` / `JWT_WORKER_SECRET` | 三套 JWT 密钥 | 生产环境务必替换 |

### AI 配置（`application.yml`）

```yaml
spring:
  ai:
    openai:
      base-url: https://api.deepseek.com          # 对话模型
      chat:
        options:
          model: deepseek-flash
      embedding:
        base-url: https://maas.qianwenaiapi.com/compatible-mode   # 向量模型（OpenAI 兼容）
        options:
          model: qwen3.7-text-embedding-flash
ai:
  vector:
    url: jdbc:postgresql://localhost:5432/yunxiutong_ai
    username: postgres
```

### API Key

`application-dev.yml` 已被 `.gitignore` 忽略，**请勿提交任何密钥**。推荐用环境变量注入：

```yaml
spring:
  ai:
    openai:
      api-key: ${DEEPSEEK_API_KEY}
      embedding:
        api-key: ${EMBEDDING_API_KEY}
```

---

## 常见问题

**Q：接口文档打不开？**
A：访问 `http://localhost:8080/doc.html`（compose 部署为 `8081`）。

**Q：AI 客服报错「表 vector_store 不存在」或连接失败？**
A：确认 PostgreSQL 已启动且安装了 `vector` 扩展。`PgVectorStore` 配置了 `initializeSchema(true)`，会在首次启动时自动建表，但需要数据库用户有建表权限。未启动 PG 时，其余业务功能不受影响。

**Q：Redis 数据串到别的应用？**
A：本项目使用 Redis 的 `database: 1`，与默认 db 0 隔离。

**Q：MySQL 端口为什么是 3307？**
A：compose 中映射为 `3307:3306`，避免与本机已安装的 MySQL（3306）冲突。

**Q：知识库文档支持哪些格式？**
A：由 Apache Tika 解析，支持 docx / pdf / txt / html 等常见格式；同一文件名重复上传会覆盖旧向量。

---

## 后续规划

- [x] 抢单/取消并发正确性验证（7 工人并发抢同一单 60 轮，每轮恰好 1 人成功；抢单 vs 取消 40 轮，终态与日志一致）
- [ ] 补齐单元测试（状态机流转、权限校验）
- [ ] 同一工人「预约时段冲突 + 在途单上限」校验（已知边界，当前用 `service_status` 单点闸门兜底，未实现时段重叠判断）
- [ ] AI 客服检索升级：BM25 + 向量混合检索、rerank、metadata 过滤
- [ ] 会话记忆持久化（JDBC / Redis 版 ChatMemoryRepository）
- [ ] 接口限流与幂等（重复提交防护）
- [ ] 前端演示环境与 README 截图

---

## 作者

- 孙楠 · 南阳师范学院 软件工程（2027 届）
- 求职意向：Java 后端开发实习生
