package com.koreait.app.biz.board;

import org.springframework.web.multipart.MultipartFile;

public class BoardDTO {
	private int bid;
	private String content;
	private String writer;
	
	private String condition; // 컨디션
	private String keyword; // 검색어
	private MultipartFile file; // 파일명
	private String path; // 경로

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
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
		return "BoardDTO{" +
				"bid=" + bid +
				", content='" + content + '\'' +
				", writer='" + writer + '\'' +
				", condition='" + condition + '\'' +
				", keyword='" + keyword + '\'' +
				", file=" + file +
				", path='" + path + '\'' +
				'}';
	}
}
