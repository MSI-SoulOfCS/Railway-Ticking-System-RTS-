<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello Page</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		$.ajax({
			url: "http://localhost:8080/Demand1/rest/hello",
			type: "get",
			dataType: "json",
			success:showData
		});
	});
	function showData(data) {
		var rows = "";
		$("#users").empty();
		$(data.user).each(function(i, item) {
			var user_id = item.id;
			var user_name = item.name;
			rows = "<tr><td>" + user_id + "</td><td>" + user_name + "</td></tr>";
			$(rows).appendTo("#users");
		});
	}
</script>
</head>
<body>
<h1><font color="blue">${title}</font></h1>
<table border="1" style="width:200px">
	<tr>
		<th>id</th>
		<th>Name</th>
	</tr>
	<tbody id="users">
	</tbody>
</table>
<a href="<c:url value='/j_spring_security_logout'/>">Logout</a>
</body>
</html>