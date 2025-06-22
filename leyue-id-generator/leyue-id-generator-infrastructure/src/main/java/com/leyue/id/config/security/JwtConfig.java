package com.leyue.id.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 配置类
 *
 * @author Trae
 * @since 2024-01-20
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    
    /**
     * RSA 公钥（用于验签）
     */
    private String publicKey;
    
    /**
     * 时间漂移容忍度（秒）
     */
    private long clockSkew = 60;
    
    public String getPublicKey() {
        return publicKey;
    }
    
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    
    public long getClockSkew() {
        return clockSkew;
    }
    
    public void setClockSkew(long clockSkew) {
        this.clockSkew = clockSkew;
    }
} 