<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>

<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Results</title>
    <link href="<c:url value="/css/coverInTicket.css" />" rel="stylesheet">
    <link href="<c:url value="/css/modal.css" />" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet" />
    <script src="<c:url value="/js/jquery-1.11.1.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.js"/>"></script>
   	<script src="<c:url value="/js/jquery-ui.js"/>"></script>
    <script src="<c:url value="/js/msi-jquery.js"/>"></script>
  	<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyAUFsv4PDnx3pCIa50N2K6O7CpFi8AW4R0&sensor=true&language=en"></script>

    <style>
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
	.hideTd{
		display:none;
	}
	.close-CSS {
	    background-image: url( <c:url value="/img/googlemaps.png"/> );
	    background-size: 15px 15px;
	    height: 19px;  
	    width: 19px;
	}
    </style>
	<script>
		var map;
		var infowindow;
		
		$(document).ready(function() {
			$('#BackToLogin').click(function(){
				window.location.href='http://localhost:8080/Demand1/#login_form';
			})
			$('#GoToUserAccount').click(function(){
				window.location.href='http://localhost:8080/Demand1/auth/user.html';
			})		

		});
		
		// init map setting
		function initialize(lat, lng) 
	    {
	        var mapCenter = new google.maps.LatLng(lat, lng);

	        var myOptions = {
	            zoom:15,
	            mapTypeId: google.maps.MapTypeId.ROADMAP,
	            center: mapCenter
	        }
			
			infowindow = new google.maps.InfoWindow({
				content: "default"
			});

	        map = new google.maps.Map(document.getElementById("map_direction"), myOptions);
		}
		
		function addMark(lat,lng,title) {
			// add marker to map
			var location = new google.maps.LatLng(lat, lng);
			var marker = new google.maps.Marker({
				position: location,
				title : title,
				bounds : true,
				map: map,
				html: '<div id="content">' +
				'<div id="siteNotce">' +
				'</div>' +
				'<div id="bodyContent">' +
				'<p><b>' + title + '</b>' +
				'<p><img src=\'http://maps.googleapis.com/maps/api/streetview?size=75x75&location=' + lat + ',' + lng +'&fov=120&heading=150&pitch=10&sensor=false&key=AIzaSyAUFsv4PDnx3pCIa50N2K6O7CpFi8AW4R0 \' width=75px"></img>' +
				'</div>' +
				'</div>'
			});
													
			// add click event for infowindows
			google.maps.event.addListener(marker, 'click', function() {
				infowindow.setContent(this.html);
				infowindow.open(map,this);
			});

		}
		function showUpMap(clicked_id) {
			var gps = StationGPSList[clicked_id];
			var gps = gps.split(",");
			location.href='#map_form';
			initialize(gps[0],gps[1]);
	        addMark(gps[0], gps[1], clicked_id);
		}
		
	</script>
</head>
<body>
   <header id="header" class="navbar navbar-default navbar-fixed-top">
        <div >
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <span class="navbar-brand" data-i18n="title">Ticket Results</span>
          		<ul class="nav navbar-nav navbar-right">
            		 <li><a href="/Demand1/">Home</a></li>
          		</ul>
            </div>
        </div>
    </header>
    
    <a href="#" class="overlay" id="success_addToCart"></a>
    <div class="popup">
    	<h2>Congratulations!</h2>
	    <p>Your tickets have been added to cart successfully!</p>
    	<a class="close" href="#"></a>
    </div> 
    <!-- need to get invalid ticket data which can be shown on popup -->
    <a href="#" class="overlay" id="failure_addToCart"></a>
    <div class="popup">
    	<h2>Sorry!</h2>
	    <p>Failed to add your tickets to cart!</p>
    	<a class="close" href="#"></a>
    </div> 
    
    <div class="container-fluid">
        <div class="row">
            
            <div class="col-md-9">
                
                <table id="grid" class="table table-condensed table-hover table-striped">
                    <thead>
                        <tr>
                            <th data-column-id="from">From</th>
                            <th data-column-id="to">To</th>
                            <th data-column-id="price" data-order="asc">Price</th>
                            <th data-column-id="date" data-type="date">Date</th>
                            <th data-column-id="checkbox">Purchase</th>
                        </tr>
                    </thead>
                    <tbody>
						<c:forEach var="ticket" items="${resultTickets}">
							<c:if test="${ticket.active == 'true'}">
	 							<c:if test="${ticket.avaiNumber > 0}">
									<tr>
										<td>${ticket.start} <button id="<c:out value="${ticket.start}"/>" onclick="showUpMap(id)" class="close-CSS"></button></td>
										<td>${ticket.destination} <button id="<c:out value="${ticket.destination}"/>" onclick="showUpMap(id)" class="close-CSS"></button></td>
										<td>${ticket.price}</td>
										<td><fmt:formatDate value="${ticket.date}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
										<td><input id="check" type="checkbox"/></td>
									</tr>
	 							</c:if>
	 						</c:if>
						</c:forEach>
                    </tbody>
                </table>
                <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                	<button id="addToCart" name="addToCart" onclick="check()" class="button orange">Add to Cart</button>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
            		<button id="BackToLogin" class="button orange">Go back to login</button>
            	</sec:authorize>
            </div>
        </div>
    </div>
          <!-- popup form #1 -->
	       <a href="#" class="overlay" id="map_form"></a>
	       
	       <div class="popup">
				<div id="map_direction" style="width:400px;height:400px;"></div>
	            <a class="close" href="#"></a>
	       </div>	
	       <!-- ----------- -->

    <footer id="footer">
    </footer>
</body>
</html>