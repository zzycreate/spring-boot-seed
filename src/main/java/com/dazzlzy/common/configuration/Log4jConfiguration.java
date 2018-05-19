package com.dazzlzy.common.configuration;

import org.apache.logging.log4j.web.Log4jServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static javax.servlet.DispatcherType.*;

/**
 * Log4j2在web中的过滤器 过滤REQUEST, FORWARD, INCLUDE, ERROR, ASYNC类型请求
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Configuration
public class Log4jConfiguration {

    /**
     * Log4j2 过滤器
     *
     * @return filter
     */
    @Bean
    public FilterRegistrationBean log4jServletFilter() {
        FilterRegistrationBean<Log4jServletFilter> filterRegistrationBean = new FilterRegistrationBean<>(new Log4jServletFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(REQUEST, FORWARD, INCLUDE, ERROR, ASYNC);
        return filterRegistrationBean;
    }

}
