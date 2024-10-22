package com.koreait.app.biz.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutCommon {

    @Pointcut("execution(* com.koreait.app.biz..*Impl.*(..))")
    public void aPointcut() {} // 참조 메서드, 실질적인 기능이 있지않다

    @Pointcut("execution(* com.koreait.app.biz..*Impl.select*(..))")
    public void bPointcut() {}

    @Pointcut("execution(* com.koreait.app.biz.board..*Impl.insert(..))")
    public void cPointcut() {}
}
