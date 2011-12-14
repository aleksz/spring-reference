<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Orders</title>
</head>
<body>
	<c:forEach items="${orderList}" var="order">
		<div>
			<a href="orders/${order.id}">${order.customer}</a>
		</div>
	</c:forEach>
	
	<a href="orders/add">Add new order</a>
</body>
</html>
