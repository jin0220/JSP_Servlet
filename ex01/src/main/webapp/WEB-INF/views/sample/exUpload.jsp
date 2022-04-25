<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>exUpload</title>
</head>
<body>
	<form action="/sample/exUploadPost" method="post"
		enctype="multiPart/form-data">
		 
		<div>
			<input type='file' name='files'>
		</div>
		<div>
			<input type='file' name='files'>
		</div>
		<div>
			<input type='file' name='files'>
		</div>
		<div>
			<input type='file' name='files'>
		</div>
		<div>
			<input type='file' name='files'>
		</div>
		<div>
			<input type='submit'>
		</div>
	</form>

</body>
</html>