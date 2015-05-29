<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello Page</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		var formData = {username : "takuro1026"};
		$.ajax({
			url: "/Demand1/auth/GetUser.html",
			type: "post",
			data: formData,
			dataType: "json",
			success:showUser
		})
		.fail(function(){ alert("please login!");
		});
		$.ajax({
			url: "/Demand1/restful/Stations.html",
			type: "get",
			dataType: "json",
			success:showStation
		})
		.fail(function(){ alert("please login!");
		});
		$.ajax({
			url: "/Demand1/restful/Tickets.html",
			type: "get",
			dataType: "json",
			success:showTicket
		})
		.fail(function(){ alert("please login!")
		});
		$.ajax({
			url: "/Demand1/auth/History.html",
			type: "get",
			dataType: "json",
			success:showHistory
		})
		.fail(function(){ alert("Something error");
		});

	});
	function showUser(data) {
		alert("username:"+data.username+" password:"+data.password);
	}
	function showStation(data) {
		var rows = "";
		$("#stations").empty();
		$(data).each(function(i, item) {
			rows = "<tr><td>" + item.id + "</td><td>" + item.station + "</td><td>" +
								item.state + "</td><td>" + item.city + "</td></tr>";
			$(rows).appendTo("#stations");
		});
	}
	function showTicket(data) {
		var rows = "";
		$("#tickets").empty();
		$(data).each(function(i, item) {
			var d = new Date(item.date);
			rows = "<tr><td>" + item.id + "</td><td>" + item.price + "</td><td>" +
								(d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear()
								+ "</td><td>" + item.amount + "</td><td>" +
								item.from_loc.station + "</td><td>" + item.to_loc.station + "</td></tr>";
			$(rows).appendTo("#tickets");
		});
	}
	function showHistory(data) {
		var rows = "";
		$("#history").empty();
		$(data).each(function(i, item) {
			rows = "<tr><td>" + item.person.username + "</td><td>" + item.ticket.id + "</td><td>" +
								item.amount + "</td></tr>";
			$(rows).appendTo("#history");
		});
	}

</script>
</head>
<body>
<h1><font color="blue">${title}</font></h1>
<table border="1" style="width:600px">
	<tr>
		<th>id</th>
		<th>Station</th>
		<th>State</th>
		<th>City</th>
	</tr>
	<tbody id="stations">
	</tbody>
</table>

<table border="1" style="width:600px">
	<tr>
		<th>id</th>
		<th>price</th>
		<th>date</th>
		<th>amount</th>
		<th>From</th>
		<th>To</th>
	</tr>
	<tbody id="tickets">
	</tbody>
</table>
<table border="1" style="width:600px">
	<tr>
		<th>username</th>
		<th>ticketid</th>
		<th>amount</th>
	</tr>
	<tbody id="history">
	</tbody>
</table>
<a href="<c:url value='/j_spring_security_logout'/>">Logout</a>
</body>
</html>