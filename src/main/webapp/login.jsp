<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='spring' uri='http://www.springframework.org/tags' %>

<!DOCTYPE html>

<html>

<head>
    <title>
    	<spring:message code="loginPageTitle"/>
    </title>

    <c:url value="/resources/" var="resources"/>
    <link rel="stylesheet" href="${resources}/css/openid.css" />
	<c:url value="/resources/css/common.css" var="commonCssUrl"/>
    <link rel="stylesheet" type="text/css" href="${commonCssUrl}" />
    <script type="text/javascript" src="${resources}/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="${resources}/js/jquery.openid.js"></script>
</head>

<body>
	<div id="container">
		<div id="leftPanel">
			<%@ include file="openIdLogin.jspf" %>
		</div>
		<div id="rightPanel">
			<%@ include file="idCardLogin.jspf" %>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>