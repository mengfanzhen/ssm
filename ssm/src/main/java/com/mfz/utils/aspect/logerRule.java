package com.mfz.utils.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by fanzhenmeng on 2017/2/15.
 */
@Aspect
@Component
public class logerRule {
    @Pointcut("execution(* com.mfz.function.testfunction.controller.*Controller*(..))")
    public void init(){

    }
}
