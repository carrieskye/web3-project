<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tourism</title>
<link rel="stylesheet" href="css/sample.css">
</head>
<body>
	<header role="banner"> <img alt="Toscane"
		src="images/toscaneRibbon.jpg"> </header>
	<main> 
	<h1>Login</h1>
	<form action="Controller?action=setlanguage" method="POST">
		<label>Choose your language</label> 
		<select name="language">
			<c:forEach var="language" items="${languages}">
				<option value="${language.code}">${language.description}</option>
			</c:forEach>
		</select>
		<button type="submit">Send</button>
	</form>
	<p>
		<a href="Controller?action=checklanguage">Check language</a>
	</p>
	<p>
		<a href="Controller?action=checkColor">Check color</a>
	</p>

	</main>

</body>
</html>