<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
	<head>
		<title>
			<fmt:message key="addOrderPageTitle"/>
		</title>
		<c:url value="/resources/css/common.css" var="commonCssUrl"/>
		<link rel="stylesheet" type="text/css" href="${commonCssUrl}" />
		<c:url value="/gwt_test_module/gwt_test_module.nocache.js" var="gwtUrl"/>
		<script type="text/javascript" src="${gwtUrl}"></script>
	</head>
	<body>
		<%@ include file="userInfo.jspf" %>
		<c:url value="/orders" var="formActionUrl"/>
		<form:form commandName="order" method="POST" action="${formActionUrl}">
			<div><fmt:message key="order.customerName"/>: <form:input path="customer"/></div>
			<div id="customer.errors" class="error"></div>
			<form:errors path="customer" element="div" cssClass="error"/>
			<div><fmt:message key="order.email"/>: <form:input path="email"/></div>
			<div id="email.errors" class="error"></div>
			<form:errors path="email" element="div" cssClass="error"/>
			<fmt:message key="saveButton" var="translatedSave"/>
			<button type="submit" id="save" name="save">${translatedSave}</button>
		</form:form>
	</body>
</html>
