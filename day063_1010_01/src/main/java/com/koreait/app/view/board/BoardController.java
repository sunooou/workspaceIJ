package com.koreait.app.view.board;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreait.app.biz.board.BoardDTO;
import com.koreait.app.biz.board.BoardService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private final String PATH = "D:\\kim1\\workspaceIJ\\day063_1010_01\\src\\main\\webapp\\images\\";

	@RequestMapping(value="/boardList.do", method=RequestMethod.POST)
	public @ResponseBody List<BoardDTO> boardList(@RequestBody BoardDTO boardDTO) {
		System.out.println("com.koreait.app.view.board.value=boardList.do 실행");

		System.out.println(boardDTO.getKeyword());
		System.out.println(boardDTO.getCondition());
		List<BoardDTO> datas=boardService.selectAll(boardDTO);
		System.out.println("boardList.do selectAll datas = ["+datas+"]");
		return datas;
	}

	@RequestMapping(value = "/board.do", method=RequestMethod.GET)
	public String board(Model model, BoardDTO boardDTO) {
		System.out.println("com.koreait.app.view.board.value=board.do 실행");

		BoardDTO data=boardService.selectOne(boardDTO);
		model.addAttribute("data", data);
		System.out.println("board.do selectOne data = ["+data+"]");
		return "board";
	}

	@RequestMapping(value = "/updateBoard.do", method=RequestMethod.POST)
	public String updateBoard(BoardDTO boardDTO) throws IOException {
		System.out.println("com.koreait.app.view.board.value=updateBoard.do 실행");

		MultipartFile file = boardDTO.getFile();
		String fileName = file.getOriginalFilename();

		file.transferTo(new File(PATH+fileName));

		boardDTO.setPath(fileName);
		boardService.update(boardDTO);

		return "redirect:main.do";
	}

	@RequestMapping(value="/main.do", method=RequestMethod.GET)
	public String main(BoardDTO boardDTO, Model model) {
		System.out.println("com.koreait.app.view.board.value=main.do 실행");
		boardDTO.setCondition("ALL");
		List<BoardDTO> datas=boardService.selectAll(boardDTO);
		model.addAttribute("datas", datas);
		return "main";
	}
	
	@RequestMapping(value="/insertBoard.do", method=RequestMethod.GET)
	public String insertBoard() {
		return "insertBoard";
	}
	@RequestMapping(value="/insertBoard.do", method=RequestMethod.POST)
	public String insertBoard(BoardDTO boardDTO) {
		System.out.println("com.koreait.app.view.board.value=insertBoard.do 실행");
		boolean flag=boardService.insert(boardDTO);
		System.out.println("insertBoard flag = ["+flag+"]");
		return "redirect:main.do";
	}
}
