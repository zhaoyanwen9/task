package com.nz.test.log;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 创建切面类 并注入spring容器
 */
@Aspect
@Component
public class LogAop {

    private static final Logger logger = LoggerFactory.getLogger(LogAop.class);

    /**
     * 抽取公共的切入点表达式
     * 1、本类引用
     * 2、其他的切面引用
     * 要拦截的方法：返回值类型 (*代表所有类型)  + 包名 + 方法名(*代表模糊匹配)+参数列表(..代表所有)
     */
    @Pointcut("execution(public * com.nz.test.controller.*Controller.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("1. 切入点: {}, 参数: {}", name, Arrays.asList(args));
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        logger.info("3. {}方法执行之后@after", name);
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturing(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        logger.info("2. 切入点: {}, 返回值: {}", name, new Gson().toJson(result));
    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        String name = joinPoint.getSignature().getName();
        logger.info("3. 切入点: {}, 异常: {}", name, ex);
    }
}