<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>

<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Results</title>
    <link href="<c:url value="/css/coverInTicket.css" />" rel="stylesheet">
    <link href="<c:url value="/css/modal.css" />" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet" />
    <link href="<c:url value="/css/jquery.bootgrid.css"/>" rel="stylesheet" />
    <script src="<c:url value="/js/moderniz.2.8.1.js"/>"></script>
    <script src="<c:url value="/js/jquery-1.11.1.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/js/jquery.bootgrid.js"/>"></script>
    <script src="<c:url value="/js/msi-jquery.js"/>"></script>
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
    </style>
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
                            <th data-column-id="checkbox">Select</th>
         					<th data-column-id="amount">Amount</th>
                        </tr>
                    </thead>
                    <tbody>
						<c:forEach var="ticket" items="${resultTickets}">
							<tr>
								<td class="hideTd">${ticket.id }</td>
								<td>${ticket.from_loc.station}</td>
								<td>${ticket.to_loc.station}</td>
								<td>${ticket.date}</td>
								<td>${ticket.price}</td>
								<td><input id="check" type="checkbox"/></td>
								<td><input id="amount" type=text size="1" onkeypress='keyboardValidate(event)' maxlength="4" value="0"/></td>
							</tr>
						</c:forEach>
                    </tbody>
                </table>
                <button id="addToCart" name="addToCart" onclick="check()" class="button orange">Add to Cart</button>
            	<button id="goToMyCart" name="goToMyCart" onclick="#" class="button orange">Go to My Cart</button>
            </div>
        </div>
    </div>
    <footer id="footer">
    </footer>
</body>
</html>