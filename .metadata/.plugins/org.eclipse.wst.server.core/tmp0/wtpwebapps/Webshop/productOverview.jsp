<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Product Overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<%@include file="header.jspf"%>
		<jsp:include page="title.jsp">
			<jsp:param name="title" value="Product Overview"/>
		</jsp:include>

		<main>
		<table>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Price</th>
			</tr>
			<c:forEach var="product" items="${products}">
				<tr>
					<td>${product.name}</td>
					<td>${product.description}</td>
					<td>${product.price}</td>
				</tr>
			</c:forEach>
			<caption>Product Overview</caption>
		</table>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>