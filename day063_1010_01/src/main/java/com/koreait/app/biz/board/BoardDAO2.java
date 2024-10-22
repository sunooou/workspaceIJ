package com.koreait.app.biz.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO2 {
    private final String SELECTALL = "SELECT BOARD_NUM,BOARD_CONTENT,BOARD_WRITER_ID FROM BOARD";
    private final String SEARCH_CONTENT = "SELECT BOARD_NUM, BOARD_CONTENT, BOARD_WRITER_ID \r\n"
            + "FROM BOARD \r\n"
            + "WHERE BOARD_CONTENT LIKE CONCAT('%', ?, '%')"; // 내용으로 검색
    private final String SEARCH_WRITER = "SELECT BOARD_NUM,BOARD_CONTENT,BOARD_WRITER_ID FROM BOARD WHERE BOARD_WRITER_ID=?"; // 아이디로 검색

    private final String SELECTONE = "SELECT BOARD_NUM,BOARD_CONTENT,BOARD_WRITER_ID,BOARD_LOCATION FROM BOARD WHERE BOARD_NUM=?";

    private final String UPDATE = "UPDATE BOARD \n" +
            "SET BOARD_LOCATION = ? \n" +
            "WHERE BOARD_NUM = ?";

    private final String INSERT = "INSERT INTO BOARD (BOARD_NUM,BOARD_CONTENT,BOARD_WRITER_ID) VALUES(?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate; // 스프링부트 내장객체

    public List<BoardDTO> selectAll(BoardDTO boardDTO) {

        String sql="";

        if(boardDTO.getCondition().equals("SEARCH_CONTENT")&& boardDTO.getKeyword()!=null){
            System.out.println("내용으로 검색~~~");
            sql=SEARCH_CONTENT;
            System.out.println("내용 검색어 = [ "+boardDTO.getKeyword()+" ]");
        }
        else if (boardDTO.getCondition().equals("SEARCH_WRITER")&& boardDTO.getKeyword()!=null) {
            System.out.println("아이디로 검색~~~");
            sql=SEARCH_WRITER;
            System.out.println("아이디 검색어 = [ "+boardDTO.getKeyword()+" ]");
        }
        else if(boardDTO.getCondition().equals("ALL")) {
            sql=SELECTALL; // 전체 검색
        }
        else {
            System.err.println("사용자 입력 오류");
        }
        Object[] args= {boardDTO.getKeyword()};
        if(sql.equals(SELECTALL) || sql.equals("")) {
            return jdbcTemplate.query(sql, new BoardRowMapper());
        }
        else {
            return jdbcTemplate.query(sql, args, new BoardRowMapper());
        }
    }
    public BoardDTO selectOne(BoardDTO boardDTO) {

        Object[] args= {boardDTO.getBid()};
        return jdbcTemplate.queryForObject(SELECTONE,args,new BoardOneRowMapper());
    }
    public boolean insert(BoardDTO boardDTO) {

        int result=jdbcTemplate.update(INSERT,boardDTO.getBid(),boardDTO.getContent(),boardDTO.getWriter());
        if(result<=0) {
            return false;
        }
        return true;
    }
    public boolean update(BoardDTO boardDTO) {

        int result=jdbcTemplate.update(UPDATE,boardDTO.getPath(),boardDTO.getBid());
        if(result<=0) {
            return false;
        }
        return true;
    }
    public boolean delete(BoardDTO boardDTO) {
        return true;
    }
}

class BoardRowMapper implements RowMapper<BoardDTO> {

    public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BoardDTO boardDTO=new BoardDTO();
        boardDTO.setBid(rs.getInt("BOARD_NUM"));
        boardDTO.setContent(rs.getString("BOARD_CONTENT"));
        boardDTO.setWriter(rs.getString("BOARD_WRITER_ID"));
        return boardDTO;
    };
}
class BoardOneRowMapper implements RowMapper<BoardDTO> {

    public BoardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BoardDTO boardDTO=new BoardDTO();
        boardDTO.setBid(rs.getInt("BOARD_NUM"));
        boardDTO.setContent(rs.getString("BOARD_CONTENT"));
        boardDTO.setWriter(rs.getString("BOARD_WRITER_ID"));
        boardDTO.setPath(rs.getString("BOARD_LOCATION"));
        return boardDTO;
    };
}
