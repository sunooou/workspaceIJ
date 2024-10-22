<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
	Swal.fire({
		title: '${info_title}',
		   text: '${info_text}',
		   icon: '${info_error}'                       
	})
	.then(result => {
   		location.href='${info_path}'
	});
	

</script>


</body>

</html>