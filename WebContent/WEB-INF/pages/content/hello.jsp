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
			url: "http://localhost:8080/Demand1/rest/Stations",
			type: "get",
			dataType: "json",
			success:showStation
		});
		$.ajax({
			url: "http://localhost:8080/Demand1/rest/Tickets",
			type: "get",
			dataType: "json",
			success:showTicket
		});
		$.ajax({
			url: "http://localhost:8080/Demand1/rest/History",
			type: "get",
			dataType: "json",
			success:showHistory
		});

	});
	function showStation(data) {
		var rows = "";
		$("#stations").empty();
		$(data.stations).each(function(i, item) {
			rows = "<tr><td>" + item.id + "</td><td>" + item.station + "</td><td>" +
								item.state + "</td><td>" + item.city + "</td></tr>";
			$(rows).appendTo("#stations");
		});
	}
	function showTicket(data) {
		var rows = "";
		$("#tickets").empty();
		$(data.tickets).each(function(i, item) {
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
		$(data.history).each(function(i, item) {
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