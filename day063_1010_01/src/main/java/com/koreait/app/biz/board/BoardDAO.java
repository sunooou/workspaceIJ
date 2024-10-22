package com.koreait.app.biz.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.koreait.app.biz.common.JDBCUtil;

@Repository
public class BoardDAO {
	private final String SELECTALL = "SELECT BOARD_NUM,BOARD_CONTENT,BOARD_WRITER_ID FROM BOARD";
	private final String SEARCH_CONTENT = "SELECT BOARD_NUM, BOARD_CONTENT, BOARD_WRITER_ID \r\n"
            								+ "FROM BOARD \r\n"
            								+ "WHERE BOARD_CONTENT LIKE CONCAT('%', ?, '%')"; // 내용으로 검색
	private final String SEARCH_WRITER = "SELECT BOARD_NUM,BOARD_CONTENT,BOARD_WRITER_ID FROM BOARD WHERE BOARD_WRITER_ID=?"; // 아이디로 검색
	
	private final String INSERT = "INSERT INTO BOARD (BOARD_CONTENT,BOARD_WRITER_ID) VALUES(?,?)";
	
	public List<BoardDTO> selectAll(BoardDTO boardDTO) {
		System.out.println("com.koreait.app.biz.board.selectAll 시작");
		List<BoardDTO> datas=new ArrayList<BoardDTO>();
		
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt=null;
		try {
			if(boardDTO.getCondition().equals("SEARCH_CONTENT")&& boardDTO.getKeyword()!=null) { // 제목으로 검색(검색어가 없을 때는 아무것도 반환x)
				System.out.println("내용으로 검색~~~");
				pstmt = conn.prepareStatement(SEARCH_CONTENT);
				
				System.out.println("내용 검색어 = [ "+boardDTO.getKeyword()+" ]");
				pstmt.setString(1, boardDTO.getKeyword().trim());
			}
			else if(boardDTO.getCondition().equals("SEARCH_WRITER")&& boardDTO.getKeyword()!=null) { // 아이디 검색(검색어가 없을 때는 아무것도 반환x)
				System.out.println("아이디로 검색~~~");
				
				pstmt = conn.prepareStatement(SEARCH_WRITER);
				System.out.println("아이디 검색어 = [ "+boardDTO.getKeyword()+" ]");
				pstmt.setString(1, boardDTO.getKeyword());				
			}
			else if(boardDTO.getCondition().equals("ALL")) {
				pstmt = conn.prepareStatement(SELECTALL); // 전체 검색
			}
			else {
				System.err.println("사용자 입력 오류");
				return datas;
			}
			ResultSet rs=pstmt.executeQuery();
			int i=1;
			while(rs.next()) {
				System.out.println(i);
				BoardDTO data=new BoardDTO();
				data.setBid(rs.getInt("BOARD_NUM"));
				data.setContent(rs.getString("BOARD_CONTENT"));
				data.setWriter(rs.getString("BOARD_WRITER_ID"));
				datas.add(data);
				i++;
			}
		} catch (SQLException e) {
			System.err.println("com.koreait.app.biz.board.selectAll SQL문 실패");
			e.printStackTrace();
		}	
		System.out.println("com.koreait.app.biz.board.selectAll 성공");
		System.out.println(datas);
		return datas;
	}
	public BoardDTO selectOne(BoardDTO boardDTO) {
		return null;
	}
	public boolean insert(BoardDTO boardDTO) {
		System.out.println("com.koreait.app.biz.board.insert 시작");
		Connection conn=JDBCUtil.connect();
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, boardDTO.getContent());
			pstmt.setString(2, boardDTO.getWriter());
			int result=pstmt.executeUpdate();
			if(result <= 0) {
				return false;
			}
		} catch (SQLException e) {
			System.err.println("com.koreait.app.biz.board.insert 실패");
			e.printStackTrace();
			return false;
		}
		System.out.println("com.koreait.app.biz.board.insert 성공");
		return true;
	}
	public boolean update(BoardDTO boardDTO) {
		return false;
	}
	public boolean delete(BoardDTO boardDTO) {
		return false;
	}
}
