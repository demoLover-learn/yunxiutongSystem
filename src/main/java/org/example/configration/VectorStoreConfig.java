package org.example.configration;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VectorStoreConfig {
    /**
     *
     * @param embeddingModel
     * @param url
     * @param username
     * @param password
     * @return
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel
    ,@Value("${ai.vector.url}") String url
    ,@Value("${ai.vector.username}") String username
    ,@Value("${ai.vector.password}") String password){

        //单独为向量库建了一个连接池，名字叫 vector-pool
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName("vector-pool");
        //向量数据库连自己的连接池
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new HikariDataSource(config));

        return PgVectorStore.builder(jdbcTemplate,embeddingModel)
                .dimensions(1024) //embedding输出维度，要和自己的embeddingModel输出维度一致
                .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE)
                .indexType(PgVectorStore.PgIndexType.HNSW)
                .initializeSchema(true)//初始化数据库的结构
                .schemaName("public")//指定使用 PostgreSQL 的 public 模式（schema）
                .vectorTableName("vector_store")
                .build();

    }





}
