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
    
    function urlbody_redirect(options) {
    	var $form = $("<form />");
        
        $form.attr("action",options.url);
        $form.attr("method",options.method);
        
        for (var data in options.data)
        $form.append('<input type="hidden" name="'+data+'" value="'+options.data[data]+'" />');
         
        $("body").append($form);
        $form.submit();
    	
    }
    
    /*This function use to update user data*/
    
    function updateUser(){
  		$('#v_email').hide();
  		$("#v_password").hide();
  		var flag=true;
  		if(!IsEmail($("#email").val())){
  			$("#v_email").show();
  			flag=false;
  		}
  		if($("#password").val().length<8||$("#password").val().length>16){
  			$("#v_password").show();
  			flag=false;
  		}
  		if(flag){
  			var formData = {firstname : $("#firstName").val(), lastname : $("#lastName").val(), email : $("#email").val() , password : $("#password").val()};
  	    	$.ajax({
  				url: "/Demand1/auth/UpdateUser.html",
  				type: "post",
  				data: formData,
  				dataType: "json",
  				success: foo
  			});
  		}
    }
    
    function foo(data){
    	
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
			(d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear()
			+"	"+d.getHours()+":"+d.getMinutes()+ "</td><td>"+item.seatNo +"</td><td>"+item.price+"</td><td><input id=" + item.itemId + " type=\"button\" value=\"remove\" onclick=\"sendID(id)\"/></td></tr>";
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
    

    /*This function use to load all tickets from db*/
    function loadAllTicket() {
    	$.ajax({
			  url:"/Demand1/restful/Tickets.html",
			  type:"GET",
			  dataType:"json",
			  success:reloadAllTicketAfterNewTicket
		});
    }
    /*load all tickets function end here*/
    
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
			(d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear()
			+"	"+d.getHours()+":"+d.getMinutes()+ "</td><td>"+item.avaiNumber+"/"+item.amount +"</td><td>"+item.price+"</td></tr>";
			$(rows).appendTo("#ManageTicketTable");
		});
    }
    /*new a ticket function end here*/
    
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
	
	function checkoutPage(){
    	var formData=[];
    	$('#CartTicket tr').each(function(){ 
			var rowcells = $(this).find('td');
			var item = {};
			alert($(rowcells[0]).html());
			item["from"] = $(rowcells[0]).html();
			item["to"] = $(rowcells[1]).html();
			item["time"] =$(rowcells[3]).html();
			formData.push(item);
			
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
		    $( "#AT_To" ).autocomplete({
		    	source: availableTags
		    });
		    $( "#AT_From" ).autocomplete({
			    source: availableTags
			});
	});