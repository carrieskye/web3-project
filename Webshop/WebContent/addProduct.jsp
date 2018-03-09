<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Add Product</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<%@include file="header.jspf"%>
		<jsp:include page="title.jsp">
			<jsp:param name="title" value="Add product" />
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



		<form method="post" action="Controller?action=addProduct"
			novalidate="novalidate">
			<!-- novalidate in order to be able to run tests correctly -->
			<p>
				<label for="name">Name</label><input type="text" id="name"
					name="name" required value="<c:out value='${namePreviousValue}'/>">
			</p>
			<p>
				<label for="description">Description</label><input type="text"
					id="description" name="description" required
					value="<c:out value='${descriptionPreviousValue}'/>">
			</p>
			<p>
				<label for="price">Price</label><input type="number" id="price"
					name="price" required value="${pricePreviousValue}">
			</p>
			<p>
				<input type="submit" id="addProduct" value="Add Product">
			</p>

		</form>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>
