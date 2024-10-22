package com.koreait.app.view.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreait.app.biz.member.MemberDTO;
import com.koreait.app.biz.member.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String login() {
		return "redirect:login.jsp";
	}
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(Model model, HttpSession session, MemberDTO memberDTO) {
																// 커맨드 객체
		memberDTO.setCondition("SELECTONE");
		memberDTO = memberService.selectOne(memberDTO);
		
		String viewName="info";
		if(memberDTO != null) {
			System.out.println("로그인성공");
			session.setAttribute("userID", memberDTO.getMid());
			model.addAttribute("info_title", "로그인 성공~!");
			model.addAttribute("info_text", "success");
			model.addAttribute("info_error", "success");
			model.addAttribute("info_path", "main.do");
		}
		else {
			model.addAttribute("info_title", "로그인 실패ㅠㅠ");
			model.addAttribute("info_text", "error");
			model.addAttribute("info_error", "error");
			model.addAttribute("info_path", "login.do");
		}
		return viewName;
	}	
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("userID");
		return "redirect:login.do";
	}
}
