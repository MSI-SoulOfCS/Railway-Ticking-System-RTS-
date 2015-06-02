<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
	<script>
	$(document).ready(function(){
		var add=0;
		$('#grid input[type=checkbox]:checked').each(function(){ 
				
				var row = $(this).parent().parent();
				var rowcells = row.find('td');
				add+=parseInt($(rowcells[0]).html());
		});
		sum.innerHTML=add.toFixed(2);
	});
	function calculate(){
		var add=0;
		$('#grid input[type=checkbox]:checked').each(function(){ 
			
			var row = $(this).parent().parent();
			var rowcells = row.find('td');
			add+=parseInt($(rowcells[0]).html());
		});
			sum.innerHTML=add.toFixed(2);
	}
	</script>

</head>

<body>
	<table id="grid">
	<tr>
		<td>200</td>
		<td><input id="check" type="checkbox" checked="checked" onclick="calculate()"/></td>
	</tr>
	<tr>
		<td>300</td>
		<td><input id="check" type="checkbox" checked="checked" onclick="calculate()"/></td>
	</tr>
	<tr>
	<td>total: <span id="sum">0.00</span></td>
	</tr>
	
		
	</table>
</body>
</html>
