<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome</title>
    <link href="<c:url value="/css/cover.css" />" rel="stylesheet">
    <!-- Added by Ning ***********start********** -->
    <link href="<c:url value="/css/modal.css" />" rel="stylesheet">
    <!-- Added by Ning ***********end************ -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="<c:url value="css/jquery-ui.css"/>">
  	<script src="<c:url value="js/jquery-ui.js"/>"></script>
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
	function ticket(){
		var formData = {From : $("#From").val(),To:$("#To").val(),Time:$("#Leave").val()+"/"+$("#At").val()};
		$.ajax({
			url: "/Demand1/restful/PeroidTickets.html",
			type: "post",
			data: formData,
			dataType: "json",
			success:showTicket
		});
	}
	function showTicket(data){
		var rows = "";
		$("#tickets").empty();
		$(data).each(function(i, item) {
			var d = new Date(item.date);
			rows = "<tr><td>" + item.id + "</td><td>" + item.price + "</td><td>" + 
								d.getHours() + ":" + d.getMinutes() +"  "+
								(d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear()
								+ "</td><td>" + item.amount + "</td><td>" +
								item.from_loc.station + "</td><td>" + item.to_loc.station + "</td></tr>";
			$(rows).appendTo("#tickets");
		});
	}
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
	function signUpValidation() {
  		$("#r_usernameReq").hide();
  		$("#r_passwordReq").hide();
  		$('#r_retypepasswordReq').hide();
  		$("#r_emailReq").hide();
  		$("#r_lastnameReq").hide();
  		$("#r_firstnameReq").hide();
		
	  	if($("#r_username").val().length < 8 ) {
	  		$("#r_usernameReq").show();
	  	}
	  	if ($("#r_password").val().length < 8 ) {
	  		$("#r_passwordReq").show();
	  	}
	  	if ($("#r_retypepassword").val() != $("#r_password").val()) {
	  		$('#r_retypepasswordReq').show();
	  	}
	  	if ($("#r_email").val().length == 0) {
	  		$("#r_emailReq").show();
	  	}
	  	if ($("#r_lastname").val().length == 0) {
	  		$("#r_lastnameReq").show();
	  	}
	  	if ($("#r_firstname").val().length == 0) {
	  		$("#r_firstnameReq").show();
	  	} else {
	  		return true;
	  	}

	}
	$(function() {
		    var availableTags = [
				"NY,NewYork,Penn Station",
				"NY,NewYork,Jamaica",
				"NY, NewYork,Yonkers Amtrak",
				"NJ,Princeton,West Trenton",
				"NJ,Princeton,Joe's Train Station",
				"NJ,Princeton,Princeton Junction",
				"NJ,Hoboken,Hoboken terminal",
				"MA,Boston,North Station",
				"MA,Boston,Haymarket Station",
				"MA,Boston,Back Bay Station",
				"CA,SanFrancisco,San Jose Train Station",
				"CA,SanFrancisco,San Francisco Caltrain Station",
				"CA,Los Angeles,Union Station",
				"CA,Los Angeles,Pomona-North Metrolink Station",
				"CA,Los Angeles,Santa Train Station",
				"TX,Abilene,Abilene Regional station",
				"TX,Amarillo,Rick Husband Amarillo station",
				"TX,Austin,Austin-Bergstrom station",
				"FL,Orlando,Orlando Amtrak Train Station",
				"FL,Orlando,SunRail Station",
				"FL,Orlando,Church Street Station",
				"FL,Miami,Miami Amtrak Train Station",
				"NC,Winston-Salem,Winston-Salem Amtrak Train Station",
				"NC,Winston-Salem,Willow Street Train Station",
				"NC,Chapel Hill,Chapel Hill Metro Station",
				"NC,Chapel Hill,Southern Rail",
				"NC,Chapel Hill,Amtrak Station",
				"GA,Appling County,Apple Station",
				"GA,Appling County,Sant Train Station",
				"GA,Appling County,Amtrak Station"
		    ];
		    $( "#To" ).autocomplete({
		      source: availableTags
		    });
		    $( "#From" ).autocomplete({
			      source: availableTags
			});
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
                  
                  <!-- Modify by Ning *******start********** -->
                  <li><a href="#join_form">Join Now</a></li>
                  <li><a href="#login_form" id="login_pop">Sign In</a></li>
                  <!-- Modify by Ning *******end********** -->
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
					<p>User name length must be between 8 to 20 charactors</p>
				</div>
				<div class="alert" style="display:none;" id="r_passwordReq">
					<p>Password is length must be between 8 to 20 charactors</p>
				</div>
				<div class="alert" style="display:none;" id="r_retypepasswordReq">
					<p>RetypePassword and Password must be same</p>
				</div>
				<div class="alert" style="display:none;" id="r_emailReq">
					<p>Email is required</p>
				</div>
				<div class="alert" style="display:none;" id="r_lastnameReq">
					<p>Last Name is required</p>
				</div>
				<div class="alert" style="display:none;" id="r_firstnameReq">
					<p>First Name is required</p>
				</div>
				<div class="alert" id="test123123" style="display:none;">
					<p>The user name or password supplied is incorrect</p>
				</div>	
				<!-- Alerts for missing form end -->
				<h2>Please register!</h2>
	            <p>Please enter your user name and password here</p>
				<table>
					<tr>
		            	<td>User name: </td>
						<td><input type="text" name="r_username" id="r_username"/></td>
		            </tr>
		           	<tr>
						<td>Password: </td>
						<td><input type="password" name="r_password" id="r_password"/></td>
					</tr>
					<tr>
						<td>RetypePassword: </td>
						<td><input type="password" name="r_retypepassword" id="r_retypepassword"/></td>
					</tr>
					<tr>
		            	<td>Email address: </td>
						<td><input type="text" name="r_email" id="r_email"/></td>
		            </tr>
					<tr>
		            	<td>Last Name: </td>
						<td><input type="text" name="r_lastname" id="r_lastname"/></td>
		            </tr>
					<tr>
		            	<td>First Name: </td>
						<td><input type="text" name="r_firstname" id="r_firstname"/></td>
		            </tr>
					<tr>
						<td>
						</td>
						<td>
							<button id="signup" class="button orange">Join Now</button>
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
					<p>User name and password are required</p>
				</div>
				
				<div class="alert" style="display:none;" id="usernameReq">
					<p>User name is required</p>
				</div>
				<div class="alert" style="display:none;" id="passwordReq">
					<p>Password is required</p>
				</div>
				<div class="alert" id="wrongCredentials" style="display:none;">
					<p>The user name or password supplied is incorrect</p>
				</div>	
				<!-- Alerts for missing form end -->
	            <h2>Please sign in!</h2>
	            <p>Please enter your user name and password here</p>
	            <form name="f" action="<c:url value='/j_spring_security_check'/>" method="POST" id="login-form">
		            <table>
		            	<tr>
		            		<td>User name: </td>
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
	       
			<!-- Modify by Ning ************end********** -->
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