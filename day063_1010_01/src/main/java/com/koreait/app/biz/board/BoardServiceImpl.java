package com.koreait.app.biz.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO3 boardDAO;

	@Override
	public List<BoardDTO> selectAll(BoardDTO boardDTO) {
		System.out.println("boardService selectAll 시작");

		if (boardDTO.getKeyword() == null) {
			System.out.println("keyword == null");
		}
		else {
			System.out.println("keyword != null");
			if(boardDTO.getKeyword().equals("")) {
				System.out.println("keyword == ");
				return null;
			}
		}
		List<BoardDTO> datas = this.boardDAO.selectAll(boardDTO);
		System.err.println("boardService selectAll 종료 + datas = [");
		for(BoardDTO data : datas) {
			System.out.println(data);
		}
		System.err.println("]");
		return datas;
	}

	@Override
	public BoardDTO selectOne(BoardDTO boardDTO) {
		return boardDAO.selectOne(boardDTO);
	}

	@Override
	public boolean insert(BoardDTO boardDTO) {
		boardDAO.insert(boardDTO);// 트랜잭션 처리 확인용 코드
		return boardDAO.insert(boardDTO);
	}

	@Override
	public boolean update(BoardDTO boardDTO) {
		return boardDAO.update(boardDTO);
	}

	@Override
	public boolean delete(BoardDTO boardDTO) {
		return boardDAO.delete(boardDTO);
	}
}
