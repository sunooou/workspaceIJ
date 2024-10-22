package com.koreait.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.app.biz.member.MemberDTO;
import com.koreait.app.biz.member.MemberService;

@Controller
public class CheckController {
	
	@Autowired
	private MemberService memberservice; // DAO == 결합도를 낮추기 위해 의존 주입 하지 않는다


	@RequestMapping(value="/checkMID.do", method=RequestMethod.POST)
	public @ResponseBody String checkMID(MemberDTO memberDTO) { // 커맨드 객체로 삼으면 mid에 자동 바인딩
		System.err.println("com.koreait.app.view.member.CheckController 비동기 처리 도착 완료");
		
		memberDTO.setCondition("SELECTONE_CHECKMID"); // condition 설정후
		memberDTO=memberservice.selectOne(memberDTO); // mid 체크해서 db에서 가져온다
		
		String result="false"; // 겹치지않는다면 == 중복 되지 않는다
			// 1. new를 쓰지않았다
			// 2. 메서드 내에서 선언 >> 메서드 종료시 사라진다
			// 결론 == 무겁지않고 가독성은 높이고 결합도는 낮췄다
		
		if(memberDTO!=null) { // 겹친다면 == 중복 된다
			result="true"; // 그럼 true로 값 저장
				// 그냥 string 값으로 보내면 handlermapper를 통해 viewresolver가 수행을 한다
				// 그럼 result의 값은 ./true.jsp >> 이 페이지는 없다
				// 결론 == vs의 개입을 막아야한다
		}
		return result;
	}
}
