<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
	<style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 20px;
            background-color: #ffffff;
        }
        h1 {
            color: #333;
        }
        hr {
            margin: 20px 0;
        }
        form {
            margin-bottom: 20px;
	        display: flex;
	        align-items: center;
	    }
	    select, input[type="text"] {
	        padding: 10px;
	        margin-right: 10px;
	        border: 1px solid #ccc;
	        border-radius: 5px;
	        font-size: 16px;
	    }
	    input[type="submit"] .logout-link, .write-link {
	        background-color: #007BFF;
	        color: white;
	        padding: 10px 15px;
	        border: none;
	        border-radius: 5px;
	        text-decoration: none;
	        font-size: 16px;
	        cursor: pointer;
	        transition: background-color 0.3s;
	    }
	    input[type="submit"]:hover, .logout-link:hover, .write-link:hover {
	        background-color: #0056b3;
	    }
        .board-list {
            list-style-type: none;
            padding: 0;
        }
        .board-item {
            background: #fff;
            margin: 10px 0;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .board-title {
            font-size: 18px;
            font-weight: bold;
        }
        .board-writer {
            color: #555;
            font-size: 14px;
        }
        
    </style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="js/boardList.js"></script>
</head>
<body>

<h1>${userID}님, 안녕하세요! :D</h1>

<hr>

<a href="logout.do" class="logout-link">로그아웃</a>

<hr>
	<select id="condition">
		<option value="SEARCH_WRITER">작성자로 검색</option>
		<option value="SEARCH_CONTENT">글내용으로 검색</option>
	</select>
	<input type="text" id="keyword" placeholder="검색어 입력"/>
	<input type="button" id="searchKeyword" value="검색"/>
<hr>

<h3>글 목록</h3>
<ul class="board-list" id="board-list">
	<c:forEach var="data" items="${datas}">
		<a href="board.do?bid=${data.board_num}">
			<li class="board-item">
				<span class="board-title">${data.board_content}</span><br>
				<span class="board-writer">작성자: ${data.board_writer}</span>
			</li>
		</a>
	</c:forEach>
</ul>

<hr>

<a href="insertBoard.do" class="write-link">글 작성</a>

</body>
</html>