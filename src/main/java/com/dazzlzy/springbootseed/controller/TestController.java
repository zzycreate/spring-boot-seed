package com.dazzlzy.springbootseed.controller;

import com.dazzlzy.common.base.BaseResult;
import com.dazzlzy.common.base.BaseResultGenerator;
import com.dazzlzy.common.configuration.ProjectProperties;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试Controller
 * 使用@RestController可以不需要使用@ResponseBody注解，方法会自动处理成json格式返回
 *
 * @author dazzlzy
 * @date 2018/5/22
 */
@Slf4j
@Api(value = "/test", tags = {"test"}, description = "测试接口，一些测试用例展示")
@RestController
@RequestMapping(value = "test")
public class TestController {

    private final ProjectProperties projectProperties;

    private final Environment environment;

    @Autowired
    public TestController(ProjectProperties projectProperties, Environment environment) {
        this.projectProperties = projectProperties;
        this.environment = environment;
    }

    /**
     * 测试
     * 使用@GetMapping相当于使用@RequestMapping(method={RequestMethod.GET})
     *
     * @return BaseResult
     */
    @ApiOperation(value = "项目配置", notes = "获取项目配置参数")
    @GetMapping(value = "projectProperties")
    public BaseResult projectProperties() {
        log.info("Project Properties: {}", projectProperties);
        //此处本意是将projectProperties返回至前端，但是projectProperties对象是由spring注入而来，其中包含过多的动态代理数据，
        //使用lombok的@Data注解处理BaseResult时，数据过大，返回报错，因此只返回success
        return BaseResultGenerator.success();
    }

    /**
     * 获取项目环境值，获取的是Environment对象中的activeProfiles，String[]
     *
     * @return 返回当前项目的环境值
     */
    @ApiOperation(value = "项目环境值", notes = "获取当前项目运行时环境值，取Environment对象中的activeProfiles, 项目运行时的值")
    @GetMapping(value = "activeProfiles")
    public BaseResult activeProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        log.info("Active Profiles: {}", Arrays.toString(activeProfiles));
        return BaseResultGenerator.success(activeProfiles);
    }

    /**
     * 运行环境，将Environment.activeProfiles注入到projectProfiles中
     *
     * @return 返回当前项目的运行环境
     */
    @ApiOperation(value = "项目环境值", notes = "获取当前项目运行环境，取ProjectProperties.env的值，应该和activeProfiles一致")
    @GetMapping(value = "env")
    public BaseResult env() {
        String[] env = projectProperties.getEnv();
        log.info("Project env: {}", Arrays.toString(env));
        return BaseResultGenerator.success(env);
    }

    /**
     * 是否是生产环境
     *
     * @return 返回当前项目的运行环境
     */
    @ApiOperation(value = "是否生产环境", notes = "检查是否是生产环境")
    @GetMapping(value = "isProduct")
    public BaseResult isProduct() {
        boolean isProduct = projectProperties.isProduct();
        String msg = "Current Environment is" + (isProduct ? "" : " not") + " product";
        log.info(msg);
        return BaseResultGenerator.success(msg);
    }

    /**
     * 检查是否授权
     *
     * @return 检查授权
     */
    @ApiOperation(value = "检查授权", notes = "检查授权，如果授权成功：返回success，否则跳转/login")
    @GetMapping(value = "checkAuthc")
    public BaseResult checkAuthc() {
        return BaseResultGenerator.success();
    }

    /**
     * 使用@ApiOperation注解描述方法的swagger文档，参数基本同@Api
     * 1. notes: 单个URL资源的描述，可以使用html标签
     * 2. response： 返回对象
     * 3. responseContainer： 返回对象容器，有效值："List", "Set" or "Map"
     * 4. httpMethod：方法method："GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
     * 5. code: http返回码
     * 6. value、tags、produces、consumes、protocols、authorizations同@Api注解
     *
     * @return 响应结果
     */
    @ApiOperation(value = "测试GET请求", notes = "简单get请求，无参，纯返回成功结果", httpMethod = "GET", response = BaseResult.class)
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    @ResponseBody
    public BaseResult get() {
        log.info("test info ...");
        return BaseResultGenerator.success();
    }

    /**
     * 简单参数get请求
     * 简单参数get请求的路径为：localhost:8080/test/getParam?id=1&name=admin&age=18&price=1.23
     * 使用@ApiParam注解展示请求参数, 可以什么参数也不带，需要描述的时候使用value，必填校验使用required，name可以不用
     * 1. name: 参数名
     * 2. value： 参数描述
     * 3. required: 是否必须，默认为false
     * 4. defaultValue: 参数展示的默认值
     * 5. example: 调试参数输入框中的默认值
     *
     * @param id    ID值
     * @param name  名字
     * @param age   年龄
     * @param price 价格
     * @return 响应结果
     */
    @ApiOperation(value = "测试带参GET请求", notes = "简单参数get请求，返回请求参数, @ApiParam注解方式")
    @RequestMapping(value = "getParam", method = {RequestMethod.GET})
    @ResponseBody
    public BaseResult getParam(@ApiParam(name = "id", value = "ID值", required = true, defaultValue = "1", example = "2") @RequestParam Long id,
                               @ApiParam(name = "name", value = "名字", example = "名字") @RequestParam String name,
                               @ApiParam(value = "年龄", defaultValue = "18") @RequestParam(required = false) Integer age,
                               @ApiParam(value = "价格", required = true) @RequestParam Double price) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", id);
        map.put("name", name);
        map.put("age", age);
        map.put("price", price);
        return BaseResultGenerator.success(map);
    }

    /**
     * 简单参数get请求
     * 简单参数get请求的路径为：localhost:8080/test/getParam/id=1?name=admin&age=18&price=1.23
     * 使用@ApiImplicitParam注解展示请求参数,重要参数，name，dataType， paramType
     * 1. name、 value、 required、 defaultValue、 example参数同@ApiParam
     * 2. dataType： 数据类型（int， Integer，Long，String等都可以，只做展示不做校验），默认string
     * 3. paramType： 参数类型 （path：以地址的形式提交数据；query：直接跟参数完成自动映射赋值；body：以流的形式提交，仅支持POST；header：参数在request headers里边提交；form：以form表单的形式提交 仅支持POST）
     *
     * @param id    ID值
     * @param name  名字
     * @param age   年龄
     * @param price 价格
     * @return 响应结果
     */
    @ApiOperation(value = "测试组合带参GET请求", notes = "简单参数get请求，返回请求参数， @ApiImplicitParam方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID值", dataTypeClass = Long.class, paramType = "query", required = true, defaultValue = "1", example = "2"),
            @ApiImplicitParam(name = "name", value = "名字", dataTypeClass = String.class, paramType = "path", example = "名字"),
            @ApiImplicitParam(name = "age", value = "年龄", dataTypeClass = Integer.class, defaultValue = "18"),
            @ApiImplicitParam(name = "price", value = "价格", dataTypeClass = Double.class, required = true)
    })
    @RequestMapping(value = "getImplicitParams/{name}", method = {RequestMethod.GET})
    @ResponseBody
    public BaseResult getImplicitParams(@RequestParam Long id, @PathVariable("name") String name,
                                        @RequestParam(required = false) Integer age, @RequestParam Double price) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", id);
        map.put("name", name);
        map.put("price", price);
        map.put("age", age);
        return BaseResultGenerator.success(map);
    }

}
