package com.dazzlzy.common.utils;

import com.dazzlzy.SpringBootSeedApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * RandomUtil的单元测试
 *
 * @author dazzlzy
 * @date 2018/5/27
 */
@Slf4j
public class RandomUtilTest extends SpringBootSeedApplicationTests {

    @Test
    public void getSalt() {
        log.info("Salt1: {}", RandomUtil.getSalt(2));
        log.info("Salt2: {}", RandomUtil.getSalt(4));
        log.info("Salt3: {}", RandomUtil.getSalt(8));
        log.info("Salt4: {}", RandomUtil.getSalt(16));
    }

    @Test
    public void getSalt1() {
        log.info("Salt1: {}", RandomUtil.getSalt());
        log.info("Salt2: {}", RandomUtil.getSalt());
        log.info("Salt3: {}", RandomUtil.getSalt());
        log.info("Salt4: {}", RandomUtil.getSalt());
    }

    @Test
    public void getUUID() {
        log.info("UUID1: {}", RandomUtil.getUUID());
        log.info("UUID2: {}", RandomUtil.getUUID());
        log.info("UUID3: {}", RandomUtil.getUUID());
        log.info("UUID4: {}", RandomUtil.getUUID());
    }

    @Test
    public void getShortUUID() {
        log.info("UUID1: {}", RandomUtil.getShortUUID());
        log.info("UUID2: {}", RandomUtil.getShortUUID());
        log.info("UUID3: {}", RandomUtil.getShortUUID());
        log.info("UUID4: {}", RandomUtil.getShortUUID());
    }
}