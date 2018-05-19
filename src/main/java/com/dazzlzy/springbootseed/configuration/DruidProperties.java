package com.dazzlzy.springbootseed.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Druid注入的配置参数
 *
 * @author dazzlzy
 * @date 2018/5/18
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {

    private String url;

    private String driverClassName;

    private String username;

    private String password;

    private String publicKey;

    private Integer initialSize;

    private Integer minIdle;

    private Integer maxActive;

    private Long maxWait;

    private Long timeBetweenEvictionRunsMillis;

    private Long minEvictableIdleTimeMillis;

    private Boolean poolPreparedStatements;

    private Integer maxPoolPreparedStatementPerConnectionSize;

    private String validationQuery;

    private String filters;

}
