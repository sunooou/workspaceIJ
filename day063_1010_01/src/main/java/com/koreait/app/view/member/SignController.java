package com.koreait.app.view.member;

import com.koreait.app.biz.member.MemberDTO;
import com.koreait.app.biz.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignController {
    @Autowired
    private MemberService memberService;

    @RequestMapping(value="/sign.do", method=RequestMethod.GET)
    public String sign() { return "redirect:sign.jsp"; }

    @RequestMapping(value = "/sign.do", method= RequestMethod.POST)
    public String sign(MemberDTO memberDTO) {
        memberService.insert(memberDTO);
        return "redirect:login.jsp";
    }
}
