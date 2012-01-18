<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Orders</title>
	</head>
	<body>
		<c:forEach items="${orderList}" var="order">
			<div>
				<a href="orders/${order.id}">
					${order.customer} 
					<fmt:formatDate value="${order.date}" type="both"/>
				</a>
			</div>
		</c:forEach>
		
		<a id="addNewOrder" href="orders/add">
			<fmt:message key="addNewOrder"/>
		</a>
	</body>
</html>
