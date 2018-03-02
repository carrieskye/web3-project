<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/sample.css">
<meta charset="UTF-8">
<title>Countries</title>
</head>
<body>
	<%@include file="header.jspf" %>
	<main id="container"> <jsp:include page="title.jsp">
		<jsp:param value="Countries" name="title" />
	</jsp:include>
	<article>

		<p>The most popular country is ${popular.name}.</p>
		<table id="overview">
			<tr>
				<th>Name</th>
				<th>Capital</th>
				<th class="getal">Inhabitants</th>
				<th class="getal">Votes</th>
			</tr>
			<c:forEach var="country" items="${countries}">
				<tr>
					<td>${country.name}</td>
					<td>${country.capital}</td>
					<td class="getal">${country.numberInhabitants}</td>
					<td class="getal">${country.votes}</td>
				</tr>
			</c:forEach>
		</table>
		<p>
			<a href="countryForm.jsp">Add new country</a>
		</p>
	</article>
	</main>
</body>
</html>