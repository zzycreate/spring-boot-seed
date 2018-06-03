package com.dazzlzy.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Controller注解的AOP切面类
 * AOP概念:
 * 1. 切面（Aspect）：官方的抽象定义为“一个关注点的模块化，这个关注点可能会横切多个对象”，在本例中，“切面”就是类TestAspect所关注的具体行为，例如，AServiceImpl.barA()的调用就是切面TestAspect所关注的行为之一。“切面”在ApplicationContext中<aop:aspect>来配置。
 * 2. 连接点（Joinpoint） ：程序执行过程中的某一行为，程序执行到的某个位置，例如，UserService.get的调用或者UserService.delete抛出异常等行为。
 * 3. 通知（Advice） ：“切面”对于某个“连接点”所产生的动作，切面要完成的工作，例如，TestAspect中对com.spring.service包下所有类的方法进行日志记录的动作就是一个Advice。其中，一个“切面”可以包含多个“Advice”，例如ServiceAspect。
 * 4. 切入点（Pointcut） ：匹配连接点的断言，在AOP中通知和一个切入点表达式关联。例如，TestAspect中的所有通知所关注的连接点，都由切入点表达式execution(* com.spring.service.*.*(..))来决定。
 * 5. 目标对象（Target Object） ：被一个或者多个切面所通知的对象。例如，AServcieImpl和BServiceImpl，当然在实际运行时，Spring AOP采用代理实现，实际AOP操作的是TargetObject的代理对象。
 * 6. AOP代理（AOP Proxy） ：在Spring AOP中有两种代理方式，JDK动态代理和CGLIB代理。默认情况下，TargetObject实现了接口时，则采用JDK动态代理，例如，AServiceImpl；反之，采用CGLIB代理，例如，BServiceImpl。强制使用CGLIB代理需要将 <aop:config>的 proxy-target-class属性设为true。
 * <p/>
 * 通知（Advice）类型：
 * 1. 前置通知（Before advice）：在某连接点（JoinPoint）之前执行的通知，但这个通知不能阻止连接点前的执行。ApplicationContext中在<aop:aspect>里面使用<aop:before>元素进行声明。例如，TestAspect中的doBefore方法。
 * 2. 后置通知（After advice）：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。ApplicationContext中在<aop:aspect>里面使用<aop:after>元素进行声明。例如，ServiceAspect中的returnAfter方法，所以Teser中调用UserService.delete抛出异常时，returnAfter方法仍然执行。
 * 3. 返回后通知（After return advice）：在某连接点正常完成后执行的通知，不包括抛出异常的情况。ApplicationContext中在<aop:aspect>里面使用<after-returning>元素进行声明。
 * 4. 环绕通知（Around advice）：包围一个连接点的通知，类似Web中Servlet规范中的Filter的doFilter方法。可以在方法的调用前后完成自定义的行为，也可以选择不执行。ApplicationContext中在<aop:aspect>里面使用<aop:around>元素进行声明。例如，ServiceAspect中的around方法。
 * 5. 抛出异常后通知（After throwing advice）：在方法抛出异常退出时执行的通知。ApplicationContext中在<aop:aspect>里面使用<aop:after-throwing>元素进行声明。例如，ServiceAspect中的returnThrow方法。
 * <p/>
 * 切入点表达式：
 * 通常使用execution可以完成大部分要求，表达式格式如下：
 * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
 * 1. modifiers-pattern：方法的操作权限
 * 2. ret-type-pattern：返回值
 * 3. declaring-type-pattern：方法所在的包
 * 4. name-pattern：方法名
 * 5. parm-pattern：参数名
 * 6. throws-pattern：异常
 *
 * @author dazzlzy
 * @date 2018/6/3
 */
@Slf4j
@Component
@Aspect
public class ControllerAspect {

    /**
     * 使用@Pointcut注解指明切入点
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@within(org.springframework.web.bind.annotation.GetMapping) || " +
            "@within(org.springframework.web.bind.annotation.PostMapping)")
    private void pointCut() {

    }

    /**
     * 使用{code @Before()}注解声明切点前置操作
     */
    @Before("pointCut()")
    public void doBefore() {
    }

    /**
     * 使用{code @After()}注解声明切点后置操作
     */
    @After("pointCut()")
    public void doAfter() {
    }

    /**
     * 使用{code @AfterThrowing()}注解声明抛出异常之后的操作
     *
     * @param e 切点抛出的异常
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void doAfterThrowing(Exception e) {
    }

    /**
     * 使用{code @AfterReturning()}注解声明获取返回值之后的操作
     *
     * @param result 返回值
     */
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void daAfterReturning(Object result) {
    }

    /**
     * 使用{code @Around()}注解环绕切点进行操作，方法参数为切点执行的操作类，在ProceedingJoinPoint.proceed()，前后可以进行相关操作
     *
     * @param pjp 切点执行实例
     * @return 返回结果
     * @throws Throwable 异常
     */
    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();

        //执行连接点的任务
        Object result = pjp.proceed();

        log.info("『返回值:』==> {}", JSON.toJSONString(result,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect));
        log.info("『请求耗时: {} ms』", (System.currentTimeMillis() - start));

        return result;
    }

}
