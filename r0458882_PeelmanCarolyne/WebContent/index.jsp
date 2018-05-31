<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body class="${color}">
	<div id="container">
		<%@include file="header.jspf"%>
		<jsp:include page="title.jsp">
			<jsp:param name="title" value="Home" />
		</jsp:include>

		<main>
		<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem
			accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae
			ab illo inventore veritatis et quasi architecto beatae vitae dicta
			sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit
			aspernatur aut odit aut fugit, sed quia consequuntur magni dolores
			eos qui ratione voluptatem sequi nesciunt.</p>
		<c:if test="${fn:length(result) gt 0}">
			<div class="alert-danger">
				<c:forEach var="error" items="${result}">
					<ul>
						<li>${error}</li>
					</ul>
				</c:forEach>
			</div>
		</c:if> <c:choose>
			<c:when test="${not userLoggedIn}">
				<form method="post" action="Controller?action=LogIn"
					novalidate="novalidate">
					<p>
						<label for="userid">Your userid</label><input type="text"
							id="userid" name="userid" required
							value="<c:out value="${userid}"/>">
					</p>

					<p>
						<label for="password">Your password</label><input type="password"
							id="password" name="password" required>
					</p>

					<p>
						<label for="remember">Remember me</label> <input type="checkbox"
							name="remember">
					</p>

					<p>
						<input type="submit" id="login" value="Log In">
					</p>

					<p>
						<input type="checkbox" name="background" value="pink"
							${pinkRegistered ? 'checked=checked' : ''}> Background
						color pink
					</p>

				</form>
			</c:when>
			<c:otherwise>
				<p>Welcome, ${userid}!</p>

				<form method="post" action="Controller?action=LogOut"
					novalidate="novalidate">

					<p>
						<input type="submit" id="logout" value="Log out">
					</p>

				</form>
			</c:otherwise>
		</c:choose> </main>

		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>