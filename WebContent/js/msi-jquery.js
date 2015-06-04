		    var StationGPSList = {
				"NY,New York,Penn Station":"40.750580,-73.993584",
				"NY,New York,Jamaica":"40.6994342,-73.8087286",
				"NY,New York,Yonkers Amtrak":"40.935616,-73.902377",
				"NJ,Newark,Newark Airport Railroad Station":"40.7040941,-74.1902228",
				"NJ,Princeton,West Trenton":"40.257778,-74.815278",
				"NJ,Princeton,Princeton Junction":"40.3149842,-74.6232774",
				"NJ,Hoboken,Hoboken terminal":"40.7335515,-74.0282383",
				"MA,Boston,North Station":"42.366432,-71.062285",
				"MA,Boston,Haymarket Station":"42.3621253,,-71.0578275",
				"MA,Boston,Back Bay Station":"42.347317,-71.075828",
				"CA,San Francisco,San Jose Diridon Station":"37.3283575,-121.9016538",
				"CA,San Francisco,San Francisco Caltrain Station":"37.776439,-122.394323",
				"CA,Los Angeles,Union Station Metrolink Station":"34.056219,-118.236502",
				"CA,Los Angeles,Pomona-North Metrolink Station":"34.093639,-117.753052",
				"CA,Los Angeles,Santa Ana Metrolink Station":"33.7509307,-117.8570025",
				"TX,Dallas,Dallas Union Station":"32.7765203,-96.804774",
				"TX,Oklahoma,Oklahoma City Amtrak":"35.46549,-97.512749",
				"TX,Austin,Howard Station":"30.43999,-97.701734",
				"FL,Orlando,Orlando Amtrak Train Station":"28.525926,-81.381516",
				"FL,Altamonte Springs,Altamonte Springs SunRail Station":"28.3820095,-81.2934944",
				"FL,Orlando,Church Street Station":"28.540344,-81.381504",
				"FL,Miami,Miami Amtrak Train Station":"25.849554,-80.257759",
				"NC,Greensboro,Galyon Transportation Center":"36.0518078,-79.8047502",
				"NC,Selma,Selma-Smithfield Amtrak":"35.5281076,-78.2779081",
				"NC,Fayetteville,Fayetteville Amtrak":"35.0501897,-78.8918496",
				"NC,Camden,Camden Amtrak":"34.2483255,-80.6255226",
				"NC,Columbia,Columbia Amtrak":"33.9926965,-81.0398496",
				"GA,Atlanta,Amtrak Station":"32.6781248,-83.223252",
				"GA,Savannah,Savannah Amtrak":"32.063683,-81.152501",
				"GA,Toccoa,Toccoa Amtrak":"34.578456,-83.331542"
		    };


	function IsEmail(email) {
		  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		  return regex.test(email);
	}
    function url_redirect(options){
        var $form = $("<form />");
        
        $form.attr("action",options.url);
        $form.attr("method",options.method);
        
        for (var data in options.data)
        $form.append('<input type="hidden" name="'+data+'" value="'+options.data[data]+'" />');
         
        $("body").append($form);
        $form.submit();
    }
	function addZeros(n) {
		  return (n < 10)? '0' + n : '' + n;
	}
	
	/*Load Current User Info*/
	function loadUserInfo() {
  	    $.ajax({
  			url: "/Demand1/auth/GetCurrentUser.html",
  			type: "GET",
  			dataType: "json",
  			success:initUserData
  		});		
	}
	
	function initUserData(data) {
		$("#u_firstName").val(data.firstname);
		$("#u_lastName").val(data.lastname);
		$("#u_email").val(data.email);
	}
	
    /*This function use to update user data*/
    
    function updateUser(){
  		$('#v_email').hide();

  		if(!IsEmail($("#u_email").val())){
  			$("#v_email").show();
  			return false;
  		}
  		var formData = {firstname : $("#u_firstName").val(), lastname : $("#u_lastName").val(), email : $("#u_email").val()};
  	    $.ajax({
  			url: "/Demand1/auth/UpdateUser.html",
  			type: "post",
  			data: formData,
  			dataType: "json",
  			success: foo
  		});

    }
        
    function updatePassword() {
  		$("#v_password").hide();
  		$("#ve_password").hide();
  		if($("#u_password").val().length<8||$("#u_password").val().length>16){
  			$("#v_password").show();
  			return false;
  		}
  		else if($("#u_password").val() != $("#cu_password").val()) {
  			$("#ve_password").show();
  			return false;
  		}
  		var formData = {password : $("#u_password").val()};
  	    $.ajax({
  			url: "/Demand1/auth/UpdatePassword.html",
  			type: "post",
  			data: formData,
  			dataType: "json",
  			success: foo
  		});
    }
    
    function foo(data){
    	alert("Update Success!");
    	$("#u_password").val("");
    	$("#cu_password").val("");
    }

    /*Update user function end here*/
    
    
    /*Load User's Cart*/
    function loadAllUserCart() {
	    $.ajax({
  			url: "/Demand1/auth/GetCartItemByUser.html",
  			type: "GET",
  			dataType: "json",
  			success: renderUserCart
  		});
    }
    function renderUserCart(data) {
    	var add=0;
    	$("#CartTicket").empty();
		$(data).each(function(i,item) {
			var d = new Date(item.start);
			rows = "<tr><td style=\"display:none\">" + item.itemId + "</td><td>" + item.from + "</td><td>"+item.to+"</td><td>"+
			d.getFullYear() + "/" + (d.getMonth()+1) + "/" + d.getDate() 
			+"	"+d.getHours()+":"+d.getMinutes()+ "</td><td>"+item.seatNo +"</td><td>"+item.price+"</td><td><input id=" + item.itemId + " type=\"button\" class=\"btn btn-default\" value=\"remove\" onclick=\"sendID(id)\"/></td></tr>";
			$(rows).appendTo("#CartTicket");
			add+=parseInt(item.price);
		});
		subtotal.innerHTML=add.toFixed(2);
		var taxesResult=add*0.05;
		taxes.innerHTML=taxesResult.toFixed(2);
		var totalResult=taxesResult+add;
		total.innerHTML=totalResult.toFixed(2);
    }
    
    function sendID(data){
    	var id={CartItem:data};
    	$.ajax({
    		url: "/Demand1/auth/RemoveCartItem.html",
  			type: "post",
  			data: id,
  			dataType: "json",
  			success: renderUserCart
    	});
    }
    /*Load User's Cart function end here*/

    /*Show Payment function*/
    function loadCheckOutUserCart() {
	    $.ajax({
  			url: "/Demand1/auth/GetCartItemByUser.html",
  			type: "GET",
  			dataType: "json",
  			success: renderCheckOutUserCart
  		});
    }
    function renderCheckOutUserCart(data) {
    	var add=0;
    	$("#CartTicket").empty();
		$(data).each(function(i,item) {
			var d = new Date(item.start);
			rows = "<tr><td style=\"display:none\">" + item.itemId + "</td><td>" + item.from + "</td><td>"+item.to+"</td><td>"+
			d.getFullYear() + "/" + (d.getMonth()+1) + "/" + d.getDate() 
			+"-"+d.getHours()+":"+d.getMinutes()+ "</td><td style=\"display:none\">"+ item.seatNo +"</td><td>"+ "$" + item.price+"</td></tr>";
			$(rows).appendTo("#CartTicket");
			add+=parseInt(item.price);
		});
		subtotal.innerHTML=add.toFixed(2);
		var taxesResult=add*0.05;
		taxes.innerHTML=taxesResult.toFixed(2);
		var totalResult=taxesResult+add;
		total.innerHTML=totalResult.toFixed(2);
    }
    /*Show Payment function end here*/

    /*This function use to load all tickets from db*/
    function loadAllTicket() {
    	$.ajax({
			  url:"/Demand1/restful/Tickets.html",
			  type:"GET",
			  dataType:"json",
			  success:reloadAllTicketAfterNewTicket
		});
    }
    //
    
    /*load all tickets function end here*/
    function loadUserHistory() {
		$.ajax({
			url: "/Demand1/auth/UserHistory.html",
			type: "get",
			success:loadHistory
		});   	
    }
	function loadHistory(data) {
		var rows = "";
		$("#tickets").empty();
		$(data).each(function(i,item) {
			var d = new Date(item.tran_time);
			var dataCols = item.ticket_id.split("#");
			var dateTicket = new Date(dataCols[2].slice(0, 4), dataCols[2].slice(4, 6) - 1, dataCols[2].slice(6, 8), dataCols[2].slice(8, 10), dataCols[2].slice(10, 12));
			rows = "<tr><td>" + dataCols[0] + "</td><td>" + dataCols[1] + "</td><td>" + dateTicket.getFullYear() 
			+ "-" + addZeros((dateTicket.getMonth()+1)) + "-" + addZeros(dateTicket.getDate())
			+"	"+ addZeros(dateTicket.getHours())+":"+ addZeros(dateTicket.getMinutes()) + "</td><td>" 
			+ item.seat_no + "</td><td>" + d.getFullYear() + "-" + addZeros((d.getMonth()+1)) + "-" + addZeros(d.getDate())
			+"	"+ addZeros(d.getHours())+":"+ addZeros(d.getMinutes())+ "</td><td>";
			var dateNow = new Date();
			if(dateNow < dateTicket) {
				var combine_key = item.ticket_id.replace(/ /gi, "_");
				rows = rows + "<input id=" + combine_key + "+" + item.seat_no + " type=\"button\" class=\"btn btn-default\" value=\"refund\" onclick=\"ticketRefund(id)\"/></td></tr>";
			}
			else {
				rows = rows + "</td></tr>";
			}
			$(rows).appendTo("#tickets");
		});
	}
	function ticketRefund(id) {
    	var data={key:id};
    	$.ajax({
    		url: "/Demand1/auth/RefundTicket.html",
  			type: "post",
  			data: data,
  			dataType: "json",
  			success: loadHistory
    	});
	}
    /*This function use to load user's transaction from oracle*/
    
    /*load history function end here*/
    
    
    /*This function use to new a ticket*/
    function addTicket() {
			var formData = {From : $("#AT_From").val(), To : $("#AT_To").val(), Time : $("#AT_Date").val()+" "+$("#AT_Hour").val()+":"+$("#AT_Min").val(), Amount : $("#AT_Amount").val(), Price: $("#AT_Price").val(), Seat : $("#AT_Seat").val()};
  	    	$.ajax({
  				url: "/Demand1/admin/NewTicket.html",
  				type: "GET",
  				data: formData,
  				dataType: "json",
  				success: reloadAllTicketAfterNewTicket
  			}); 
    }
    
    function reloadAllTicketAfterNewTicket(data) {
		var rows = "";
		$("#ManageTicketTable").empty();
		$(data).each(function(i,item) {
			var d = new Date(item.date);
			rows = "<tr><td>" + item.start + "</td><td>"+item.destination+"</td><td>"+
			d.getFullYear() + "-" + addZeros((d.getMonth()+1)) + "-" + addZeros(d.getDate()) + 
			" "+ addZeros(d.getHours()) + ":" + addZeros(d.getMinutes())+ "</td><td>"+item.avaiNumber+"/"+item.amount +"</td><td>"+item.price;
			var key = item.start+"#"+item.destination+"#"+d.getFullYear()+addZeros((d.getMonth()+1))+addZeros(d.getDate())+addZeros(d.getHours())+addZeros(d.getMinutes());
			key = key.replace(/ /gi, "_");
			
			if(item.active == "true") {
				rows = rows + "</td><td><input id=" + key + " type=\"button\" class=\"btn btn-default\" value=\"disable\" onclick=\"disableTicket(id)\"/>";
			}
			else {
				rows = rows + "</td><td><input id=" + key + " type=\"button\" class=\"btn btn-default\" value=\"enable\" onclick=\"enableTicket(id)\"/>";
				if(item.avaiNumber == item.amount) {
					rows = rows + "<input id=" + key + " type=\"button\" class=\"btn btn-default\" value=\"delete\" onclick=\"deleteTicket(id)\"/>";
				}
			}
			rows = rows + "</td></tr>";
			$(rows).appendTo("#ManageTicketTable");
		});
    } 
    /*new a ticket function end here*/
    
    /*Enable And Disable And Delete ticket*/
    function disableTicket(key) {
		var formData = {key : key};
	    	$.ajax({
				url: "/Demand1/admin/DisableTicket.html",
				type: "POST",
				data: formData,
				dataType: "json",
				success: reloadAllTicketAfterNewTicket
			});    
    }
    function enableTicket(key) {
		var formData = {key : key};
    	$.ajax({
			url: "/Demand1/admin/EnableTicket.html",
			type: "POST",
			data: formData,
			dataType: "json",
			success: reloadAllTicketAfterNewTicket
		});    
    }
    function deleteTicket(key) {
		var formData = {key : key};
    	$.ajax({
			url: "/Demand1/admin/DeleteTicket.html",
			type: "POST",
			data: formData,
			dataType: "json",
			success: reloadAllTicketAfterNewTicket
		});    
    }
    
    /*This function use to add ticket to cart*/
    function check(){
    	var formData=[];
    	$('#grid input[type=checkbox]:checked').each(function(){ 
			var row = $(this).parent().parent();
			var rowcells = row.find('td');
			var item = {};
			item["from"] = $(rowcells[0]).html();
			item["to"] = $(rowcells[1]).html();
			item["time"] =$(rowcells[3]).html();
			formData.push(item);
		});
    	$.ajax({
			  url:"/Demand1/auth/AddCart.html",
			  contentType: 'application/json',
			  type:"post",
			  data: JSON.stringify(formData),
			  dataType:"json",
			  success:checkResult
		});
    }
	function checkResult(data) {
		
		if(data.length != 0) {
			var rows = '';
			$(data).each(function(i,item) {
				var d = new Date(item.date);
				rows = rows + "From : " + item.start + "\nTO : "+item.destination+"\nOn "+
				d.getFullYear() + "-" + addZeros((d.getMonth()+1)) + "-" + addZeros(d.getDate()) + 
				" "+ addZeros(d.getHours()) + ":" + addZeros(d.getMinutes())+ "\nTicket already sold out.\n\n";
			});
			rows = rows + "Please check out in 2 min, or the reservation will be canceled.";
			alert(rows);
		}
		else {
			alert("Please check out in 2 mins, or the reservation will be canceled.");
		}
		
		window.location.href="/Demand1/auth/user.html";
/*		if(data[0].result == "yes") {
			location.href = "#success_addToCart";
		}
		else{
			window.location = "#failure_addToCart";
		}*/
	}
	/*add to cart function end here*/
	
	/*This function use to query ticket from db*/
	function ticket(){
        url_redirect({url: "/Demand1/content/ticket.html",
           			  method: "post",
             		  data: {"From":$("#From").val(), "To":$("#To").val(), "Time":$("#Leave").val()+"/"+$("#At").val()}
		});
	}
	/*Ticket query function end here*/
	
	/*This function use to check out*/
	function checkoutPage(){
    	var formData=[];
    	$('#CartTicket tr').each(function(){ 
			var rowcells = $(this).find('td');
			var dateVar = $(rowcells[3]).text();
			var dsplit = dateVar.split("/");
			var tsplit = dsplit[2].split("-");
			var hmsplit = tsplit[1].split(":");
			var time = dsplit[0] + "-" + dsplit[1] + "-" + tsplit[0] + "T" +  parseInt(hmsplit[0], 10) + ":" +  parseInt(hmsplit[1], 10) + ":00.000Z";
			var price = $(rowcells[5]).html().split("$");
			var item = {};
			item["itemId"] = $(rowcells[0]).html();
			item["from"] = $(rowcells[1]).html();
			item["to"] = $(rowcells[2]).html();
			item["start"] = time;
			item["seatNo"] = $(rowcells[4]).html();
			item["price"] = price[1];
			item["addTime"] = time;
			formData.push(item);
		});	
    	$.ajax({
			  url:"/Demand1/auth/CheckOut.html",
			  contentType: 'application/json',
			  type:"post",
			  data: JSON.stringify(formData),
			  dataType:"json",
			  success:checkOutResult
		});
	}
	
	function checkOutResult(data) {
		alert("Success!");
		window.location.href="/Demand1/";		
	}
	/*check out function end here*/
	
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
  		$("#r_alreadyexisted").hide();
		
  		var judge = true;
  		
	  	if($("#r_username").val().length < 8 ) {
	  		$("#r_usernameReq").show();
	  		judge = false;
	  	}
	  	if ($("#r_password").val().length < 8 ) {
	  		$("#r_passwordReq").show();
	  		judge = false;
	  	}
	  	if ($("#r_retypepassword").val() != $("#r_password").val()) {
	  		$('#r_retypepasswordReq').show();
	  		judge = false;
	  	}
	  	if (!IsEmail($("#r_email").val())) {
	  		$("#r_emailReq").show();
	  		judge = false;
	  	}
	  	if ($("#r_lastname").val().length == 0) {
	  		$("#r_lastnameReq").show();
	  		judge = false;
	  	}
	  	if ($("#r_firstname").val().length == 0) {
	  		$("#r_firstnameReq").show();
	  		judge = false;
	  	}
	  	if(judge) {
 			var formData = {r_username : $("#r_username").val(), r_password : $("#r_password").val(), r_email : $("#r_email").val() , r_firstname : $("#r_firstname").val() , r_lastname : $("#r_lastname").val()};
			$.ajax({
				url: "/Demand1/restful/RegisterUser.html",
				type: "post",
				data: formData,
				dataType: "json",
				success:registerResult
			});
		}
	}

	function registerResult(data) {
		if(data[0].result == "yes") {
			window.location.href = "/Demand1/#Success_form";
		}
		else {
	  		$("#r_usernameReq").hide();
	  		$("#r_passwordReq").hide();
	  		$('#r_retypepasswordReq').hide();
	  		$("#r_emailReq").hide();
	  		$("#r_lastnameReq").hide();
	  		$("#r_firstnameReq").hide();
	  		$("#r_alreadyexisted").show();
		}
	}
	
	function forgetPWDValidation() {
		$("#rs_emailReq").hide();
	  	if (!IsEmail($("#rs_email").val())) {
	  		$("#rs_emailReq").show();
	  	}
	  	else {
 			var formData = {email : $("#rs_email").val()};
			$.ajax({
				url: "/Demand1/restful/ResetPwd.html",
				type: "post",
				data: formData,
				dataType: "json",
				success:resetResult
			});	  		
	  	}
	}	

	function resetResult(data) {
		if(data[0].result == "yes") {
			window.location.href = "/Demand1/#ResetSuccess_form";
		}
		else {
	  		$("#r_usernameReq").hide();
	  		$("#r_passwordReq").hide();
	  		$('#r_retypepasswordReq').hide();
	  		$("#r_emailReq").hide();
	  		$("#r_lastnameReq").hide();
	  		$("#r_firstnameReq").hide();
	  		$("#r_alreadyexisted").show();
		}
	}
	
	
	function keyboardValidate(evt) {
		  var theEvent = evt || window.event;
		  var key = theEvent.keyCode || theEvent.which;
		  key = String.fromCharCode( key );
		  var regex = /^[0-9]+$/;
		  if( !regex.test(key) ) {
		    theEvent.returnValue = false;
		    if(theEvent.preventDefault) theEvent.preventDefault();
		  }
	}
	$(function() {
		    var availableTags = [
		         				"NY,New York,Penn Station",
		        				"NY,New York,Jamaica",
		        				"NY,New York,Yonkers Amtrak",
		        				"NJ,Newark,Newark Airport Railroad Station",
		        				"NJ,Princeton,West Trenton",
		        				"NJ,Princeton,Princeton Junction",
		        				"NJ,Hoboken,Hoboken terminal",
		        				"MA,Boston,North Station",
		        				"MA,Boston,Haymarket Station",
		        				"MA,Boston,Back Bay Station",
		        				"CA,San Francisco,San Jose Diridon Station",
		        				"CA,San Francisco,San Francisco Caltrain Station",
		        				"CA,Los Angeles,Union Station Metrolink Station",
		        				"CA,Los Angeles,Pomona-North Metrolink Station",
		        				"CA,Los Angeles,Santa Ana Metrolink Station",
		        				"TX,Dallas,Dallas Union Station",
		        				"TX,Oklahoma,Oklahoma City Amtrak",
		        				"TX,Austin,Howard Station",
		        				"FL,Orlando,Orlando Amtrak Train Station",
		        				"FL,Altamonte Springs,Altamonte Springs SunRail Station",
		        				"FL,Orlando,Church Street Station",
		        				"FL,Miami,Miami Amtrak Train Station",
		        				"NC,Greensboro,Galyon Transportation Center",
		        				"NC,Selma,Selma-Smithfield Amtrak",
		        				"NC,Fayetteville,Fayetteville Amtrak",
		        				"NC,Camden,Camden Amtrak",
		        				"NC,Columbia,Columbia Amtrak",
		        				"GA,Atlanta,Amtrak Station",
		        				"GA,Savannah,Savannah Amtrak",
		        				"GA,Toccoa,Toccoa Amtrak"
		    ];
		    $( "#To" ).autocomplete({
		    	source: availableTags
		    });
		    $( "#From" ).autocomplete({
			    source: availableTags
			});
		    $( "#AT_To" ).autocomplete({
		    	source: availableTags
		    });
		    $( "#AT_From" ).autocomplete({
			    source: availableTags
			});
	});