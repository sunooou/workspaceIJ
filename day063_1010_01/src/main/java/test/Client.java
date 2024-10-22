package test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.koreait.app.biz.member.MemberService;

public class Client {
	public static void main(String[] args) {
		AbstractApplicationContext factory = new GenericXmlApplicationContext("applicationContext.xml");
		// 컨테이너를 구동시키는 코드
		
		MemberService ms=(MemberService)factory.getBean("memberService");
		ms.selectAll(null);
				
		factory.close();
	}
}
