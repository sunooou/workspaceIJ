
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 작성</title>
    <script src="./js/preview.js"></script>
</head>
<body>


내용 ${data.content} <br>
작성자 ${data.writer} <br>

<hr>

<c:if test="${empty data.path}">
    <img alt="기본 이미지" src="images/default.png">
</c:if>
<c:if test="${not empty data.path}">
    <img alt="비워두면 안됨" src="./images/${data.path}">
</c:if>

<form action="updateBoard.do" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="bid" value="${data.bid}">
    이미지 <input type="file" name="file" onchange="preview(event)">
    <br>
    <img id="previewImage" style="display:none;margin:5px;" alt="미리보기 이미지"><br>
    <input type="submit" value="이미지 변경">
</form>

<hr>

<a href="main.do">메인으로 이동</a>


</body>
</html>
