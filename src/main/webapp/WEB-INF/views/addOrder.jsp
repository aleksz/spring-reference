<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<html>
<head>
	<title>Order</title>
</head>
<body>
	<c:url value="/orders" var="formActionUrl"/>
	<form:form commandName="order" method="POST" action="${formActionUrl}">
		<div>Customer name <form:input path="customer"/></div>
		<form:errors path="customer" element="div"/>
		<div>Email <form:input path="email"/></div>
		<form:errors path="email" element="div"/>
		<input type="submit" value="Save"/>
	</form:form>
</body>
</html>
