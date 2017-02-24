package com.mfz.utils.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by fanzhenmeng on 2017/2/14.
 */
@Component
@Aspect
public class LogAspect {
    @Pointcut("execution(* com.mfz..*(..))")
    public void init(){

    }


    @Before(value="init()")
    public void before(){
        System.out.println("方法执行前执行.....");
    }

    @After(value="init()")
    public void after(){
        System.out.println("方法执行之后.....");
    }
    @AfterReturning(value="init()")
    public void afterReturn(){
        System.out.println("返回参数以后.....");
    }

    @Around(value = "@within(org.springframework.stereotype.Controller)")
    public Object controllerMonitor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("controller begin");
        Object obj =  proceedingJoinPoint.proceed();
        System.out.println("controller end");
        return obj;
    }

}
