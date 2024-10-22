package com.koreait.app.biz.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.koreait.app.biz.common.JDBCUtil;

@Repository
public class MemberDAO {
	private final String SELECTONE = "SELECT MEMBER_ID,MEMBER_ROLE FROM MEMBER WHERE MEMBER_ID=? AND MEMBER_PASSWORD=?";
	private final String SELECTONE_CHECKMID = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID=?";

	private final String INSERT = "INSERT INTO MEMBER (MEMBER_ID, MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_LOCATION)\n" +
			"VALUES (?, ?, ?, ?, ?)";
	
	public List<MemberDTO> selectAll(MemberDTO memberDTO) {
		return null;
	}
	public MemberDTO selectOne(MemberDTO memberDTO) {
		System.out.println("com.koreait.app.biz.member.selectOne 시작");
		MemberDTO data=null;
		
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			if(memberDTO.getCondition().equals("SELECTONE")) { // 로그인
				pstmt = conn.prepareStatement(SELECTONE);
				System.out.println("아이디 = [ "+memberDTO.getMid()+" ]");
				pstmt.setString(1, memberDTO.getMid());
				System.out.println("패스워드 = [ "+memberDTO.getPassword()+" ]");
				pstmt.setString(2, memberDTO.getPassword());
			}
			else if (memberDTO.getCondition().equals("SELECTONE_CHECKMID")) { // 회원가입 로그인 중복 체크
				pstmt = conn.prepareStatement(SELECTONE_CHECKMID);
				System.out.println("아이디 = [ "+memberDTO.getMid()+" ]");
				pstmt.setString(1, memberDTO.getMid());
			}
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("com.koreait.app.biz.member.selectOne 검색 성공");
				data=new MemberDTO();
				data.setMid(rs.getString("MEMBER_ID"));
				data.setRole(rs.getString("MEMBER_ROLE"));
			}
		} catch (SQLException e) {
			System.err.println("com.koreait.app.biz.member.selectOne SQL문실패");
			e.printStackTrace();
		}
		System.out.println("com.koreait.app.biz.member.selectOne 성공");
		return  data;
	}
	public boolean insert(MemberDTO memberDTO) {
		System.out.println("com.koreait.app.biz.member.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt;

        try {
            pstmt = conn.prepareStatement(INSERT);
			System.out.println("아이디 = [ "+memberDTO.getMid()+" ]");
			pstmt.setString(1, memberDTO.getMid());
			System.out.println("패스워드 = [ "+memberDTO.getPassword()+" ]");
			pstmt.setString(2, memberDTO.getPassword());
			System.out.println("이름 = [ "+memberDTO.getName()+" ]");
			pstmt.setString(3, memberDTO.getName());
			System.out.println("전화번호 = [ "+memberDTO.getPhone()+" ]");
			pstmt.setInt(4, memberDTO.getPhone());
			System.out.println("지역 = [ "+memberDTO.getLocation()+" ]");
			pstmt.setString(5, memberDTO.getLocation());

			int rs=pstmt.executeUpdate();
			if(rs<=0) {
				System.err.println("com.koreait.app.biz.member.insert 실패");
				return false;
			}
        } catch (SQLException e) {
			System.err.println("com.koreait.app.biz.member.insert SQL문실패");
			e.printStackTrace();
        }
		System.out.println("com.koreait.app.biz.member.insert 성공");
		return true;
	}
	public boolean update(MemberDTO memberDTO) {
		return false;
	}
	public boolean delete(MemberDTO memberDTO) {
		return false;
	}
}
