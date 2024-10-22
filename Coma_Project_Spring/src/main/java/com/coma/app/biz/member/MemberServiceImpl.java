package com.coma.app.biz.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO memberDAO;

	@Override
	public List<MemberDTO> selectAll(MemberDTO memberDTO) {
		return this.memberDAO.selectAll(memberDTO);
	}

	@Override
	public MemberDTO selectOne(MemberDTO memberDTO) {
		return this.memberDAO.selectOne(memberDTO);
	}

	@Override
	public boolean insert(MemberDTO memberDTO) {
		return this.memberDAO.insert(memberDTO);
	}

	@Override
	public boolean update(MemberDTO memberDTO) {
		return this.memberDAO.update(memberDTO);
	}

	@Override
	public boolean delete(MemberDTO memberDTO) {
		return this.memberDAO.delete(memberDTO);
	}
	
	
	
	
}
