
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
    
    
    <script src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script>
		$(document).ready(function() {
 			$.ajax({
				url: "/Demand1/restful/Stations.html",
				type: "get",
				dataType: "json",
				success:showData
			});
			
			$("#navigation-menu li a").on("click", function(event){
				removeActiveClass();
				$(event.target).parent().addClass("active");
				var viewTag = event.target.id + "View";
				$("#"+viewTag).show();
			});
		});
		
		function showData(data) {
			var rows = "";
			$("#users").empty();
			$(data).each(function(i, item) {
				var station_id = item.id;
				var station_name = item.station;
				rows = "<tr><td>" + station_id + "</td><td>" + station_name + "</td></tr>";
				$(rows).appendTo("#users");
			});
		}
		
		function removeActiveClass() {
			$("#MyTrip").parent().removeClass("active");
			$("#History").parent().removeClass("active");
			$("#Profile").parent().removeClass("active");
			
			$("#MyTripView").hide();
		}
		
		
	</script>
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
            			<li class="active"><a id="History" href="#History">History</a></li>
          			</sec:authorize>	
          		</ul>
        	</div>
        	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
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
	              					<tbody id="users">
				  					</tbody>
	            			</table>
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
