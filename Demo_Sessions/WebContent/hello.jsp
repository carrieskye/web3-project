<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/sample.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Hello</title>
</head>
<body>
<head>
</head>
<main>
<article>
	<h2>Hello ${user.name}</h2>
	<p><a href="helloagain.jsp">More information about ${user.name}</a></p>
	<p><a href="Controller?action=helloagain">Or even more information about ${user.name}</a></p>
</article>
</main>
</body>
</html>