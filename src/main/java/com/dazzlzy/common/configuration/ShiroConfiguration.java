package com.dazzlzy.common.configuration;

import com.dazzlzy.common.enums.CookieEnum;
import com.dazzlzy.common.shiro.BaseRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置注入
 *
 * @author dazzlzy
 * @date 2018/5/26
 */
@Configuration
public class ShiroConfiguration {

    /**
     * Realm： 自定义shiro认证
     *
     * @return BaseRealm
     */
    @Bean
    public BaseRealm baseRealm() {
        return new BaseRealm();
    }

    /**
     * SessionManager，设置Session超时
     *
     * @return SessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(7200000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        return defaultWebSessionManager;
    }

    /**
     * rememberMeCookie: 记住自己的cookie
     *
     * @return SimpleCookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie(CookieEnum.REMEMBER_ME.getValue());
        //记住我cookie生效时间30天 ,单位秒;
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * RememberMeManager: remenberMe的管理器，注入有Cookie
     *
     * @return CookieRememberMeManager
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * SecurityManager: 安全管理器，注入有Realm、SessionManager、RememberMeManager
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(baseRealm());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager，其中注入了自定义的Realm
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 登陆url
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 成功登陆后打开的url
        shiroFilterFactoryBean.setSuccessUrl("/index");

        // 授权失败跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");

        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
        // 添加过滤器，例如：验证码过滤器 KaptchaFilter
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 权限过滤链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        /*
         * rest： 比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等。
         * port： 比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
         * perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
         * roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
         * anon： 比如/admins/**=anon 没有参数，表示可以匿名使用。
         * authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
         * authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
         * ssl：  比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
         * user： 比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
         */
        filterChainDefinitionMap.put("/login.jsp", "anon");
        filterChainDefinitionMap.put("/test/checkAuthc", "authc");
        filterChainDefinitionMap.put("/test/**", "anon");
        // druid过滤
        filterChainDefinitionMap.put("/druid", "anon");
        // swagger过滤, swagger-ui.html是swagger-ui的入口，/webjars/**和/swagger-resources/**里面是前端资源文件(js、css)
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        // 其他需要授权
        filterChainDefinitionMap.put("/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

}
