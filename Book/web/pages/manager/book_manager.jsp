<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">

</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
%>
<jsp:include page="header.jsp">
    <jsp:param name="msg" value="图书管理系统"/>
</jsp:include>

<div id="main" style="height: 500px">
    <table>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td>图片</td>
            <td colspan="2">操作</td>
        </tr>

        <c:forEach items="${page.items}" var="book">
            <tr>
                <td>${book.name}</td>
                <td>${book.price}</td>
                <td>${book.author}</td>
                <td>${book.sales}</td>
                <td>${book.stock}</td>
                <td><img src="${book.imgPath}" width="50px" height="50px"></td>
                <td><a href="BookServlet?action=queryById&id=${book.id}&pageNo=${page.pageNo}">修改</a></td>
                    <%--						<td><a href="BookServlet?action=delete&id=${book.id}" onclick="return myDelete()">删除</a></td>--%>
                    <%--						<td><a href="javascript:void(0)" onclick="myDelete1('${book.id}','${book.name}')">删除</a></td>--%>
                <td><a href="javascript:void(0)" onclick="myDelete2('${book.id}',this,'${page.pageNo}')">删除</a></td>
            </tr>
        </c:forEach>

        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><a href="pages/manager/book_add.jsp">添加图书</a></td>
        </tr>
    </table>

    <jsp:include page="/pages/common/page.jsp"></jsp:include>
</div>

<%--	<%@include file="/pages/common/bottom.jsp" %>--%>
<jsp:include page="/pages/common/bottom.jsp"></jsp:include>
</body>
</html>
<script>
    function myDelete() {
        return confirm("你确定要删除?");
    }

    function myDelete1(id, name) {
        if (window.confirm("您确定要删除" + name + "吗")) {
            window.location.href = "BookServlet?action=delete&id=" + id;
        }
    }

    function myDelete2(id, obj, pageNo) {
        if (window.confirm("您确定要删除《" + obj.parentElement.parentElement.cells[0].innerHTML + "》吗")) {
            // window.location.href="BookServlet?action=delete&id="+id;
            window.location.href = "BookServlet?action=delete&id=" + id + "&pageNo=" + pageNo;
        }
    }

</script>