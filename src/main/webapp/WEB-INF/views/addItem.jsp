<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<html>
<head>
	<title>Order</title>
</head>
<body>
	<c:url value="/orders/${item.order.id}/items" var="formActionUrl"/>
	<form:form commandName="item" method="POST" action="${formActionUrl}">
		<form:errors element="div"/>
		<div>Product name: <form:input path="product"/></div>
		<form:errors path="product" element="div"/>
		<div>Price: <form:input path="price"/></div>
		<form:errors path="price" element="div"/>
		<div>Quantity: <form:input path="quantity"/></div>
		<form:errors path="quantity" element="div"/>
		<input type="submit" value="Save"/>
	</form:form>
</body>
</html>
