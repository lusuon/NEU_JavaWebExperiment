<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="goods.DAO.impl.GoodsDAOImpl" %><%--
  Created by IntelliJ IDEA.
  User: Jackson Ma
  Date: 2018-11-18
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%
    GoodsDAOImpl goodsDAO = new GoodsDAOImpl();
    request.setAttribute("good",goodsDAO.getSimple(request.getParameter("id")));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>编辑产品</title>
</head>
<body>
<form action="/goods.do" method="post">

    <table border="1" cellpadding="0" cellspacing="0">
        <c:choose>
            <c:when test="${param.id eq null}">
                <tr>
                    <td>商品ID</td>
                    <td><input type="text" name="id"/></td>
                    <td><input type="hidden" name="add" value="add"></td>
                </tr>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="id" value="${good.id}"/>
            </c:otherwise>
        </c:choose>
        <tr>
            <td>商品名</td>
            <td><input type="text" name="name" value="${good.name}"/></td>
        </tr>
        <tr>
            <td>厂商</td>
            <td><input type="text" name="factory" value="${good.factory}"/></td>
        </tr>
        <tr>
            <td>类别</td>
            <td><input type="text" name="category" value="${good.category}"/></td>
        </tr>
        <tr>
            <td>型号</td>
            <td><input type="text" name="type" value="${good.type}"/></td>
        </tr>
        <tr>
            <td>产地</td>
            <td><input type="text" name="origin" value="${good.origin}"/></td>
        </tr>
        <tr>
            <td>描述</td>
            <td><input type="text" name="description" value="${good.description}"/></td>
        </tr>
        <tr>
            <input type="hidden" name="cmd" value="save">
            <td colspan="2"><input type="submit" value="保存"/></td>
        </tr>
    </table>
</form>
</body>
</html>
