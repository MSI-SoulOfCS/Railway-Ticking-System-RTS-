
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Dashboard Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->    
	 <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">   
    <!-- Custom styles for this template -->
    <link href="<c:url value="/css/dashboard.css" />" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/css/jquery-ui.css"/>">    
    
    <script src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
   	<script src="<c:url value="/js/jquery-ui.js"/>"></script>
  	<script src="<c:url value="/js/msi-jquery.js"/>"></script>
	
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script> 
		$(document).ready(function() {
			/* need to pass username from welcome */
/*			var ID={"username":"qiushuidamowang"};
 			$.ajax({
				url: "/Demand1/restful/Tickets.html",
				type: "get",
				data: ID,
				dataType: "json",
				success:showData
			});*/
			
			$("#navigation-menu li a").on("click", function(event){
				removeActiveClass();
				$(event.target).parent().addClass("active");
				var viewTag = event.target.id + "View";
				$("#"+viewTag).show();
			});

			
			//This block use to admin page
			function addZeros(n) {
				  return (n < 10)? '0' + n : '' + n;
			}		
			
			var select = '';
			for (i=0;i<=23;i++){
			    select += '<option val=' + addZeros(i) + '>' + addZeros(i) + '</option>';
			}
			$('#AT_Hour').html(select);
			
			var select = '';
			for (i=0;i<=59;i++){
			    select += '<option val=' + addZeros(i) + '>' + addZeros(i) + '</option>';
			}
			$('#AT_Min').html(select);
			
			//This block use to load ticket list in admin page
			loadAllTicket();
		});
		
/*		function showData(data) {
			var rows = "";
			$("#tickets").empty();
			$(data).each(function(i,item) {
				var d = new Date(item.date);
				var ticket_from = item.from_loc;
				var ticket_to=item.to_loc;
				var ticket_price=item.price;
				rows = "<tr><td>" + ticket_from.station + "</td><td>"+ticket_to.station+"</td><td>"+
				(d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear()
				+"	"+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"</td><td>"+ticket_price+"</td></tr>";
				$(rows).appendTo("#tickets");
			});
		}*/
		
		function removeActiveClass() {
			$("#MyTrip").parent().removeClass("active");
			$("#History").parent().removeClass("active");
			$("#Profile").parent().removeClass("active");
			
			$("#MyTripView").hide();
			$("#HistoryView").hide();
			$("#ProfileView").hide();
			
			$("#ManageTicket").parent().removeClass("active");
			$("#ManageTicketView").hide();
		}
	</script>
	<script src="<c:url value="/js/msi-jquery.js" />"></script>
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
  </head>

  <body>
	<sec:authentication var="user" property="principal" />  
  
  
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
          	<li><a>${user.username}'s Account</a></li>
            <li><a href="/Demand1/">Home</a></li>
            <li><a href="<c:url value='/j_spring_security_logout'/>">Logout</a></li>
          </ul>
        </div>
      </div>
    </nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul id="navigation-menu" class="nav nav-sidebar">
					<sec:authorize access="hasRole('ROLE_USER')">
            			<li class="active"><a id="MyTrip" href="#MyTrip">My Trip</a></li>
            			<li>			   <a id="History" href="#History">History</a></li>
            			<li>			   <a id="Profile" href="#Profile">Profile</a>
          			</sec:authorize>
          			<sec:authorize access="hasRole('ROLE_ADMIN')">
            			<li class="active"><a id="ManageTicket" href="#ManageTicket">Manage Ticket</a></li>
          			</sec:authorize>	
          		</ul>
        	</div>
        	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        		<!-- User -->
          		<sec:authorize access="hasRole('ROLE_USER')">
          			<div id="MyTripView">
	        	 		<h2 class="sub-header">Section title</h2>
	          			<div class="table-responsive">
	            			<table class="table table-striped">
	              				<thead>
	                				<tr>
	                  					<th>#</th>
	                  					<th>Header</th>
	                  					<th>Header</th>
	                  					<th>Header</th>
	                  					<th>Header</th>
	               					</tr>
	              				</thead>
	            			</table>
	          			</div>
					</div>
					
					<div id="HistoryView" style="display:none">
						<h2 class="sub-header">Update Profile</h2>
	          			<div>
	            			<table class="table table-striped">
	                			<tr>
	                  				<th>From</th>
	                  				<th>To</th>
	                  				<th>Date</th>
	                  				<th>Price</th>
	               				</tr>
	               				<tbody id="tickets">
				  				</tbody>
	            			</table>
	          			</div>
					</div>
					
					<div id="ProfileView" style="display:none">
						<h2 class="sub-header">Update Profile</h2>
	          			<div>
							<div class="alert" style="display:none;" id="v_email">
								<p>Email is invalid</p>
							</div>
							<div class="alert" style="display:none;" id="v_password">
								<p>Password is length must be between 8 to 16 charactors</p>
							</div>
	            			<table class="table table-striped">
	                			<tr>
	                  				<th>FirstName</th>
	                  				<th>LastName</th>
	                  				<th>Email</th>
	                  				<th>Password</th>
	               				</tr>
	               				<tr>
	               					<td><input id="firstName" type="text" maxlength="20"/></td>
	               					<td><input id="lastName" type="text"  maxlength="20"/></td>
	               					<td><input id="email" type="text"  maxlength="50"/></td>
	               					<td><input id="password" type="password"  maxlength="16"/></td>
	               				</tr>
	            			</table>
	            			<button onclick="updateUser()" class="button orange">Submit</button>
	          			</div>
					</div>
				<!-- ADMIN -->	
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div id="ManageTicketView">
						<h2 class="sub-header">Ticket Management</h2>
	          			<div class="table-responsive">
					          <div align="left">
									<table>
										<tr>
											<td align="left">From:</td>
											<td align="left">To:</td>
											<td align="left">Leave:</td>
											<td align="left">Time:</td>
											<td align="left">Amount:</td>
											<td align="left">Price:</td>
											<td align="left">SeatType:</td>
										</tr>	
										
										<tr>
											<td><input type="text" style="height:24px" id="AT_From" placeholder="from"></td>
											<td><input type="text" style="height:24px" id="AT_To" placeholder="to"></td>	
											<td><input type="date" style="height:24px" id="AT_Date"></td>
											<td>
												<select id="AT_Hour" style="height:24px">
												</select> : 
												<select id="AT_Min" style="height:24px">
												</select>
											</td>
											<td>
												<input type="text" style="height:24px;width:70px;" id="AT_Amount" value="0" placeholder="amount">
											</td>
											<td><input type="text" style="height:24px;width:70px;" value="0" id="AT_Price" placeholder="Price"></td>
											<td><input type="text" style="height:24px;width:70px;" id="AT_Seat" placeholder="seat type"></td>
										</tr>
									</table>
									<br />
					          </div>
							<button onclick="addTicket()" class="button orange">Add Ticket</button> 
							<br />
							<br />
	          			</div>
	          			<div class="table-responsive">
					          <div align="left">
									<table class="table table-striped">
										<tr>
											<th align="left" >From:</th>
											<th align="left" >To:</th>
											<th align="left" >Leave:</th>
											<th align="left" >Amount:</th>
											<th align="left" >Price:</th>
										</tr>	
										
	               						<tbody id="ManageTicketTable">
				  						</tbody>
									</table>
									<br />
					          </div>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
    </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
<%--     <script src="<c:url value="/css/bootstrap.min.css" />"></script>
 --%>  </body>
</html>
