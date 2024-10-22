<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
	<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .sign-container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
        }
        td {
            padding: 10px;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="js/checkID.js"></script>

</head>
<body>

<div class="sign-container">
    <h2>회원가입</h2>
    <form action="sign.do" method="POST">
        <table>
            <tr>
                <td>아이디</td>
                <td><input type="text" id="mid" name="mid" required><span id="result"></span></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td>이름</td>
                <td><input type="text" name="name" required></td>
            </tr>
            <tr>
                <td>전화번호</td>
                <td><input type="text" name="phone" required></td>
            </tr>
            <tr>
                <td>지역</td>
                <td>
                    <select name="location">
                        <option>서울특별시</option>
                        <option>충청남도</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <input type="submit" value="회원가입">
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>