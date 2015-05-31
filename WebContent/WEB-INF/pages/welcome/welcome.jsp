<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome</title>
    <link href="<c:url value="/css/cover.css" />" rel="stylesheet">
    <link href="<c:url value="/css/modal.css" />" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="css/jquery-ui.css"/>">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
  	<script src="<c:url value="js/jquery-ui.js"/>"></script>
  	<script src="<c:url value="js/msi-jquery.js"/>"></script>
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

<script>
	$(document).ready(function() {
		if ("<c:out value='${param.login_error}'/>" != "") {
		  	$('#wrongCredentials').show();
		}
		$("#signin").on("click", loginValidation);
		$("#signup").on("click", signUpValidation);
	});

</script>
</head>
<body background="<c:url value="/img/15.jpg" />">
<!-- Login Form -->	
<div class="site-wrapper">

      <div class="site-wrapper-inner">

			<div class="cover-container">

          		<div class="masthead clearfix">
            		<div class="inner">
              
              		<nav>
                		<ul class="nav masthead-nav">            
	         		  		<sec:authorize access="isAnonymous()">
    	              			<li><a href="#join_form">Sign up</a></li>
        	          			<li><a href="#login_form" id="login_pop">Sign In</a></li>
					  		</sec:authorize>
							<li><sec:authentication var="user" property="principal" /></li>
				  			<sec:authorize access="hasRole('ROLE_USER')">
				  				<b>Hi, ${user.username}</b><br />
					 			<li><a href="http://localhost:8080/Demand1/content/main.html">My Account</a></li>
					 			<li><a href="<c:url value='/j_spring_security_logout'/>">Logout</a></li>
				  			</sec:authorize>
                		</ul>
              		</nav>
            	</div>
          </div>
          <!-- Modify by Ning ************start********** -->
          <!-- popup form #1 -->
	       <a href="/Demand1/" class="overlay" id="join_form"></a>
			<div class="popup">
		       <!-- Alerts for missing form info  --> 
				<div class="alert" style="display:none;" id="r_usernameReq">
					<p>Username length must be between 8 to 16 charactors</p>
				</div>
				<div class="alert" style="display:none;" id="r_passwordReq">
					<p>Password is length must be between 8 to 16 charactors</p>
				</div>
				<div class="alert" style="display:none;" id="r_retypepasswordReq">
					<p>RetypePassword and Password must be same</p>
				</div>
				<div class="alert" style="display:none;" id="r_emailReq">
					<p>Email is invalid</p>
				</div>
				<div class="alert" style="display:none;" id="r_lastnameReq">
					<p>Last Name is required</p>
				</div>
				<div class="alert" style="display:none;" id="r_firstnameReq">
					<p>First Name is required</p>
				</div>
				<div class="alert" id="r_alreadyexisted" style="display:none;">
					<p>Username already existed, please choose other username</p>
				</div>	
				<!-- Alerts for missing form end -->
				<h2>Please register!</h2>
	            <p>Please enter your user name and password here</p>
				<table>
					<tr>
		            	<td>Username: </td>
						<td><input type="text" maxlength="16" name="r_username" id="r_username"/></td>
		            </tr>
		           	<tr>
						<td>Password: </td>
						<td><input type="password" maxlength="16" name="r_password" id="r_password"/></td>
					</tr>
					<tr>
						<td>Comfirm Password: </td>
						<td><input type="password" maxlength="16" name="r_retypepassword" id="r_retypepassword"/></td>
					</tr>
					<tr>
		            	<td>Email address: </td>
						<td><input type="email" maxlength="50" name="r_email" id="r_email"/></td>
		            </tr>
					<tr>
		            	<td>Last Name: </td>
						<td><input type="text" maxlength="20" name="r_lastname" id="r_lastname"/></td>
		            </tr>
					<tr>
		            	<td>First Name: </td>
						<td><input type="text" maxlength="20" name="r_firstname" id="r_firstname"/></td>
		            </tr>
					<tr>
						<td>
						</td>
						<td>
							<button id="signup" class="button orange">Submit</button>
						</td>
					</tr>
		            </table>
	            <a class="close" href="/Demand1/"></a>
	           </div>
          <!-- popup form #1 -->
	       <a href="/Demand1/" class="overlay" id="login_form"></a>
	       
	       <div class="popup">
		       <!-- Alerts for missing form info  --> 
				<div class="alert" style="display:none;" id="usernameAndPasswordReq">
					<p>Username and Password are required</p>
				</div>
				
				<div class="alert" style="display:none;" id="usernameReq">
					<p>Username is required</p>
				</div>
				<div class="alert" style="display:none;" id="passwordReq">
					<p>Password is required</p>
				</div>
				<div class="alert" id="wrongCredentials" style="display:none;">
					<p>The Username or Password supplied is incorrect</p>
				</div>	
				<!-- Alerts for missing form end -->
	            <h2>Please sign in!</h2>
	            <p>Please enter your Username and Password here</p>
	            <form name="f" action="<c:url value='/j_spring_security_check'/>" method="POST" id="login-form">
		            <table>
		            	<tr>
		            		<td>Username: </td>
							<td><input type="text" name="j_username" id="j_username"/></td>
		            	</tr>
		            	<tr>
							<td>Password: </td>
							<td><input type="password" name="j_password" id="j_password"/></td>
						</tr>
						<tr>
							<td></td>
							<td>
								<button class="button orange" id="signin" type="submit">Login</button>
							</td>
						</tr>
		            </table>
		            	            
	 			</form>
	            <a class="close" href="/Demand1/"></a>
	       </div>

          <!-- popup form #1 -->
	       <a href="/Demand1/" class="overlay" id="Success_form"></a>
	       
	       <div class="popup">
	            <h2>Register success!</h2>
	            <p>Please check your e-mail to activate your account!</p>
	            <a class="close" href="/Demand1/"></a>
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
								<option value="00-09">0am-9am</option>
								<option value="06-12">6am-noon</option>
								<option value="10-14">10am-2pm</option>
								<option value="12-17">noon-5pm</option>
								<option value="16-20">4pm-8pm</option>
								<option value="18-24">6pm-0am</option>
								<option value="01">1am</option>
								<option value="02">2am</option>
								<option value="03">3am</option>
								<option value="04">4am</option>
								<option value="05">5am</option>
								<option value="06">6am</option>
								<option value="07">7am</option>
								<option value="08">8am</option>
								<option value="09">9am</option>
								<option value="10">10am</option>
								<option value="11">11am</option>
								<option value="12">Noon</option>
								<option value="13">1pm</option>
								<option value="14">2pm</option>
								<option value="15">3pm</option>
								<option value="16">4pm</option>
								<option value="17">5pm</option>
								<option value="18">6pm</option>
								<option value="19">7pm</option>
								<option value="20">8pm</option>
								<option value="21">9pm</option>
								<option value="22">10pm</option>
								<option value="23">11pm</option>
								<option value="00">Midnight</option>
							</select>
						</td>
					</tr>
				</table>
				<br>
				<button onclick="ticket()" class="button orange">Search Ticket</button> 
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