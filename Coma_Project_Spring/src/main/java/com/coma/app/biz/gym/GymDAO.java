package com.coma.app.biz.gym;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GymDAO {
	//(페이지 네이션) 암벽장 전체출력
	private String ALL = "SELECT \r\n"
			+ "    GYM_NUM, \r\n"
			+ "    GYM_NAME, \r\n"
			+ "    GYM_PROFILE, \r\n"
			+ "    GYM_DESCRIPTION, \r\n"
			+ "    GYM_LOCATION, \r\n"
			+ "    GYM_RESERVATION_CNT, \r\n"
			+ "    GYM_PRICE, \r\n"
			+ "    BATTLE_NUM, \r\n"
			+ "    BATTLE_GAME_DATE\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        G.GYM_NUM, \r\n"
			+ "        G.GYM_NAME, \r\n"
			+ "        G.GYM_PROFILE, \r\n"
			+ "        G.GYM_DESCRIPTION, \r\n"
			+ "        G.GYM_LOCATION, \r\n"
			+ "        G.GYM_RESERVATION_CNT, \r\n"
			+ "        G.GYM_PRICE, \r\n"
			+ "        B.BATTLE_NUM, \r\n"
			+ "        B.BATTLE_GAME_DATE,\r\n"
			+ "        ROW_NUMBER() OVER (PARTITION BY G.GYM_NAME ORDER BY G.GYM_NUM) AS RN_G,"  // GYM_NAME별로 순번 부여\r\n
			+ "        ROW_NUMBER() OVER (ORDER BY G.GYM_NUM) AS ROW_INDEX\r\n"
			+ "    FROM \r\n"
			+ "        GYM G\r\n"
			+ "    LEFT JOIN \r\n"
			+ "        BATTLE B ON G.GYM_NUM = B.BATTLE_GYM_NUM\r\n"
			+ ") AS GYM_BATTLE_CTE\r\n"
			+ "WHERE RN_G = 1\r\n"
			+ "ORDER BY GYM_NUM\r\n"
			+ "LIMIT ?, ?";  // ?는 시작 인덱스와 행 수를 위한 자리 표시자입니다.

	//암벽장 총 개수
	private final String ONE_COUNT = "SELECT COUNT(*) AS GYM_TOTAL FROM GYM";
	
	//암벽장 PK로 검색 GYM_NUM
	private final String ONE = "SELECT\r\n"
			+ "	G.GYM_NUM,\r\n"
			+ "	G.GYM_NAME,\r\n"
			+ "	G.GYM_PROFILE,\r\n"
			+ "	G.GYM_DESCRIPTION,\r\n"
			+ "	G.GYM_LOCATION,\r\n"
			+ "	G.GYM_RESERVATION_CNT,\r\n"
			+ "	G.GYM_PRICE,\r\n"
			+ "	B.BATTLE_NUM,\r\n"
			+ "	B.BATTLE_GAME_DATE\r\n"
			+ "FROM\r\n"
			+ "	GYM G\r\n"
			+ "LEFT JOIN\r\n"
			+ "	BATTLE B\r\n"
			+ "ON\r\n"
			+ "	G.GYM_NUM = B.BATTLE_GYM_NUM\r\n"
			+ "WHERE\r\n"
			+ "	G.GYM_NUM = ?";
	
	//예약가능 개수 업데이트 GYM_RESERVATION_CNT, GYM_NUM
	private final String UPDATE_RESERVATION_CNT = "UPDATE GYM SET GYM_RESERVATION_CNT = ? WHERE GYM_NUM = ?";
	
	//암벽장 등록 GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION
	private final String INSERT = "INSERT INTO GYM (GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION) " 
			+ "VALUES (?, ?, ?, ?)";

	@Autowired
	private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

	public boolean insert(GymDTO gymDTO) {
		System.out.println("com.coma.app.biz.gym.insert 시작");
		//암벽장 등록 GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION
		int result=jdbcTemplate.update(INSERT,gymDTO.getGym_name(),gymDTO.getGym_profile(),gymDTO.getGym_description(),gymDTO.getGym_location());
		if(result<=0) {
			System.out.println("com.coma.app.biz.gym.insert SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.gym.insert 성공");
		return true;
	}

	public boolean update(GymDTO gymDTO) {
		System.out.println("com.coma.app.biz.gym.update 시작");
		//예약가능 개수 업데이트 GYM_RESERVATION_CNT, GYM_NUM
		int result=jdbcTemplate.update(UPDATE_RESERVATION_CNT, gymDTO.getGym_reservation_cnt(),gymDTO.getGym_num());
		if(result<=0) {
			System.out.println("com.coma.app.biz.gym.update SQL문 실패");
			return false;
		}
		System.out.println("com.coma.app.biz.gym.update 성공");
		return true;
	}

	public boolean delete(GymDTO gymDTO) { // TODO 여기없는 CRUD
		System.err.println("com.coma.app.biz.gym.delete 시작");
		return false;
	}

	public GymDTO selectOne(GymDTO gymDTO){
		System.out.println("com.coma.app.biz.gym.selectOne 시작");
		GymDTO data=null;
		Object[] args= {gymDTO.getGym_num()};
		try {
			//암벽장 PK로 검색 GYM_NUM
			data= jdbcTemplate.queryForObject(ONE, new GymSelectRowMapperOneAll());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.gym.selectOne SQL문 실패");
		}
		System.out.println("com.coma.app.biz.gym.selectOne 성공");
		return data;
	}

	public GymDTO selectOneCount(GymDTO gymDTO){
		System.out.println("com.coma.app.biz.gym.selectOneCount 시작");
		GymDTO data=null;
		try {
			//암벽장 총 개수
			data= jdbcTemplate.queryForObject(ONE_COUNT, new GymCountRowMapper());
		}
		catch (Exception e) {
			System.out.println("com.coma.app.biz.gym.selectOneCount SQL문 실패");
		}
		System.out.println("com.coma.app.biz.gym.selectOneCount 성공");
		return data;
	}


	public List<GymDTO> selectAll(GymDTO gymDTO){
		System.out.println("com.coma.app.biz.gym.selectAll 시작");

		Object[] args= {gymDTO.getGym_min_num(),6};
		//(페이지 네이션) 암벽장 전체출력
		List<GymDTO> datas=jdbcTemplate.query(ALL,args,new GymSelectRowMapperOneAll());
		System.out.println("com.coma.app.biz.gym.selectAll 성공");
		return datas;
	}
}

class GymSelectRowMapperOneAll implements RowMapper<GymDTO> {

	public GymDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GymDTO gymDTO=new GymDTO();
		System.out.print("GymSelectRowMapper DB에서 가져온 데이터 {");
		gymDTO.setGym_num(rs.getInt("GYM_NUM"));
		System.err.println("gym_num = ["+gymDTO.getGym_num()+"]");
		gymDTO.setGym_name(rs.getString("GYM_NAME"));
		System.err.println("gym_name = ["+gymDTO.getGym_name()+"]");
		gymDTO.setGym_profile(rs.getString("GYM_PROFILE"));
		System.err.println("gym_profile = ["+gymDTO.getGym_profile()+"]");
		gymDTO.setGym_description(rs.getString("GYM_DESCRIPTION"));
		System.err.println("gym_description = ["+gymDTO.getGym_description()+"]");
		gymDTO.setGym_location(rs.getString("GYM_LOCATION"));
		System.err.println("gym_location = ["+gymDTO.getGym_location()+"]");
		gymDTO.setGym_reservation_cnt(rs.getInt("GYM_RESERVATION_CNT"));
		System.err.println("gym_reservation = ["+gymDTO.getGym_reservation_cnt()+"]");
		gymDTO.setGym_price(rs.getString("GYM_PRICE"));
		System.err.println("gym_price = ["+gymDTO.getGym_price()+"]");
		gymDTO.setGym_battle_num(rs.getInt("GYM_BATTLE_NUM"));
		System.err.println("gym_battle_num = ["+gymDTO.getGym_battle_num()+"]");
		gymDTO.setGym_battle_game_date(rs.getString("GYM_BATTLE_GAME_DATE"));
		System.err.print("gym_battle_game_date = ["+gymDTO.getGym_battle_game_date()+"]");
		System.out.println("}");
		return gymDTO;
	};
}

class GymCountRowMapper implements RowMapper<GymDTO> {

	public GymDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GymDTO gymDTO=new GymDTO();
		System.out.print("GymCountRowMapper DB에서 가져온 데이터 {");
		gymDTO.setGym_total(rs.getInt("GYM_TOTAL"));
		System.err.print("gym_total = ["+gymDTO.getGym_total()+"]");
		System.out.println("}");
		return gymDTO;
	};
}
