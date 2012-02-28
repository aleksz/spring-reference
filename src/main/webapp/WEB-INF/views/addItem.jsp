<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
	<head>
		<title>
			<fmt:message key="addItemPageTitle"/>
		</title>
		<c:url value="/resources/css/common.css" var="commonCssUrl"/>
		<link rel="stylesheet" type="text/css" href="${commonCssUrl}" />
	</head>
	<body>
		<%@ include file="userInfo.jspf" %>
		<c:url value="/orders/${item.order.id}/items" var="formActionUrl"/>
		<form:form commandName="item" method="POST" action="${formActionUrl}">
			<form:errors path="order.*" element="div" cssClass="error"/>
			<div><fmt:message key="order.item.product"/>: <form:input path="product"/></div>
			<form:errors path="product" element="div" cssClass="error"/>
			<div><fmt:message key="order.item.price"/>: <form:input path="price"/></div>
			<form:errors path="price" element="div" cssClass="error"/>
			<div><fmt:message key="order.item.quantity"/>: <form:input path="quantity"/></div>
			<form:errors path="quantity" element="div" cssClass="error"/>
			<fmt:message key="saveButton" var="translatedSave"/>
			<input type="submit" name="save" value="${translatedSave}"/>
		</form:form>
	</body>
</html>
