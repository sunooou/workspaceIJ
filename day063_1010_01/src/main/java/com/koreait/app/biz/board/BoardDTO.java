package com.koreait.app.biz.board;

import org.springframework.web.multipart.MultipartFile;

public class BoardDTO {
	private int board_num;
	private String board_content;
	private String board_writer;
	
	private String condition; // 컨디션
	private String keyword; // 검색어
	private MultipartFile file; // 파일명
	private String path; // 경로

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}

	public String getBoard_content() {
		return board_content;
	}

	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}

	public String getBoard_writer() {
		return board_writer;
	}

	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "BoardDTO [board_num=" + board_num + ", board_content=" + board_content + ", board_writer="
				+ board_writer + ", condition=" + condition + ", keyword=" + keyword + ", file=" + file + ", path="
				+ path + "]";
	}
}
