<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Sign Up</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<%@include file="header.jspf"%>
		<jsp:include page="title.jsp">
			<jsp:param name="title" value="Sign Up" />
		</jsp:include>

		<c:if test="${fn:length(result) gt 0}">
			<div class="alert-danger">
				<main> <c:forEach var="error" items="${result}">
					<ul>
						<li>${error}</li>
					</ul>
				</c:forEach>
			</div>
		</c:if>



		<form method="post" action="Controller?action=addUser"
			novalidate="novalidate">
			<!-- novalidate in order to be able to run tests correctly -->
			<p>
				<label for="userid">User id</label><input type="text" id="userid"
					name="userid" required value="<c:out value='${useridPreviousValue}'/>">
			</p>
			<p>
				<label for="firstName">First Name</label><input type="text"
					id="firstName" name="firstName" required
					value="<c:out value='${firstNamePreviousValue}'/>">
			</p>
			<p>
				<label for="lastName">Last Name</label><input type="text"
					id="lastName" name="lastName" required
					value="<c:out value='${lastNamePreviousValue}'/>">
			</p>
			<p>
				<label for="email">Email</label><input type="email" id="email"
					name="email" required value="<c:out value='${emailPreviousValue}'/>">
			</p>
			<p>
				<label for="password">Password</label><input type="password"
					id="password" name="password" required value="<c:out value=''/>">
			</p>
			<p>
				<input type="submit" id="signUp" value="Sign Up">
			</p>

		</form>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>
