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
		<form:input path="customer"/>
		<form:errors path="customer" element="div"/>
		<input type="submit" value="Save"/>
	</form:form>
</body>
</html>
