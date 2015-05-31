<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>

<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Results</title>
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
            </div>
            
        </div>
    </header>

    <div class="container-fluid">
        <div class="row">
            
            <div class="col-md-9">
                
                <table id="grid" class="table table-condensed table-hover table-striped">
                    <thead>
                        <tr>
         					<th data-column-id="amount">Amount</th>
                            <th data-column-id="from">From</th>
                            <th data-column-id="to">To</th>
                            <th data-column-id="price" data-order="asc">Price</th>
                            <th data-column-id="date" data-type="date">Date</th>
                            <th data-column-id="checkbox">Select</th>
                        </tr>
                    </thead>
                    <tbody>
						<c:forEach var="ticket" items="${resultTickets}">
							<tr>
								<td class="hideTd">${ticket.id }</td>
								<td><input id="amount" type=text size="1"/></td>
								<td>${ticket.from_loc.station}</td>
								<td>${ticket.to_loc.station}</td>
								<td>${ticket.date}</td>
								<td>${ticket.price}</td>
								<td><input id="check" type="checkbox"/></td>
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

    
    <!-- <script>
        $(function ()
        {
            var data = [];

            var grid = $("#grid").bootgrid({
                pagination: 3,
                selection: true,
                multiSelect: true,
                formatters: {
                    "quantity": function (column, row)
                    {
                        return "<input id=\"" + row.id + "-quantity\" type=\"text\" class=\"quantity\" value=\"" + row.quantity + "\" />";
                    }
                }
            }).on("loaded.rs.jquery.bootgrid", function (e)
            {
                // Resets the selected data array on reload, searching, sorting or changing page
                data = [];

                grid.on("keyup", "input.quantity", function ()
                {
                    e.stopPropagation();

                    // Array.first is an extension of bootgrid
                    var $this = $(this),
                        rowId = +$this.attr("id").split("-")[0], // "+" to convert the a string to an integer
                        item = data.first(function (item) { return item.id === rowId; });
                    if (item != null)
                    {
                        item.quantity = $this.val();
                    }
                });
            }) .on("selected.rs.jquery.bootgrid", function (e, selectedRows)
            {
                var row, quantity;
                for (var i = 0; i < selectedRows.length; i++)
                {
                    row = selectedRows[i];

                    // Array.contains is an extension of bootgrid
                    if (!data.contains(function (item) { return item.id === row.id; }))
                    {
                        quantity = grid.find("#" + row.id + "-quantity").val();
                        data.push({ id: row.id, quantity: quantity || 0 });
                    }
                }
            }).on("deselected.rs.jquery.bootgrid", function (e, deselectedRows)
            {
                var row;
                for (var i = 0; i < deselectedRows.length; i++)
                {
                    row = deselectedRows[i];
                    for (var j = 0; j < data.length; j++)
                    {
                        if (data[j].id === row.id)
                        {
                            data.splice(j, 1);
                            return;
                        }
                    }
                }
            });

             $("#send").on("click", function ()
            {
                var params = $.param({ "": data });
                alert(decodeURIComponent(params));
            }); 
        });
    </script> -->
</body>
</html>