<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<html>
<head>
	<title>Order</title>
</head>
<body>
	<c:url value="/order/${order.id}" var="formActionUrl"/>
	
	<form:form commandName="order" method="POST" action="${formActionUrl}">
		<form:input path="customer"/>
		<input type="submit" value="Save"/>
	</form:form>
	
	<form:form commandName="order" method="DELETE" action="${formActionUrl}">
		<input type="submit" value="Delete"/>
	</form:form>
</body>
</html>
