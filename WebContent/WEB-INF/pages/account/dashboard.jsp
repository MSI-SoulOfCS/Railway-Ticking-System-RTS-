
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

    <title>User Account Info</title>

    <!-- Bootstrap core CSS -->    
	<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">   
    <!-- Custom styles for this template -->
    <link href="<c:url value="/css/dashboard.css" />" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/css/jquery-ui.css"/>">    
    
    <script src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
   	<script src="<c:url value="/js/jquery-ui.js"/>"></script>
  	<script src="<c:url value="/js/msi-jquery.js"/>"></script>
  	<script src="<c:url value="/js/bootstrap.js"/>"></script>

	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap-theme.min.css">

  	<script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['corechart']}]}"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script>
		$(document).ready(function(){
			loadAllUserCart();
			loadAllTicket();
			loadUserInfo();
			
			$("#navigation-menu li a").on("click", function(event){
				removeActiveClass();
				$(event.target).parent().addClass("active");
				var viewTag = event.target.id + "View";
				$("#"+viewTag).show();
				
				if(viewTag=="AnalysisView"){
					loadAllTicketForAnalysis();
				} else if(viewTag=="HistoryView") {
					loadUserHistory();					
				} else if(viewTag=="ShoppingCartView") {
					loadAllUserCart();					
				}
			});	
			
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
						
			$('#checkoutBtn').click(function(){
				window.location.href='http://localhost:8080/Demand1/auth/checkOut.html';
			});
			
			
		
		});
		
		function loadAllTicketForAnalysis() {
	    	$.ajax({
				  url:"/Demand1/restful/Tickets.html",
				  type:"GET",
				  dataType:"json",
				  success:reloadAllTicketAfterNewTicketForAnalysis
			});
	    }
		
		function reloadAllTicketAfterNewTicketForAnalysis(data) {
			var rows = "";
			$("#analysisTicket").empty();
			$(data).each(function(i,item) {
				var d = new Date(item.date);
				var str=item.start +"+"+item.destination+"+" + d.getFullYear()+"-"+addZeros((d.getMonth()+1))+ "-"+ addZeros(d.getDate())
				+" "+addZeros(d.getHours())+":"+addZeros(d.getMinutes())+":00.0";
				str=str.replace(/ /g, "_");
				rows = "<tr><td>" + item.start + "</td><td>"+item.destination+"</td><td>"+
				d.getFullYear() + "-" + addZeros((d.getMonth()+1)) + "-" + addZeros(d.getDate()) + 
				" "+ addZeros(d.getHours()) + ":" + addZeros(d.getMinutes())+ "</td><td>"+item.avaiNumber+"/"+item.amount +"</td><td>"+item.price + 
				"</td><td><button id = " + str + " class=\"btn btn-default\" onclick=\"sendIDforPieChart(id)\" data-toggle=\"modal\" data-target=\"#myModal\">Pie Chart</button>";
/* <input id=" +str+ " type=\"button\" value=\"show pie chart\" onclick=\"sendIDforPieChart(id)\"/>"+"</td></tr>";
 */				$(rows).appendTo("#analysisTicket");
				
			});
	    }
		 function sendIDforPieChart(data){
		    	var id={ticketItem:data};
		    	 
		    	$.ajax({
		    		url: "/Demand1/restful/PeroidTickets.html",
		  			type: "post",
		  			data: id,
		  			dataType: "json",
		  			success: drawChart
		  			
		    	});
		    	
		}
		 
		 function drawChart(data) {
			 var saled=0;
			 var available=0;
			 $(data).each(function(i,item){
				 saled=item.amount-item.avaiNumber;
				 available=item.avaiNumber;
				 $("#myModalLabel").text(item.start+" To "+item.destination);
			 });
		      var data1 = google.visualization.arrayToDataTable([
		        ['Sales', 'Amount'],
		        ['Saled', saled],
		        ['Not saled', available]
		      ]);
		          
		      var options = {
		        title: 'Ticket sale information',
		        is3D: true,
		      };
		          
		      var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
		      chart.draw(data1, options);
		      $("#piechart_3d").show();
		}
		
		function removeActiveClass() {
			$("#History").parent().removeClass("active");
			$("#Profile").parent().removeClass("active");
			$("#ShoppingCart").parent().removeClass("active");

			
			$("#HistoryView").hide();
			$("#ProfileView").hide();
			$("#ShoppingCartView").hide();
			
			$("#ManageTicket").parent().removeClass("active");
			$("#ManageTicketView").hide();
			$("#Analysis").parent().removeClass("active");
			$("#AnalysisView").hide();
			
			$("#piechart_3d").hide();
		}
		
		
	</script>
	
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
          				<li class="active"><a id="ShoppingCart" href="#ShoppingCart">Shopping Cart</a>
            			<li>			   <a id="History" href="#History">History</a></li>
          			</sec:authorize>
          			<sec:authorize access="hasRole('ROLE_ADMIN')">
            			<li class="active"><a id="ManageTicket" href="#ManageTicket">Manage Ticket</a></li>
            			<li>			   <a id="Analysis" href="#Analysis">Analysis</a></li>
          			</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
            			<li>			   <a id="Profile" href="#Profile">Profile</a>
					</sec:authorize>
          		</ul>
        	</div>
        	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        		<!-- User -->
          		<sec:authorize access="hasRole('ROLE_USER')">
					
					<div id="HistoryView" style="display:none">
						<h2 class="sub-header">Transaction History</h2>
	          			<div>
	            			<table class="table table-striped">
	                			<tr>
	                  				<th>From</th>
	                  				<th>To</th>
	                  				<th>Departure</th>
	                  				<th>Seat</th>
	                  				<th>Transaction Date</th>
	                  				<th></th>
	               				</tr>
	               				<tbody id="tickets">
				  				</tbody>
	            			</table>
	          			</div>
					</div>
										
					<div id="ShoppingCartView">
						<h2 class="sub-header">Shopping Cart</h2>
	          			<div>
	          				<table id="gridCheckOut" class="table table-striped">
	                			<tr>
	                  				<th>From</th>
	                  				<th>To</th>
	                  				<th>Date</th>
	                  				<th>Seat</th>
	                  				<th>Price</th>
	                  				<th>Operation</th>
	               				</tr>
	               				<tbody id="CartTicket">
				  				</tbody>
	            			</table>
	          			</div>
	          			<div align="right">
							<div class='checkout'>
								<div><b>Subtotal:</b><span id="subtotal" class="sum js-subtotal">$0</span></div>
								<div><b>Taxes (5%):</b><span id="taxes" class='sum js-taxes'>$0</span></div>
								<div><b>Total:</b><span id="total" class='sum js-total'>$0</span></div>
								<div><button id="checkoutBtn" class="button orange">Checkout</button></div>
							</div>
						</div>	
					</div>
				</sec:authorize>
				<!-- ADMIN -->	
				<sec:authorize access="hasRole('ROLE_ADMIN')">
						<!-- Hidden no use table -->
						<div style="display:none">
	          				<table id="grid" class="table table-striped">
	                			<tr>
	                  				<th>From</th>
	                  				<th>To</th>
	                  				<th>Date</th>
	                  				<th>Price</th>
	               				</tr>
	               				<tr>
	               					<td>NJ</td>
	               					<td>IL</td>
	               					<td>17-MAY-2015</td>
	               					<td>100</td>
	               					<td><input id="check" type="checkbox" checked="checked" onclick="calculate()"/></td>
	               				</tr>
	               				<tr>
	               					<td>NJ</td>
	               					<td>CA</td>
	               					<td>18-MAY-2015</td>
	               					<td>200</td>
	               					<td><input id="check" type="checkbox" checked="checked" onclick="calculate()"/></td>
	               				</tr>
	            			</table>
	          			</div>
	          			<div align="right" style="display:none">
							<div class='checkout'>
								<div><b>Subtotal:</b><span id="subtotal" class="sum js-subtotal">$32.86</span></div>
								<div><b>Taxes (5%):</b><span id="taxes" class='sum js-taxes'>$1.64</span></div>
								<div><b>Total:</b><span id="total" class='sum js-total'>$39.50</span></div>
								<div><button id="checkoutBtn" class="button orange">Checkout</button></div>
							</div>
						</div>	
						<!-- Hidden no use table -->
						
						
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
											<td><input type="number" style="height:24px;width:100px" id="AT_Amount" placeholder="amount"></td>
											<td><input type="number" style="height:24px;width:70px;" id="AT_Price" placeholder="price"></td>
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
											<th align="left" >Operation:</th>
										</tr>	
										
	               						<tbody id="ManageTicketTable">
				  						</tbody>
									</table>
									<br />
					          </div>
						</div>
					</div>
					
					<div id="AnalysisView" style="display:none">
						<h3 class="sub-header">Tickets</h3>
	          			<div>
	          				<table id="gridAnalysis" class="table table-striped">
	                			<tr>
	                  				<th>From</th>
	                  				<th>To</th>
	                  				<th>Date</th>
	                  				<th>Seat</th>
	                  				<th>Price</th>
	                  				<th></th>
	               				</tr>
	               				<tbody id="analysisTicket">
				  				</tbody>
	            			</table>
	          			</div>
					</div>
