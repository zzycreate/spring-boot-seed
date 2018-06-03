package com.dazzlzy.common.filter;

import com.dazzlzy.common.utils.RandomUtil;
import com.dazzlzy.common.utils.SessionUtil;
import com.dazzlzy.springbootseed.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用Slf4j的MDC制作的过滤器,记录Servlet请求信息
 * 使用WebFilter注解向web容器中注入过滤器，类似spring中web.xml配置的filter及filter-mapping，filterName为filter的名称，urlPatterns为过滤的请求
 * WebFilter注解方式和注入FilterRegistrationBean实现效果一致
 * 如果要对Filter进行排序，可以使用@Order注解进行排序
 *
 * @author dazzlzy
 * @date 2018/6/3
 */
@Slf4j
@WebFilter(filterName = "mdcFilter", urlPatterns = "/*")
public class MdcFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            mdcFilter(request, response);
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            log.error("MdcFilter catch IOException!", e);
            throw e;
        } catch (ServletException e) {
            log.error("MdcFilter catch ServletException!", e);
            throw e;
        } catch (Exception e) {
            log.error("MdcFilter catch Exception!", e);
            throw e;
        } finally {
            MDC.clear();
        }
    }


    @Override
    public void destroy() {

    }

    /**
     * 向Slf4j的MDC中写入MDC_ID和USER_NAME
     *
     * @param request  请求
     * @param response 响应
     */
    private void mdcFilter(HttpServletRequest request, HttpServletResponse response) {
        String mdcid = RandomUtil.getShortUUID();
        User user = SessionUtil.getCurrentUser();
        String userName = user == null ? "anonymous" : user.getUserName();

        MDC.clear();
        MDC.put("MDC_ID", mdcid);
        MDC.put("USER_NAME", userName);
    }
}
