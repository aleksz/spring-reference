<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<html>
	<head>
		<title>
			<fmt:message key="ordersPageTitle"/>
		</title>
		<c:url value="/resources/css/common.css" var="commonCssUrl"/>
		<link rel="stylesheet" type="text/css" href="${commonCssUrl}" />
	</head>
	<body>
	
		<%@ include file="userInfo.jspf" %>
	
		<c:forEach items="${page.content}" var="order">
			<div>
				<a href="orders/${order.id}">
					${order.customer} 
					<spring:eval expression="order.date"/>
					<%-- <fmt:formatDate value="${order.date}" type="both"/> --%>
				</a>
			</div>
		</c:forEach>
		
		<c:if test="${page.totalPages > 1}">
			<div id="pages">
				<c:forEach begin="1" end="${page.totalPages}" var="i">
					<c:choose>
						<c:when test="${(i - 1) == page.number}">
							${i}
						</c:when>
						<c:otherwise>
							<a href="?page=${i - 1}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</c:if>
		
		<a id="addNewOrder" href="orders/add">
			<fmt:message key="addNewOrder"/>
		</a>
	</body>
</html>
