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
    function check(){
    	var i=0;
    	var formData=[];
    	$('#grid input[type=checkbox]:checked').each(function(){ 		
			var row = $(this).parent().parent();
			var rowcells = row.find('td');
			var rowData={ID: $(rowcells[0]).html(),Amount: $(rowcells[1]).find('input').val()};;
			formData[i]=rowData;
			i++; 		  
		});
    	$.ajax({
			  url:"Demand1/restful/#",
			  type:"post",
			  data: formData,
			  dataType:"json"
		});
    }
	function ticket(){
        url_redirect({url: "/Demand1/content/ticket.html",
           			  method: "post",
             		  data: {"From":$("#From").val(), "To":$("#To").val(), "Time":$("#Leave").val()+"/"+$("#At").val()}
		});
/*        
 		var formData = {From : $("#From").val(),To:$("#To").val(),Time:$("#Leave").val()+"/"+$("#At").val()};
		$.ajax({
			url: "/Demand1/restful/PeroidTickets.html",
			type: "post",
			data: formData,
			dataType: "json",
			success:showTicket
		});
*/
	}
/*
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
*/
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