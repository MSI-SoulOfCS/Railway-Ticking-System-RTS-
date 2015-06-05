<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activate</title>
<script>     
function countDown(secs,surl){//alert(surl);     
	var jumpTo = document.getElementById('jumpTo');
	jumpTo.innerHTML=secs;  
	if(--secs>0){     
	     setTimeout("countDown("+secs+",'"+surl+"')",1000);     
	}     
	 else{       
	     location.href=surl;     
	}     
}     
</script>
<style>
	.style{
		width: 400px;
		height: 200px;
		margin: 200px auto;
		border: 0px rgb(89,89,89) solid;
		text-align:center;
		line-height: 55px;
		
		-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=70)";
		filter: alpha(opacity=70);
		-moz-opacity: 0.7;
		-khtml-opacity: 0.7;
		opacity: 0.7;
		
		background: rgb(136, 133, 226);
		
		color: rgb(250, 245, 245);
		font-size: inherit;
		font-weight: inherit;
		font-family: inherit;
		font-style: inherit;
		text-decoration: inherit;
		text-align: center;
		
		-webkit-border-radius: 50px;
		-moz-border-radius: 50px;
		border-radius: 50px;
		
		-moz-box-shadow:  0px 0px 47px 17px rgb(230, 173, 91);
		-webkit-box-shadow:  0px 0px 47px 17px rgb(230, 173, 91);
		box-shadow:  0px 0px 47px 17px rgb(230, 173, 91);
	}
</style>
</head>
<body background="<c:url value="/img/bg.jpg" />">
<div class="style">
	
	<br/>
	${Result}! 
	<br /><span id="jumpTo" style="font-size:20pt">5</span> seconds to MSI Ticket System!
	<script>countDown(5,'http://localhost:8080/Demand1/');</script> 
</div>
</body>
</html>