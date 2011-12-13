<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<html>
<head>
	<title>Order</title>
</head>
<body>
	<c:url value="/order/${item.order.id}/item" var="formActionUrl"/>
	<form:form commandName="item" method="POST" action="${formActionUrl}">
		<form:input path="product"/>
		<form:errors path="product" element="div"/>
		<form:input path="price"/>
		<form:errors path="price" element="div"/>
		<form:input path="quantity"/>
		<form:errors path="quantity" element="div"/>
		<input type="submit" value="Save"/>
	</form:form>
</body>
</html>
