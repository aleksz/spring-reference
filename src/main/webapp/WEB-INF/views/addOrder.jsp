<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
	<head>
		<title>
			<fmt:message key="addOrderPageTitle"/>
		</title>
	</head>
	<body>
		<c:url value="/orders" var="formActionUrl"/>
		<form:form commandName="order" method="POST" action="${formActionUrl}">
			<div><fmt:message key="order.customerName"/>: <form:input path="customer"/></div>
			<form:errors path="customer" element="div"/>
			<div><fmt:message key="order.email"/>: <form:input path="email"/></div>
			<form:errors path="email" element="div"/>
			<fmt:message key="saveButton" var="translatedSave"/>
			<input type="submit" name="save" value="${translatedSave}"/>
		</form:form>
	</body>
</html>
