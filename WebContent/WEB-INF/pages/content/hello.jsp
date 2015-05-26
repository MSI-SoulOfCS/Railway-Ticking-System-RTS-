<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello Page</title>
</head>
<body>
<%@page import="com.mercury.demand.persistence.model.User1"%>
<jsp:useBean id="user1DaoImpl" scope="request" class="com.mercury.result.Result"/>
<h1><font color="blue">${title}</font></h1>
<table border="1" style="width:200px">
	<tr>
		<th>id</th>
		<th>Name</th>
		<th>Location</th>
		<th>Country</th>
		<th>Type</th>
	</tr>
	<%
		for(User1 user:user1DaoImpl.getResult()){
			out.println("<tr>");
			out.println("<td>"+user.getId()+"</td>");
			out.println("<td>"+user.getName()+"</td>");
			out.println("<td>"+user.getLocation()+"</td>");
			out.println("<td>"+user.getCountry()+"</td>");
			out.println("<td>"+user.getType()+"</td>");
			out.println("</tr>");
		}
	%>
</table>
<a href="<c:url value='/j_spring_security_logout'/>">Logout</a>
</body>
</html>