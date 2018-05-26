package com.dazzlzy.common.configuration;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis配置注入，使用spring-data-redis自动装配的RedisProperties，设置Jedis的配置
 *
 * @author dazzlzy
 * @date 2018/5/20
 */
@Configuration
public class RedisConfiguration {

    /**
     * 注入RedisProperties，使用spring-data-redis的配置对象，yml中的spring.redis配置
     */
    private final RedisProperties properties;

    @Autowired
    public RedisConfiguration(RedisProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        jedisPoolConfig.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        return jedisPoolConfig;
    }

    @Bean
    public List<JedisShardInfo> jedisShardInfos() {
        List<JedisShardInfo> jedisShardInfos = new ArrayList<>();
        //默认redis
        int timeout = Integer.parseInt(String.valueOf(properties.getTimeout().toMillis()));
        String password = properties.getPassword();
        JedisShardInfo defaultJedisShardInfo = new JedisShardInfo(properties.getHost(), properties.getPort(), timeout);
        defaultJedisShardInfo.setPassword(password);
        jedisShardInfos.add(defaultJedisShardInfo);

        //集群配置
        RedisProperties.Cluster cluster = properties.getCluster();
        if (cluster != null && cluster.getNodes() != null && cluster.getNodes().size() > 0) {
            List<String> nodes = cluster.getNodes();
            for (String node : nodes) {
                String[] nodeAry = node.split(":");
                int port = Integer.parseInt(nodeAry[1]);
                JedisShardInfo jedisShardInfo = new JedisShardInfo(nodeAry[0], port, timeout);
                if (StringUtils.isNotBlank(password)) {
                    jedisShardInfo.setPassword(password);
                }
                jedisShardInfos.add(jedisShardInfo);
            }
        }

        return jedisShardInfos;
    }

    @Bean
    public ShardedJedisPool shardedJedisPool() {
        return new ShardedJedisPool(jedisPoolConfig(), jedisShardInfos());
    }

}
