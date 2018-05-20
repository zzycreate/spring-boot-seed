package com.dazzlzy.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.*;

/**
 * redis操作类
 *
 * @author dazzlzy
 * @date 2018/5/20
 */
@Slf4j
@Component
public class RedisClientTemplate {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    /**
     * 获取redis操作类
     *
     * @return ShardedJedis
     */
    public ShardedJedis getRedisClient() {
        try {
            return shardedJedisPool.getResource();
        } catch (Exception e) {
            log.error("getRedisClent error ! ", e);
            if (null != shardedJedisPool) {
                shardedJedisPool.close();
            }
        }
        return null;
    }

    /**
     * 设置单个值
     *
     * @param key   key
     * @param value value
     * @return result
     */
    public String set(String key, String value) {
        String result = null;

        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error("RedisClientTemplate set error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 设置单个值
     *
     * @param key   key
     * @param value value
     * @return result
     */
    public String set(String key, String value, int seconds) {
        String result = null;

        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.set(key, value);
            shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("RedisClientTemplate set error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 设置多个值
     *
     * @param params 设置的key-value对
     */
    public void set(Map<String, String> params) {
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            throw new RuntimeException("未获取到RedisClient");
        }

        try {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue();
                shardedJedis.set(key, value);
            }
        } catch (Exception e) {
            log.error("RedisClientTemplate set error !", e);
        } finally {
            shardedJedis.close();
        }
    }

    /**
     * 获取单个值
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.get(key);

        } catch (Exception e) {
            log.error("RedisClientTemplate get error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 获取多个值
     *
     * @param keys key集合
     * @return result
     */
    public Map<String, String> get(Collection<String> keys) {
        Map<String, String> result = new HashMap<>(keys.size());
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            for (String key : keys) {
                String value = shardedJedis.get(key);
                result.put(key, value);
            }
        } catch (Exception e) {
            log.error("RedisClientTemplate get error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }


    /**
     * 获取多个值
     *
     * @param keys key集合
     * @return result
     */
    public Map<String, String> get(String... keys) {
        return this.get(Lang.list(keys));
    }

    /**
     * 检查某个值是否存在
     *
     * @param key key
     * @return 是否存在某值
     */
    public boolean exists(String key) {
        Boolean result = false;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return false;
        }

        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            log.error("RedisClientTemplate exists error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 获取某值的类型
     *
     * @param key key
     * @return 类型
     */
    public String type(String key) {
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.type(key);
        } catch (Exception e) {
            log.error("RedisClientTemplate type error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 在某段时间后失效
     *
     * @param key     key
     * @param seconds 失效时间
     * @return result
     */
    public Long expire(String key, int seconds) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("RedisClientTemplate expire error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 在某个时间点失效
     *
     * @param key      key
     * @param unixTime 失效时间点
     * @return result
     */
    public Long expireAt(String key, long unixTime) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.expireAt(key, unixTime);

        } catch (Exception e) {
            log.error("RedisClientTemplate expireAt error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }


    /**
     * 删除某个值
     *
     * @param key key
     * @return result
     */
    public Long del(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.del(key);

        } catch (Exception e) {
            log.error("RedisClientTemplate del error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    // -------------------------------- list 操作 ----------------------------------------

    /**
     * 获取制定位置列表
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return result
     */
    public List<String> getList(String key, long start, long end) {
        List<String> result = null;
        try {
            result = lrange(key, start, end);
        } catch (Exception e) {
            log.error("RedisClientTemplate getList error !", e);
        }
        return result;
    }

    /**
     * 获取全部列表
     *
     * @param key key
     * @return result
     */
    public List<String> getList(String key) {
        List<String> result = null;

        try {
            result = lrange(key, 0, llen(key));
        } catch (Exception e) {
            log.error("RedisClientTemplate getList error !", e);
        }

        return result;
    }


    /**
     * 获取list长度
     *
     * @param key key
     * @return length
     */
    public Long llen(String key) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.llen(key);

        } catch (Exception e) {
            log.error("RedisClientTemplate llen error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 获取列表指定范围内的list结果
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return result
     */
    public List<String> lrange(String key, long start, long end) {
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("RedisClientTemplate lrange error !", e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * list右侧插入（队尾）
     *
     * @param key    key
     * @param string value
     * @return result
     */
    public Long rpush(String key, String string) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.rpush(key, string);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * list左侧插入（队头）
     *
     * @param key    key
     * @param string value
     * @return result
     */
    public Long lpush(String key, String string) {
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.lpush(key, string);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

}
