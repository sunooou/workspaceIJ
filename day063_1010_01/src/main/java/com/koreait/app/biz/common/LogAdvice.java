package com.koreait.app.biz.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Aspect
public class LogAdvice {

    @Around("PointcutCommon.aPointcut()") // 전 후 실행
    public Object printLog(ProceedingJoinPoint jp) {

        String methodName = jp.getSignature().getName(); // 메서드명 받아와서
        System.out.println("공통 관심 - 로그 : [" + methodName + "] 비즈니스 메서드 수행 전에 호출 됨"); // 잘 진입했는지 확인하는 용도

        Object result = null;
        try {
            if ("insert".equals(methodName) || "update".equals(methodName) || "delete".equals(methodName)) {
                System.out.println("DB 접근 발생");

                result=jp.proceed(); // 메서드 수행
                if((Boolean)result) { // 성공했다면 (다운캐스팅)
                    System.out.println("\u001B[34m 변경 완료: " + methodName+"\u001B[0m");
                }
            }
            else{
                System.out.println("DB 접근 xx");
                result=jp.proceed(); // 메서드 수행
            }
        } catch (Throwable throwable) {
            System.out.println("메서드 호출 실패: " + methodName);
        }
        if(!(result instanceof List || result instanceof Boolean)) {
            System.out.println("반환값 로그 = ["+result.getClass().getSimpleName()+"]");
        }
        return result;
    }
}
