package com.koreait.app.biz.common;


import com.koreait.app.biz.board.BoardDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class BoardInsertAdvice {

    @AfterReturning("PointcutCommon.cPointcut()")
    public void printLog(JoinPoint jp) { //바인드 변수
        // 현재 이 어드바이스랑 연결된 조인포인트의 메서드명
        // == 포인트컷의 메서드명
        String methodName=jp.getSignature().getName();
        System.out.println("BoardInsertAdvice 메서드 = ["+methodName+"]");

        // 현재 이 어드바이스랑 연결된 조인포인트의 매개변수 정보
        // == 포인트컷의 매개변수 정보
        Object[] args=jp.getArgs();

        if(args[0] instanceof BoardDTO) { // instanceof는 다운캐스팅시 꼭 따라다닌다
            BoardDTO boardDTO=(BoardDTO)args[0];

            System.out.println(boardDTO.getWriter()+"님의 글이 DB에 정상적으로 등록되었습니다");
        }
    }
}
