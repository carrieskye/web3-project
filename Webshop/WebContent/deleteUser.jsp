<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Delete User</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<%@include file="header.jspf"%>
		<jsp:include page="title.jsp">
			<jsp:param name="title" value="Delete user" />
		</jsp:include>

		<main>
		<p>Are you sure you want to remove ${firstname} ${lastname}?
		<p>
		<p>
			<a id="confirm" href="Controller?action=deleteUser&userid=${userid}">OK</a>
		</p>
		<p>
			<a href="Controller?action=userOverview">Cancel</a>
		</p>


		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>
