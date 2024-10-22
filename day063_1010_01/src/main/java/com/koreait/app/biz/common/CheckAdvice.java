package com.koreait.app.biz.common;

import com.koreait.app.biz.member.MemberDTO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class CheckAdvice {

    @AfterReturning(pointcut = "PointcutCommon.bPointcut()",returning = "returnObj")
    public void check(Object returnObj) {
        if(returnObj instanceof MemberDTO) { // 클래스 명칭확인
            MemberDTO memberDTO=(MemberDTO)returnObj;
            if (memberDTO.getRole().equals("F")) { // 멤버가 회원이라면
                System.out.println("회원이 로그인했습니다.");
            }
            else { // 멤버가 관리자라면
                System.out.println("관리자가 로그인했습니다.");
            }
        }
    }
}
