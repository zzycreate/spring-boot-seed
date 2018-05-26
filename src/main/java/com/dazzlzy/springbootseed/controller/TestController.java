package com.dazzlzy.springbootseed.controller;

import com.dazzlzy.common.base.BaseResult;
import com.dazzlzy.common.base.BaseResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试Controller
 * 使用@RestController可以不需要使用@ResponseBody注解，方法会自动处理成json格式返回
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
     * 使用@GetMapping相当于使用@RequestMapping(method={RequestMethod.GET})
     *
     * @return BaseResult
     */
    @ApiOperation(value = "测试", notes = "测试接口")
    @GetMapping(value = "test")
    public BaseResult test() {
        log.info("test info ...");
        return BaseResultGenerator.success();
    }


}
