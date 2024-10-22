package com.coma.app.biz.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class MemberDAO {
	//아이디로 찾기 FIXME 관리자 권한 추가(char값 'T','F')
	private final String SEARCH_ID = "SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_ID = ?";

	//아이디 비밀번호로 찾기
	private final String SEARCH_ID_PASSWORD="SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_ID = ? AND MEMBER_PASSWORD= ? ";

	//크루에 속한 회원목록 조회
	private final String SEARCH_CREW="SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_CREW_NUM = ?";

	//랭킹높은순으로 정렬 관리자 FIXME 관리자 권한이 아닌사람들만
	private final String SEARCH_RANK="SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_ROLE='F'\r\n"
			+ "ORDER BY MEMBER_TOTAL_POINT DESC";

	//회원가입
	private final String INSERT="INSERT INTO MEMBER(MEMBER_ID,MEMBER_NAME,MEMBER_PASSWORD,MEMBER_PHONE,MEMBER_LOCATION) \r\n"
			+ "VALUES(?,?,?,?,?)";

	//회원탈퇴
	private final String DELETE="DELETE FROM MEMBER WHERE MEMBER_ID = ?";

	//회원정보 업데이트 MEMBER_PASSWORD, MEMBER_PROFILE, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
	private final String UPDATE_ALL="UPDATE MEMBER\r\n"
			+ "SET\r\n"
			+ "	MEMBER_PASSWORD = ?,\r\n"
			+ "	MEMBER_PROFILE = ?,\r\n"
			+ "	MEMBER_PHONE = ?,\r\n"
			+ "	MEMBER_LOCATION = ?\r\n"
			+ "WHERE MEMBER_ID = ?";

	//회원정보 업데이트 (profile X) MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
	private final String UPDATE_WITHOUT_PROFILE="UPDATE MEMBER\r\n"
			+ "SET\r\n"
			+ "	MEMBER_PASSWORD = ?,\r\n"
			+ "	MEMBER_PHONE = ?,\r\n"
			+ "	MEMBER_LOCATION = ?\r\n"
			+ "WHERE MEMBER_ID = ?";

	//크루가입 (크루가입시 가입날짜입력때문에 분리)
	private final String UPDATE_CREW = "UPDATE MEMBER\r\n" //FIXME 관리자 권한이 아닌 사람들만
			+ "SET\r\n"
			+ "	MEMBER_CREW_NUM = 1,\r\n"
			+ "	MEMBER_CREW_JOIN_DATE = SYSDATE()\r\n"
			+ "WHERE MEMBER_ID = ?";

	//관리자 권한 변경 ROLE, ID
	private final String UPDATE_ADMIN ="UPDATE MEMBER SET MEMBER_ROLE = '?' WHERE MEMBER_ID = '?'";

	//관리자가 아닌 신규회원 출력 (기간 7일)
	private final String ALL_NEW ="SELECT MEMBER_ID,MEMBER_PASSWORD,MEMBER_NAME,MEMBER_PHONE,MEMBER_PROFILE,MEMBER_REGISTRATION_DATE,MEMBER_CURRENT_POINT,MEMBER_TOTAL_POINT,MEMBER_CREW_NUM,MEMBER_CREW_JOIN_DATE,MEMBER_LOCATION,MEMBER_ROLE\r\n"
			+ "FROM MEMBER\r\n"
			+ "WHERE MEMBER_REGISTRATION_DATE >= DATE_ADD(SYSDATE(), INTERVAL - (INTERVAL '7' DAY) * 86400 SECOND) AND ROLE='F'";

	//크루 랭킹 상위 10개 조회
	private final String ALL_TOP10_CREW_RANK = "SELECT\r\n"
			+ "    ROWNUM AS RANKING,\r\n"
			+ "    SUB.CREW_PROFILE,\r\n"
			+ "    SUB.CREW_NAME,\r\n"
			+ "    MEMBER_CREW_RANK\r\n"
			+ "FROM (\r\n"
			+ "    SELECT\r\n"
			+ "        C.CREW_PROFILE,\r\n"
			+ "        C.CREW_NAME,\r\n"
			+ "        SUM(M.MEMBER_TOTAL_POINT) AS MEMBER_CREW_RANK\r\n"
			+ "    FROM\r\n"
			+ "        MEMBER M\r\n"
			+ "    JOIN\r\n"
			+ "        CREW C \r\n"
			+ "    ON \r\n"
			+ "        M.MEMBER_CREW_NUM = C.CREW_NUM\r\n"
			+ "    GROUP BY\r\n"
			+ "        C.CREW_PROFILE,\r\n"
			+ "        C.CREW_NAME\r\n"
			+ "    ORDER BY\r\n"
			+ "        MEMBER_CREW_RANK DESC\r\n"
			+ ") SUB\r\n"
			+ "LIMIT 10";

	//상위 개인 랭킹 10개
	private final String ALL_TOP10_RANK = "SELECT\r\n"
			+ "    MEMBER_NAME,\r\n"
			+ "    MEMBER_PROFILE\r\n"
			+ "FROM (\r\n"
			+ "    SELECT\r\n"
			+ "        MEMBER_NAME,\r\n"
			+ "        MEMBER_PROFILE\r\n"
			+ "    FROM\r\n"
			+ "        MEMBER\r\n"
			+ "    ORDER BY\r\n"
			+ "        MEMBER_TOTAL_POINT DESC\r\n"
			+ ") s\r\n"
			+ "LIMIT 10";

	//특정 크루에 속한 사용자 이름 전부 조회 CREW_NUM
	private final String ALL_SEARCH_CREW_MEMBER_NAME = "SELECT MEMBER_NAME FROM MEMBER M JOIN CREW C ON C.CREW_NUM = M.MEMBER_CREW_NUM WHERE CREW_NUM = ?";

	//특정 사용자가 속한 크루 찾기 MEMBER_ID
	private final String SEARCH_MY_CREW = "SELECT\r\n"
			+ "	M.MEMBER_CREW_NUM\r\n"
			+ "FROM\r\n"
			+ "	MEMBER M\r\n"
			+ "JOIN\r\n"
			+ "	CREW C\r\n"
			+ "ON\r\n"
			+ "	M.MEMBER_CREW_NUM = C.CREW_NUM\r\n"
			+ "WHERE\r\n"
			+ "	MEMBER_ID = ?";

	//크루 랭킹 전체 출력
	private final String ALL_CREW_RANK = "SELECT\r\n"
			+ "	C.CREW_NUM,\r\n"
			+ "    C.CREW_NAME,\r\n"
			+ "    C.CREW_LEADER,\r\n"
			+ "    C.CREW_MAX_MEMBER_SIZE,\r\n"
			+ "    COUNT(M.MEMBER_ID) AS CREW_CURRENT_SIZE,\r\n"
			+ "    SUM(M.MEMBER_TOTAL_POINT) AS MEMBER_TOTAL_POINT\r\n"
			+ "FROM\r\n"
			+ "    CREW C\r\n"
			+ "JOIN\r\n"
			+ "    MEMBER M \r\n"
			+ "ON \r\n"
			+ "    M.MEMBER_CREW_NUM = C.CREW_NUM\r\n"
			+ "GROUP BY\r\n"
			+ "	C.CREW_NUM,\r\n"
			+ "    C.CREW_NAME,\r\n"
			+ "    C.CREW_LEADER,\r\n"
			+ "    C.CREW_MAX_MEMBER_SIZE\r\n"
			+ "ORDER BY\r\n"
			+ "    MEMBER_TOTAL_POINT DESC";

	//사용자 포인트 업데이트 MEMBER_CURRENT_POINT, MEMBER_ID
	private final String UPDATE_CURRENT_POINT = "UPDATE MEMBER SET MEMBER_CURRENT_POINT = ? WHERE MEMBER_ID = ?";


	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.insert 시작");

		//회원가입 MEMBER_ID ,MEMBER_NAME, MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION
		int result=jdbcTemplate.update(INSERT,memberDTO.getMember_id(),memberDTO.getMember_name(),memberDTO.getMember_password(),memberDTO.getMember_phone(),memberDTO.getMember_location());
		if(result<=0) {
			System.out.println("com.coma.app.biz.member.insert SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.insert 성공");
		return true;
	}

	public boolean update(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.update 시작");

		int result;

		//회원정보 업데이트 MEMBER_PASSWORD, MEMBER_PROFILE, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
		if(memberDTO.getMember_condition().equals("MEMBER_UPDATE_ALL")) {
			result=jdbcTemplate.update(UPDATE_ALL,memberDTO.getMember_password(),memberDTO.getMember_profile(),memberDTO.getMember_phone(),memberDTO.getMember_location(),memberDTO.getMember_id());
		}
		//회원정보 업데이트 (profile X) MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
		else if(memberDTO.getMember_condition().equals("MEMBER_UPDATE_WITHOUT_PROFILE")) {
			result=jdbcTemplate.update(UPDATE_WITHOUT_PROFILE,memberDTO.getMember_password(),memberDTO.getMember_phone(),memberDTO.getMember_location(),memberDTO.getMember_id());
		}
		//크루가입 MEMBER_CREW_NUM, MEMBER_ID
		else if(memberDTO.getMember_condition().equals("MEMBER_UPDATE_CREW")) {
			result=jdbcTemplate.update(UPDATE_CREW,memberDTO.getMember_crew_num(),memberDTO.getMember_id());
		}
		//관리자 권한 변경 MEMBER_ROLE, MEMBER_ID
		else if(memberDTO.getMember_condition().equals("MEMBER_UPDATE_ADMIN")) {
			result=jdbcTemplate.update(UPDATE_ADMIN,memberDTO.getMember_role(),memberDTO.getMember_id());
		}
		//사용자 포인트 업데이트 MEMBER_CURRENT_POINT, MEMBER_ID
		else if(memberDTO.getMember_condition().equals("MEMBER_UPDATE_CURRENT_POINT")) {
			result=jdbcTemplate.update(UPDATE_CURRENT_POINT,memberDTO.getMember_current_point(),memberDTO.getMember_id());
		}
		else {
			System.err.println("컨디션 입력 오류");
			return false;
		}

		if(result<=0) {
			System.out.println("com.coma.app.biz.member.update SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.update 성공");
		return true;
	}

	public boolean delete(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.delete 시작");

		//회원탈퇴 MEMBER_ID
		int result=jdbcTemplate.update(DELETE,memberDTO.getMember_id());
		if(result<=0) {
			System.out.println("com.coma.app.biz.member.delete SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.member.delete 성공");
		return true;
	}

	public MemberDTO selectOne(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectOne 시작");

		Object[] args={};
		String sql="";
		MemberDTO data=null;

		//아이디로 검색 MEMBER_ID
		if(memberDTO.getMember_condition().equals("MEMBER_SEARCH_ID")) {
			sql=SEARCH_ID;
			args= new Object[]{memberDTO.getMember_id()};
		}
		//아이디,비밀번호로 검색 MEMBER_ID, MEMBER_PASSWORD
		else if(memberDTO.getMember_condition().equals("MEMBER_SEARCH_ID_PASSWORD")) {
			sql=SEARCH_ID_PASSWORD;
			args= new Object[]{memberDTO.getMember_id(), memberDTO.getMember_password()};
		}
		//특정 사용자가 속한 크루 찾기 MEMBER_ID
		else if(memberDTO.getMember_condition().equals("MEMBER_SEARCH_MY_CREW")) {
			sql=SEARCH_MY_CREW;
			args= new Object[]{memberDTO.getMember_id()};
		}
		else {
			System.out.println("com.coma.app.biz.member.selectOne SQL문 실패");
			return null;
		}

		if(sql.equals(SEARCH_MY_CREW)) {
			data=jdbcTemplate.queryForObject(sql, args, new MemberSearchCrewRowMapper());
		}
		else {
			data=jdbcTemplate.queryForObject(sql, args, new MemberSelectOneRowMapper());
		}
		System.out.println("com.coma.app.biz.member.selectOne 성공");
		return data;
	}

	public List<MemberDTO> selectAll(MemberDTO memberDTO) {
		System.out.println("com.coma.app.biz.member.selectAll 시작");
		Object[] args={memberDTO.getMember_crew_num()};


		String sql="";
		String sqlq; // 쿼리수행결과 구분용 데이터

		//랭킹높은순으로 정렬
		if(memberDTO.getMember_condition().equals("MEMBER_SEARCH_RANK")) {
			sql=SEARCH_RANK;
			sqlq="all";
		}
		//크루 랭킹 높은순으로 전체 출력
		else if(memberDTO.getMember_condition().equals("MEMBER_ALL_CREW_RANK")) {
			sql=ALL_CREW_RANK;
			sqlq="all";
		}
		//관리자가 아닌 신규회원 출력 (기간 7일)
		else if(memberDTO.getMember_condition().equals("MEMBER_ALL_NEW")) {
			sql=ALL_NEW;
			sqlq="all";
		}
		//크루 랭킹 상위 10개
		else if(memberDTO.getMember_condition().equals("MEMBER_ALL_TOP10_CREW_RANK")) {
			sql=ALL_TOP10_CREW_RANK;
			sqlq="all";
		}
		//개인 랭킹 상위  10개
		else if(memberDTO.getMember_condition().equals("MEMBER_ALL_TOP10_RANK")) {
			sql=ALL_TOP10_RANK;
			sqlq="all";
		}
		//크루에 속한 회원목록 조회 MEMBER_CREW_NUM
		else if(memberDTO.getMember_condition().equals("MEMBER_SEARCH_CREW")) {
			sql=SEARCH_CREW;
			sqlq="search";
		}
		//특정 크루에 속한 사용자 이름 전부 조회 CREW_NUM
		else if(memberDTO.getMember_condition().equals("MEMBER_ALL_SEARCH_CREW_MEMBER_NAME")) {
			sql=ALL_SEARCH_CREW_MEMBER_NAME;
			sqlq="search";
		}
		else {
			System.err.println("컨디션 입력 오류");
			return null;
		}

		List<MemberDTO> datas=null;
		if(sqlq.equals("all")) {
			System.out.println("com.coma.app.biz.member.selectAll 성공");
			datas=jdbcTemplate.query(sql, new MemberRowMapper());
		}
		else if(sqlq.equals("search")) {
			System.out.println("com.coma.app.biz.member.selectAll 성공");
			datas=jdbcTemplate.query(sql, args, new MemberRowMapper());
		}
		else {
			System.out.println("com.coma.app.biz.member.selectAll SQL문 실패");
			return null;
		}
		return datas;
	}
}
//CREW_NUM,\r\n"
//		+ "    C.CREW_NAME,\r\n"
//		+ "    C.CREW_LEADER,\r\n"
//		+ "    C.CREW_MAX_MEMBER_SIZE,\r\n"
//		+ "    COUNT(M.MEMBER_ID) AS CREW_CURRENT_SIZE,\r\n"
//		+ "    SUM(M.MEMBER_TOTAL_POINT) AS MEMBER_TOTAL_POINT
class MemberSelectOneRowMapper implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_id(rs.getString("MEMBER_ID"));
		System.err.println("member_id = ["+memberDTO.getMember_id()+"]");
		memberDTO.setMember_password(rs.getString("MEMBER_PASSWORD"));
		System.err.println("member_password = ["+memberDTO.getMember_password()+"]");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		System.err.println("member_name = ["+memberDTO.getMember_name()+"]");
		memberDTO.setMember_phone(rs.getString("MEMBER_PHONE"));
		System.err.println("member_phone = ["+memberDTO.getMember_phone()+"]");
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		System.err.println("member_profile = ["+memberDTO.getMember_profile()+"]");
		memberDTO.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE"));
		System.err.println("member_registration_date = ["+memberDTO.getMember_registration_date()+"]");
		memberDTO.setMember_current_point(rs.getInt("MEMBER_CURRENT_POINT"));
		System.err.println("member_current_point = ["+memberDTO.getMember_current_point()+"]");
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		System.err.println("member_total_point = ["+memberDTO.getMember_total_point()+"]");
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		System.err.println("member_crew_num = ["+memberDTO.getMember_crew_num()+"]");
		memberDTO.setMember_crew_join_date(rs.getString("MEMBER_CREW_JOIN_DATE"));
		System.err.println("member_crew_join_date = ["+memberDTO.getMember_crew_join_date()+"]");
		memberDTO.setMember_location(rs.getString("MEMBER_LOCATION"));
		System.err.println("member_location = ["+memberDTO.getMember_location()+"]");
		memberDTO.setMember_role(rs.getString("MEMBER_ROLE"));
		System.err.print("member_role = ["+memberDTO.getMember_role()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberSelectOneRowMapper implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_id(rs.getString("MEMBER_ID"));
		System.err.println("member_id = ["+memberDTO.getMember_id()+"]");
		memberDTO.setMember_password(rs.getString("MEMBER_PASSWORD"));
		System.err.println("member_password = ["+memberDTO.getMember_password()+"]");
		memberDTO.setMember_name(rs.getString("MEMBER_NAME"));
		System.err.println("member_name = ["+memberDTO.getMember_name()+"]");
		memberDTO.setMember_phone(rs.getString("MEMBER_PHONE"));
		System.err.println("member_phone = ["+memberDTO.getMember_phone()+"]");
		memberDTO.setMember_profile(rs.getString("MEMBER_PROFILE"));
		System.err.println("member_profile = ["+memberDTO.getMember_profile()+"]");
		memberDTO.setMember_registration_date(rs.getDate("MEMBER_REGISTRATION_DATE"));
		System.err.println("member_registration_date = ["+memberDTO.getMember_registration_date()+"]");
		memberDTO.setMember_current_point(rs.getInt("MEMBER_CURRENT_POINT"));
		System.err.println("member_current_point = ["+memberDTO.getMember_current_point()+"]");
		memberDTO.setMember_total_point(rs.getInt("MEMBER_TOTAL_POINT"));
		System.err.println("member_total_point = ["+memberDTO.getMember_total_point()+"]");
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		System.err.println("member_crew_num = ["+memberDTO.getMember_crew_num()+"]");
		memberDTO.setMember_crew_join_date(rs.getString("MEMBER_CREW_JOIN_DATE"));
		System.err.println("member_crew_join_date = ["+memberDTO.getMember_crew_join_date()+"]");
		memberDTO.setMember_location(rs.getString("MEMBER_LOCATION"));
		System.err.println("member_location = ["+memberDTO.getMember_location()+"]");
		memberDTO.setMember_role(rs.getString("MEMBER_ROLE"));
		System.err.print("member_role = ["+memberDTO.getMember_role()+"]");
		System.out.println("}");
		return memberDTO;
	};
}

class MemberSearchCrewRowMapper implements RowMapper<MemberDTO> {

	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberDTO memberDTO=new MemberDTO();
		System.out.print("DB에서 가져온 데이터 {");
		memberDTO.setMember_crew_num(rs.getInt("MEMBER_CREW_NUM"));
		System.err.print("member_crew_num = ["+memberDTO.getMember_crew_num()+"]");
		System.out.println("}");
		return memberDTO;
	};
}
