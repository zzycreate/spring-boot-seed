package com.dazzlzy.common.redis;

import com.dazzlzy.SpringBootSeedApplicationTests;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RedisClientTemplate 单元测试
 *
 * @author dazzlzy
 * @date 2018/5/20
 */
@Slf4j
public class RedisClientTemplateTest extends SpringBootSeedApplicationTests {

    /**
     * 注入的jedis模板操作对象
     */
    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Test
    public void set() {
        redisClientTemplate.set("test.key", "KEY");
        log.info("============= redis set success =============");
    }

    @Test
    public void set1() {
        Map<String, String> map = new HashMap<>();
        map.put("map.key1", "KEY1");
        map.put("map.key2", "KEY2");
        redisClientTemplate.set(map);
        TestCase.assertEquals("KEY1", redisClientTemplate.get("map.key1"));
        TestCase.assertEquals("KEY2", redisClientTemplate.get("map.key2"));
        log.info("============= redis set success =============");
    }

    @Test
    public void get() {
        redisClientTemplate.set("test.key", "get result");
        String value = redisClientTemplate.get("test.key");
        log.info("==============================================");
        log.info(value);
        log.info("==============================================");
        TestCase.assertEquals("get result", value);
        log.info("============= redis get success ==============");
    }

    @Test
    public void get1() {
        redisClientTemplate.set("map.key1", "get1 key1");
        redisClientTemplate.set("map.key2", "get1 key2");
        List<String> list = new ArrayList<>();
        list.add("map.key1");
        list.add("map.key2");
        Map<String, String> map = redisClientTemplate.get(list);
        log.info("==============================================");
        log.info(map.toString());
        log.info("==============================================");
        TestCase.assertEquals("get1 key1", map.get("map.key1"));
        TestCase.assertEquals("get1 key2", map.get("map.key2"));
        log.info("============= redis get1 success ==============");
    }

    @Test
    public void get2() {
        redisClientTemplate.set("map.key1", "get2 key1");
        redisClientTemplate.set("map.key2", "get2 key2");
        Map<String, String> map = redisClientTemplate.get("map.key1", "map.key2");
        log.info("==============================================");
        log.info(map.toString());
        log.info("==============================================");
        TestCase.assertEquals("get2 key1", map.get("map.key1"));
        TestCase.assertEquals("get2 key2", map.get("map.key2"));
        log.info("============= redis get2 success ==============");
    }

    @Test
    public void exists() {
        TestCase.assertTrue(redisClientTemplate.exists("test.key"));
        log.info("============= redis exists success ==============");
    }

    @Test
    public void type() {
        TestCase.assertEquals("string", redisClientTemplate.type("test.key"));
        log.info("============= redis type success ==============");
    }

    @Test
    public void expire() {
        redisClientTemplate.expire("test.key", 1);
        try {
            Thread.sleep(2000);
            TestCase.assertNull(redisClientTemplate.get("test.key"));
            log.info("============= redis expire success =============");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("============= redis expire error =============");
        }
    }

    @Test
    public void expireAt() {
        //TODO 单元测试未通过
        redisClientTemplate.set("test.key", "expireAt key");
        redisClientTemplate.expireAt("test.key", System.currentTimeMillis() + 2000);
        TestCase.assertEquals("expireAt key", redisClientTemplate.get("test.key"));
        try {
            Thread.sleep(50000);
            TestCase.assertNull(redisClientTemplate.get("test.key"));
            log.info("============= redis expireAt success ==============");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("============= redis expireAt error ==============");
        }
    }

    @Test
    public void del() {
        redisClientTemplate.set("test.key", "KEYYYYY");
        TestCase.assertEquals("KEYYYYY", redisClientTemplate.get("test.key"));
        redisClientTemplate.del("test.key");
        TestCase.assertNull(redisClientTemplate.get("test.key"));
        log.info("============= redis del success ==============");
    }

    @Test
    public void getList1() {
        redisClientTemplate.del("list.key");
        redisClientTemplate.rpush("list.key", "LIST-VALUE-1");
        redisClientTemplate.rpush("list.key", "LIST-VALUE-2");
        redisClientTemplate.rpush("list.key", "LIST-VALUE-3");
        List<String> list = redisClientTemplate.getList("list.key");
        log.info("==============================================");
        log.info(list.toString());
        log.info("==============================================");
        TestCase.assertEquals("LIST-VALUE-1", list.get(0));
        TestCase.assertEquals("LIST-VALUE-2", list.get(1));
        TestCase.assertEquals("LIST-VALUE-3", list.get(2));
        log.info("============= redis getList1 success ==============");
    }

    @Test
    public void getList2() {
        redisClientTemplate.del("list.key");
        redisClientTemplate.rpush("list.key", "LIST-VALUE-1");
        redisClientTemplate.rpush("list.key", "LIST-VALUE-2");
        redisClientTemplate.rpush("list.key", "LIST-VALUE-3");
        List<String> list = redisClientTemplate.getList("list.key", 0, 1);
        log.info("==============================================");
        log.info(list.toString());
        log.info("==============================================");
        TestCase.assertEquals(2, list.size());
        TestCase.assertEquals("LIST-VALUE-1", list.get(0));
        TestCase.assertEquals("LIST-VALUE-2", list.get(1));
        log.info("============= redis getList2 success ==============");
    }

    @Test
    public void llen() {
        Long length = redisClientTemplate.llen("test.list.length");
        TestCase.assertEquals(0, length.longValue());
        log.info("============= redis llen success ==============");
    }

    @Test
    public void rpush() {
        if (0 != redisClientTemplate.llen("test.list")) {
            redisClientTemplate.del("test.list");
        }
        redisClientTemplate.rpush("test.list", "LIST-1");
        redisClientTemplate.rpush("test.list", "LIST-2");
        redisClientTemplate.rpush("test.list", "LIST-3");
        TestCase.assertEquals("list", redisClientTemplate.type("test.list"));
        List<String> list = redisClientTemplate.getList("test.list");
        log.info("==============================================");
        log.info(list.toString());
        log.info("==============================================");
        TestCase.assertEquals(3, list.size());
        TestCase.assertEquals("LIST-1", list.get(0));
        TestCase.assertEquals("LIST-2", list.get(1));
        TestCase.assertEquals("LIST-3", list.get(2));
        log.info("============= redis rpush success ==============");
    }

    @Test
    public void lpush() {
        if (0 != redisClientTemplate.llen("test.list")) {
            redisClientTemplate.del("test.list");
        }
        redisClientTemplate.lpush("test.list", "LIST-1");
        redisClientTemplate.lpush("test.list", "LIST-2");
        redisClientTemplate.lpush("test.list", "LIST-3");
        TestCase.assertEquals("list", redisClientTemplate.type("test.list"));
        List<String> list = redisClientTemplate.getList("test.list");
        log.info("==============================================");
        log.info(list.toString());
        log.info("==============================================");
        TestCase.assertEquals(3, list.size());
        TestCase.assertEquals("LIST-3", list.get(0));
        TestCase.assertEquals("LIST-2", list.get(1));
        TestCase.assertEquals("LIST-1", list.get(2));
        log.info("============= redis lpush success ==============");
    }
}