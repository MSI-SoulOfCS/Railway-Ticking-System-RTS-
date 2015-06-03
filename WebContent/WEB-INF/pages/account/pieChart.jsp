<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script>
	
		google.load("visualization", "1", {packages:["corechart"]});
		google.setOnLoadCallback(drawChart);
		//alert(item.amount);
		function drawChart() {
		      var data1 = google.visualization.arrayToDataTable([
		        ['Sales', 'Amount'],
		        ['Saled', $(resultTicektInfo)],
		        ['Not saled', 20]
		      ]);
		          
		      var options = {
		        title: 'Ticket sale information',
		        is3D: true,
		      };
		          
		      var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
		      chart.draw(data1, options);
		}
	

</script>
</head>
<body>
	<div id="piechart_3d" style="width: 900px; height: 500px;"></div>
</body>
</html>