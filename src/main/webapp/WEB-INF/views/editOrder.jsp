<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
<head>
	<title>
		<fmt:message key="editOrderPageTitle"/>
	</title>
	<c:url value="/resources/css/common.css" var="commonCssUrl"/>
	<link rel="stylesheet" type="text/css" href="${commonCssUrl}" />
</head>
<body>
	
	<%@ include file="userInfo.jspf" %>
	
	<c:url value="/orders/${order.id}" var="formActionUrl"/>
	
	<form:form commandName="order" method="POST" action="${formActionUrl}">
		<div>
			<span><fmt:message key="order.customerName"/>: </span>
			<span id="customerLabel">${order.customer}</span>
		</div>
		<div><fmt:message key="order.email"/>: <form:input path="email"/></div>
		<form:errors path="email" element="div" cssClass="error"/>
		<fmt:message key="saveButton" var="translatedSave"/>
		<input type="submit" name="save" value="${translatedSave}"/>
	</form:form>
	
	<form:form commandName="order" method="DELETE" action="${formActionUrl}">
		<fmt:message key="deleteButton" var="translatedDelete"/>
		<input type="submit" name="delete" value="${translatedDelete}"/>
	</form:form>
	
	<c:forEach items="${order.items}" var="item">
		<div class="item">${item.product} ${item.quantity} x
			<fmt:formatNumber type="currency" value="${item.price}" currencySymbol="$"/>
		</div>
	</c:forEach>
	
	<a id="addItemUrl" href="${formActionUrl}/items/add">
		<fmt:message key="addItem"/>
	</a>
	
	<div>${remoteAddr}</div>
</body>
</html>
