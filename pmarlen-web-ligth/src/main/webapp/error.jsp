<%-- 
    Document   : error
    Created on : 08-ene-2015, 8:36:34
    Author     : alfredo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"     prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"      prefix="fmt" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Show Error Page</title>
	</head>
	<body>
		<h1>ERROR</h1>
		<table width="100%" border="1">
			<tr valign="top">
				<td width="40%"><b>Error:</b></td>
				<td>${pageContext.exception}</td>
			</tr>
			<tr valign="top">
				<td><b>URI:</b></td>
				<td>${pageContext.errorData.requestURI}</td>
			</tr>
			<tr valign="top">
				<td><b>Status code:</b></td>
				<td>${pageContext.errorData.statusCode}</td>
			</tr>
			<tr valign="top">
				<td><b>Stack trace:</b></td>
				<td>
					<c:forEach var="trace" 
							   items="${pageContext.exception.stackTrace}">
						<p>${trace}</p>
					</c:forEach>
				</td>
			</tr>
			<h1><a href="<%=request.getContextPath()%>/">REGRESAR A PAGINA INICIAL</a></h1>
		</table>
	</body>
</html>