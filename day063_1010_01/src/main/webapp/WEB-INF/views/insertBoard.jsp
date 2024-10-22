<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성 페이지</title>
	<style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 20px;
            background-color: #ffffff; /* 밝은 흰색 배경 */
        }
        h1 {
            color: #333;
            text-align: center; /* 제목 중앙 정렬 */
        }
        form {
            margin: 20px auto; /* 중앙 정렬 */
            max-width: 400px; /* 최대 너비 설정 */
            display: flex;
            flex-direction: column;
        }
        label {
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"] {
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .back-link {
            display: inline-block; /* 블록으로 만들어 여백을 주기 */
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            text-align: center;
            text-decoration: none;
            margin-top: 20px; /* 위쪽 여백 */
            transition: background-color 0.3s;
        }
        .back-link:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>글 작성</h1>

<form action="insertBoard.do" method="POST">
    <label for="content">내용</label>
    <input type="text" name="content" id="content" required> 
    <label for="writer">작성자</label>
    <input type="text" name="writer" id="writer" value="${userID}" readonly> 
    <input type="submit" value="글 작성">
</form>

<hr>

<a href="main.do" class="back-link">메인으로 이동</a>

</body>
</html>