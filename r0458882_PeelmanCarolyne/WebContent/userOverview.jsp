<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<%@include file="header.jspf"%>
		<jsp:include page="title.jsp">
			<jsp:param name="title" value="User Overview"/>
		</jsp:include>

		<main>
		<table>
			<tr>
				<th>E-mail</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th></th>
			</tr>
			<c:forEach var="person" items="${persons}">
				<tr>
					<td>${person.email}</td>
					<td>${person.firstName}</td>
					<td>${person.lastName}</td>
					<td><a id="remove" href="Controller?action=DeleteUserConfirmation&userid=${person.userid}">Remove</a></td>
				</tr>
			</c:forEach>
			<caption>Users Overview</caption>
		</table>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>