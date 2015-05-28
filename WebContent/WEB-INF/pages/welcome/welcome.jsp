<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cover Template for Bootstrap</title>
  <!--  <link href=".\css\bootstrap.min.css" rel="stylesheet"> -->
    <link href="<c:url value="/css/cover.css" />" rel="stylesheet">
<style>
	.alert {
		color: red;
		background: #fdf1e5;
		font-size: 10px;
		line-height: 16px;
		width: 200px;
		margin: 10;
		position: relative;
	}
	.button {
	display: inline-block;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	font: 16px/100% 'Microsoft yahei',Arial, Helvetica, sans-serif;
	padding: .5em 2em .55em;
	text-shadow: 0 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
	border-radius: .5em;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	box-shadow: 0 1px 2px rgba(0,0,0,.2);
	}
	.orange {
	color: #fef4e9;
	border: solid 1px #da7c0c;
	background: #f78d1d;
	background: -webkit-gradient(linear, left top, left bottom, from(#faa51a), to(#f47a20));
	background: -moz-linear-gradient(top,  #faa51a,  #f47a20);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#faa51a', endColorstr='#f47a20');
	}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		if ("<c:out value='${param.login_error}'/>" != "") {
		  	$('#wrongCredentials').show();
		}
		$("#signin").on("click", loginValidation);	
	});

	function loginValidation() {
		$("#usernameAndPasswordReq").hide();
		$("#usernameReq").hide();
		$("#passwordReq").hide();   
		$("#wrongCredentials").hide();	
	  	if($("#j_username").val().length == 0 && $("#j_password").val().length == 0) {
	  		$("#usernameAndPasswordReq").show();
	  		return false;
	  	} else if ($("#j_username").val().length == 0) {
	  		$('#usernameReq').show();
	  		return false;
	  	} else if ($("#j_password").val().length == 0) {
	  		$("#passwordReq").show();
	  		return false;
	  	} else {
	  		return true;
	  	}
	}
</script>
</head>
<body background="<c:url value="/img/15.jpg" />">
<!-- <h1><font color="blue">Login with Username and Password</font></h1> -->
<!-- Alerts for missing form info  --> 
<div class="alert" style="display:none;" id="usernameAndPasswordReq">
	<p>Username and password are required</p>
</div>

<div class="alert" style="display:none;" id="usernameReq">
	<p>Username is required</p>
</div>
<div class="alert" style="display:none;" id="passwordReq">
	<p>Password is required</p>
</div>
<div class="alert" id="wrongCredentials" style="display:none;">
	<p>The username or password supplied is incorrect</p>
</div>	
<!-- Login Form -->	
<div class="site-wrapper">

      <div class="site-wrapper-inner">

        <div class="cover-container">

          <div class="masthead clearfix">
            <div class="inner">
              
              <nav>
                <ul class="nav masthead-nav">
                  
                  <li><a href="#">Sign On</a></li>
                  <li><a href="./content/main.html">Sign In</a></li>
                </ul>
              </nav>
            </div>
          </div>
			
          <div align="center">
			<div>
				<h1>Search your tickets!</h1>
				<table>
					<tr>
						<td align="left">From:</td>
						<td align="left">To:</td>
						<td align="left">Leave:</td>
						<td align="left">At:</td>
					</tr>	
					
					<tr>
						<td><input type="text" style="height:18px" id="From" placeholder="from"></td>
						<td><input type="text" style="height:18px" id="To" placeholder="to"></td>	
						<td><input type="date" style="height:20px" id="Leave"></td>
						<td>
							<select id="At" style="height:24px">
								<option value="Anytime">Anytime</option>
								<option value="12am-9am">12am-9am</option>
								<option value="6am-noon">6am-noon</option>
								<option value="10am-2pm">10am-2pm</option>
								<option value="noon-5pm">noon-5pm</option>
								<option value="4pm-8pm">4pm-8pm</option>
								<option value="6pm-12am">6pm-12am</option>
								<option value="1am">1am</option>
								<option value="2am">2am</option>
								<option value="3am">3am</option>
								<option value="4am">4am</option>
								<option value="5am">5am</option>
								<option value="6am">6am</option>
								<option value="7am">7am</option>
								<option value="8am">8am</option>
								<option value="9am">9am</option>
								<option value="10am">10am</option>
								<option value="Noon">Noon</option>
								<option value="1pm">1pm</option>
								<option value="2pm">2pm</option>
								<option value="3pm">3pm</option>
								<option value="4pm">4pm</option>
								<option value="5pm">5pm</option>
								<option value="6pm">6pm</option>
								<option value="7pm">7pm</option>
								<option value="8pm">8pm</option>
								<option value="9pm">9pm</option>
								<option value="10pm">10pm</option>
								<option value="11pm">11pm</option>
								<option value="Midnight">Midnight</option>
							</select>
						</td>
					</tr>
				</table>
				<br>
				<a href="./content/main.html" class="button orange">Search Ticket</a> 
			</div>
          </div>

          <div class="mastfoot">
            <div class="inner">
              <p>Copyright © 2015 Nina, Vincent, Takuro, God Song All rights reserved.</p>
            </div>
          </div>

        </div>

      </div>

    </div>

</body>
</html>