<!-- 					<table>
						<tr>
							<td><div id="piechart_3d" align="center"></div></td>
						</tr>
					</table>
 -->					
 
					<!-- Modal -->
					<div class="modal fade" id="myModal" tabindex="-1">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal">&times;</button>
					        <h4 class="modal-title" id="myModalLabel">Pop Up</h4>
					      </div>
					      <div class="modal-body">
					       <div id="piechart_3d" style="width: 150px; height: 200px;"></div>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					      </div>
					    </div>
					  </div>
					</div>
 
				</sec:authorize>
				
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
					<div id="ProfileView" style="display:none">
						<h2 class="sub-header">Update Profile</h2>
							<div class="alert" style="display:none;" id="v_email">
								<p>Email is invalid</p>
							</div>
	            			<table class="table table-striped">
	                			<tr>
	                  				<th>FirstName</th>
	                  				<th>LastName</th>
	                  				<th>Email</th>
	               				</tr>
	               				<tr>
	               					<td><input id="u_firstName" type="text" maxlength="20"/></td>
	               					<td><input id="u_lastName" type="text" maxlength="20"/></td>
	               					<td><input id="u_email" type="text" maxlength="50" /></td>
	               				</tr>
	            			</table>
	            			<button onclick="updateUser()" class="button orange">Submit</button>
							<br />
							<br />
						<h2 class="sub-header">Change Password</h2>
							<div class="alert" style="display:none;" id="v_password">
								<p>Password is length must be between 8 to 16 charactors</p>
							</div>
							<div class="alert" style="display:none;" id="ve_password">
								<p>Password and Comfirm Password must be same</p>
							</div>

	            			<table class="table table-striped">
	                			<tr>
	                  				<th>New Password</th>
	                  				<th>Comfirm New Password</th>
	               				</tr>
	               				<tr>
	               					<td><input id="u_password" type="password"/></td>
	               					<td><input id="cu_password" type="password"/></td>
	               				</tr>
	            			</table>
	            			<button onclick="updatePassword()" class="button orange">Submit</button>

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
