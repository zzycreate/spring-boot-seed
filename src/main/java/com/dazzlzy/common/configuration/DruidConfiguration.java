package com.dazzlzy.common.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid 配置注入
 *
 * @author dazzlzy
 * @date 2018/5/18
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public class DruidConfiguration {

    /**
     * 注入Druid配置，yml中的spring.datasource
     */
    private final DruidProperties properties;

    /**
     * 讲解@Autowired注解：
     * 在使用@Autowired时，首先在容器中查询对应类型的bean
     * 1. 如果查询结果刚好为一个，就将该bean装配给@Autowired指定的数据
     * 2. 如果查询的结果不止一个，那么@Autowired会根据名称来查找。指定名称可以使用 @Qualifier 注解
     * 3. 如果查询的结果为空，那么会抛出异常。解决方法时，使用required=false
     * <p>
     * 讲解@Autowired注解与@Resource注解的区别：
     * 1. spring2.5提供的@Autowired默认是使用byType的方式(根据Bean的class类型)搜索bean，
     * 如果搜索到多个bean，再使用byName的方式(根据bean实例上申明的名称)搜索，需要配合@Qualifier注解使用，名称写在@Qualifier注解上
     * 而且@Autowired默认情况下要求依赖对象必须存在，如果要允许null值，可以设置它的required属性为false
     * 2. jdk1.6提供的@Resource注解默认是使用byName的方式搜索bean，名称可以通过name属性进行指定，
     * 如果没有指定name属性，当注解写在字段上时，默认取字段名，并按照名称查找，如果注解写在setter方法上默认取属性名进行装配。
     * 当找不到与名称匹配的bean时才按照类型进行装配。但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。
     * <p>
     * spring注入@Autowired的时机问题：
     * 1. 如果在成员属性或者setter方法上添加@Autowired注解注入，则该属性的值是在构造器之后才会注入，如果在构造器中就要使用这个属性，则会报空指针的异常
     * 2. spring4提供了在构造器上添加@Autowired注解的方法，spring会向构造器中的入参注入bean实例，之后才会执行构造器内部的代码
     * 3. spring官方推荐spring4及以后版本使用构造器注入bean，IDEA也对此提供了Warning级别的提示
     * 4. spring的单元测试类中不使用构造器@Autowired注入，因为单元测试需要有无参构造器；如果要使用有参构造器，则需要在类上添加@RunWith(Parameterized.class)注解，
     * 并且增加带@Parameters注解的方法，@Parameters方法的方法签名必须是public static Collection，方法不能有参数，并且collection元素必须是相同长度的数组，
     * 因此spring单元测试类一般不使用构造器注入的方式
     *
     * @param properties spring注入的对象
     */
    @Autowired
    public DruidConfiguration(DruidProperties properties) {
        this.properties = properties;
    }

    /**
     * 注入 DruidDataSource
     *
     * @return dataSource
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setConnectionProperties("config.decrypt=true;config.decrypt.key=" + properties.getPublicKey());
        dataSource.setInitialSize(properties.getInitialSize());
        dataSource.setMinIdle(properties.getInitialSize());
        dataSource.setMaxActive(properties.getMaxActive());
        dataSource.setMaxWait(properties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        dataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setValidationQuery(properties.getValidationQuery());
        dataSource.setDefaultAutoCommit(true);
        try {
            dataSource.setFilters(properties.getFilters());
        } catch (SQLException e) {
            log.error("DataSource set filters error ！", e);
        }
        return dataSource;
    }

    /**
     * 注入Druid的StatViewServlet
     *
     * @return servlet
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("loginUsername", properties.getUsername());
        servletRegistrationBean.addInitParameter("loginPassword", properties.getPassword());
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;
    }

    /**
     * 注入Druid的WebStatFilter
     *
     * @return filter
     */
    @Bean
    public FilterRegistrationBean druidWebStatFilter() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        // 过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 过滤忽略格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
