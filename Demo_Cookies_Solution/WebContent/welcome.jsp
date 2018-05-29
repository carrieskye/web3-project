<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/sample.css">
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<main>
	<article>
		<h1>${greeting}</h1>
		<form action="Controller" method="GET">
			<input type="hidden" name = "action" value="checklanguage" />
			<input type="submit" value="Check language" />
		</form>
	</article>
	</main>
</body>
</html>