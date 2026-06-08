package org.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "yxt.jwt")
@Data
public class JwtProperties {
    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     *用户端口生成jwt令牌的相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

    /**
     *工人端口生成jwt令牌的相关配置
     */
    private String  workerSecretKey;
    private long workerTtl;
    private String workerTokenName;
}
