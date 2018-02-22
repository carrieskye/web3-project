<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<header>
			<h1>
				<span>Web shop</span>
			</h1>
			<nav>
				<ul>
					<li><a href="Controller">Home</a></li>
					<li><a href="Controller?action=userOverview">Users</a></li>
					<li><a href="Controller?action=productOverview">Products</a></li>
					<li id="actual"><a href="Controller?action=addProduct">Add
							Product</a></li>
					<li><a href="Controller?action=signUp">Sign up</a></li>
				</ul>
			</nav>
			<h2>Add Product</h2>

		</header>
		<main> <c:choose>
			<c:when test="${errormessage != null}">
				<div class="alert-danger">
					<ul>
						<c:forEach var="string" items="${errormessage}">
							<li>${string}</li>
						</c:forEach>
					</ul>
				</div>
			</c:when>

		</c:choose>



		<form method="post" action="Controller?action=newProduct"
			novalidate="novalidate">
			<!-- novalidate in order to be able to run tests correctly -->
			<p>
				<label for="name">Name</label><input type="text" id="name"
					name="name" required value="${namePreviousValue}">
			</p>
			<p>
				<label for="description">Description</label><input type="text"
					id="description" name="description" required
					value="${descriptionPreviousValue}">
			</p>
			<p>
				<label for="price">Price</label><input type="text"
					id="price" name="price" required
					value="${pricePreviousValue}">
			</p>
			<p>
				<input type="submit" id="add" value="Add">
			</p>

		</form>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>
