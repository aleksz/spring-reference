<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<html>
<head>
	<title>Order</title>
</head>
<body>
	<c:url value="/orders/${order.id}" var="formActionUrl"/>
	
	<form:form commandName="order" method="POST" action="${formActionUrl}">
		<div>
			<span>Customer name:</span>
			<span id="customerLabel">${order.customer}</span>
		</div>
		<div>Email: <form:input path="email"/></div>
		<form:errors path="email" element="div"/>
		<input type="submit" name="save" value="Save"/>
	</form:form>
	
	<form:form commandName="order" method="DELETE" action="${formActionUrl}">
		<input type="submit" name="delete" value="Delete"/>
	</form:form>
	
	<c:forEach items="${order.items}" var="item">
		<div class="item">${item.product} ${item.quantity} x ${item.price}$</div>
	</c:forEach>
	
	<a href="${formActionUrl}/items/add">Add item</a>
	
	<div>${remoteAddr}</div>
</body>
</html>
