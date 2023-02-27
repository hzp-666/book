<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
	<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>

</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
			<span class="wel_word">订单详情</span>
			<div>
				<span>欢迎<span class="um_span">${user.username}</span>光临网上书城</span>
				<a href="OrderServlet?action=listOrder">我的订单</a>
				<a href="UserServlet?action=logout">注销</a>&nbsp;&nbsp;
				<a href="OrderServlet?action=listOrder">返回</a>
			</div>
	</div>
	
	<div id="main">
		
		<table>
			<tr>
				<td>书名</td>
				<td>数量</td>
				<td>单价</td>
				<td>总价</td>
				<td>订单号</td>
			</tr>
			<c:forEach items="${page.items}" var="order">
				<tr>
					<td>${order.name}</td>
					<td>${order.count}</td>
					<td>${order.price}</td>
					<td>${order.totalPrice}</td>
					<td>${order.orderId}</td>
				</tr>
			</c:forEach>

		</table>
		<jsp:include page="/pages/common/page.jsp"></jsp:include>
	
	</div>

<%--	<%@include file="/pages/common/bottom.jsp" %>--%>
	<jsp:include page="/pages/common/bottom.jsp"></jsp:include>
</body>
</html>