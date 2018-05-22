package com.dazzlzy.springbootseed.controller;

import com.dazzlzy.common.base.BaseResult;
import com.dazzlzy.common.base.BaseResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 *
 * @author dazzlzy
 * @date 2018/5/22
 */
@Slf4j
@Api(value = "/test", tags = "测试接口模块")
@RestController
@RequestMapping(value = "test")
public class TestController {

    /**
     * 测试
     *
     * @return BaseResult
     */
    @ApiOperation(value = "测试", notes = "测试接口")
    @RequestMapping(value = "test", method = {RequestMethod.GET})
    @ResponseBody
    public BaseResult test() {
        log.info("test info ...");
        return BaseResultGenerator.success();
    }


}
