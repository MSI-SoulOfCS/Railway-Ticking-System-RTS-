<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang='en'>
	<head>
	<meta charset="UTF-8" /> 
	<link href="<c:url value="/css/style.css" />" rel="stylesheet">
	<link href="<c:url value="/css/font.css" />" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,600' rel='stylesheet' type='text/css'>
    <script src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
   	<script src="<c:url value="/js/jquery-ui.js"/>"></script>
  	<script src="<c:url value="/js/msi-jquery.js"/>"></script>
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="<c:url value="/js/jquery.creditCardValidator.js"/>"></script>

	<title>Payment</title>
	
	<script>
		$(document).ready(function() {
			loadCheckOutUserCart();
		    $("#complete").click(function(){
		    	checkoutPage();
		   	});
		});
		
		$(function() {
	        $('#card_number').validateCreditCard(function(result) {
	        	if(result.card_type == null){
	        		$("#Visa").fadeTo("fast",1);
	        		$("#AmericanExpress").fadeTo("fast",1);
	        		$("#Master").fadeTo("fast",1);
	        		$("#Discover").fadeTo("fast",1);
	        		$("#Maestro").fadeTo("fast",1);
	        	}
	        	else{
	        		if(result.card_type.name=="mastercard"){
	            		$("#Master").fadeTo("fast",1);
	            		$("#AmericanExpress").fadeTo("fast",0.3);
	            		$("#Discover").fadeTo("fast",0.3);
	            		$("#Visa").fadeTo("fast",0.3);
	            		$("#Maestro").fadeTo("fast",0.3);
	            	}
	            	else if(result.card_type.name=="discover"){
	            		$("#Discover").fadeTo("fast",1);
	            		$("#AmericanExpress").fadeTo("fast",0.3);
	            		$("#Master").fadeTo("fast",0.3);
	            		$("#Visa").fadeTo("fast",0.3);
	            		$("#Maestro").fadeTo("fast",0.3);
	            	}
	            	else if(result.card_type.name=="amex"){
	            		$("#AmericaExpress").fadeTo("fast",1);
	            		$("#Discover").fadeTo("fast",0.3);
	            		$("#Master").fadeTo("fast",0.3);
	            		$("#Visa").fadeTo("fast",0.3);
	            		$("#Maestro").fadeTo("fast",0.3);
	            	}
	            	else if(result.card_type.name=="maestro"){
	            		$("#Maestro").fadeTo("fast",1);
	            		$("#AmericanExpress").fadeTo("fast",0.3);
	            		$("#Master").fadeTo("fast",0.3);
	            		$("#Visa").fadeTo("fast",0.3);
	            		$("#Discover").fadeTo("fast",0.3);
	            	}
	            	else if(result.card_type.name=="visa"){
	            		$("#Visa").fadeTo("fast",1);
	            		$("#AmericanExpress").fadeTo("fast",0.3);
	            		$("#Master").fadeTo("fast",0.3);
	            		$("#Discover").fadeTo("fast",0.3);
	            		$("#Maestro").fadeTo("fast",0.3);
	            	}
	        	}
	        	var valid=result.valid;
	        	var length=result.length_valid;
	        	var luhn=result.luhn_valid;
	        	$(".valid").hide();
	        	if(valid&&luhn&&length){
	        		$(".valid").hide();
	        	}
	        	else {
	        		$(".valid").show();
	        		$(".valid").html('Please enter a valid card number!');
	        	}
	        	
	/*	             $(".log").html('Card type: ' + (result.card_type == null ? '-' : result.card_type.name)
	                     + '<br>Valid: ' + result.valid
	                     + '<br>Length valid: ' + result.length_valid
	                     + '<br>Luhn valid: ' + result.luhn_valid);	*/
	             /* if(result.card_type == null)
	            	 alert("test");
	             alert("hello"+result.card_type.name); */
	        });     
	    });
		
	</script>
	</head>
	<body>
		<div class="headline">
			<h1>Payment Page</h1>
		</div>
	
		 <div id="wrap">
			<div id="accordian">
				<div class="step" id="step2">
					<div class="number">
						<span>1</span>
					</div>
					<div class="title">
						<h1>Billing Information</h1>
					</div>
				</div>
				<div class="content" id="address">
					<form class="go-right">
						<div>
							<input type="text" name="first_name" value="" id="first_name" placeholder="first name" data-trigger="change" data-validation-minlength="1" data-type="name" data-required="true" data-error-message="Enter Your First Name"/>
				        </div>
						<div>
							<input type="text" name="last_name" value="" id="last_name" placeholder="last name" data-trigger="change" data-validation-minlength="1" data-type="name" data-required="true" data-error-message="Enter Your Last Name"/>
						</div>
						<div>
							<input type="text" name="telephone" value="" id="telephone" placeholder="phone number" data-trigger="change" data-validation-minlength="1" data-type="number" data-required="true" data-error-message="Enter Your Telephone Number"/>
						</div>
						<div>
							<input type="text" name="company" value="" id="company" placeholder="company" data-trigger="change" data-validation-minlength="1" data-type="name" data-required="false"/>
						</div>
						<div>
							<input type="text" name="address" value="" id="address" placeholder="address" data-trigger="change" data-validation-minlength="1" data-type="text" data-required="true" data-error-message="Enter Your Billing Address"/>
						</div>
						<div>
							<input type="text" name="city" value="" id="city" placeholder="city" data-trigger="change" data-validation-minlength="1" data-type="text" data-required="true" data-error-message="Enter Your Billing City"/>
						</div>
						<div>
							<div class="state_options">
	        					<div class="select">
			                 		<select id="state">
			                  	 		<option value = "1">Alabama</option>
			                   			<option value = "2">Alaska</option>
			                   			<option value = "3">Arkansas</option>
			                   			<option value = "4">Etc.</option>
			                 		</select>
			                 	</div>
			          		</div>
						</div>
						<div>
							<input type="text" name="zip" value="" id="zip" placeholder="zip code" data-trigger="change" data-validation-minlength="1" data-type="text" data-required="true" data-error-message="Enter Your Billing Zip Code"/>
						</div>
						<div>
							<div class="country_options">
		        				<div class="select">
		                 			<select id="country">
		                  	 			<option value = "1">United States</option>
		                   				<option value = "2">United Kingdom</option>
			                   			<option value = "3">Uganda</option>
			                   			<option value = "4">Etc.</option>
			                 		</select>
		                 		</div>
	          				</div>
						</div>
					</form>
					<a class="continue" href="#step3">Continue</a>
				</div>
				<div class="step" id="step3">
					<div class="number">
						<span>2</span>
					</div>
					<div class="title">
						<h1>Payment Information</h1>
					</div>
				</div>
				<div class="content" id="payment">
					<div class="left credit_card">
						<form class="go-right">
							<div>
								<input type="text" name="card_number" placeholder="card number" value="" id="card_number" >
								<p id="valid" class="valid" style="color:red"></p>
								<p id="length" class="length" style="color:red"></p>
							</div>
							<div>
								<div class="expiry">	
									<div class="month_select">
										<select name="exp_month" id="exp_month"  data-trigger="change" data-type="name" data-required="true" data-error-message="Enter Your Credit Card Expiration Date">
											<option value = "1">01 (Jan)</option>
				                   			<option value = "2">02 (Feb)</option>
				                   			<option value = "3">03 (Mar)</option>
				                   			<option value = "4">04 (Apr)</option>
				                   			<option value = "5">05 (May)</option>
				                   			<option value = "6">06 (Jun)</option>
				                   			<option value = "7">07 (Jul)</option>
				                   			<option value = "8">08 (Aug)</option>
				                   			<option value = "9">09 (Sep)</option>
				                   			<option value = "10">10 (Oct)</option>
				                   			<option value = "11">11 (Nov)</option>
				                   			<option value = "12">12 (Dec)</option>
										</select>
									</div>
									<span class="divider">-</span>
									<div class="year_select">
										<select name="exp_year"  id="exp_year"  data-trigger="change" data-type="name" data-required="true" data-error-message="Enter Your Credit Card Expiration Date">
											<option value = "1">14 (Jan)</option>
				                   			<option value = "2">15 (Feb)</option>
				                   			<option value = "3">16 (Mar)</option>
				                   			<option value = "4">17 (Apr)</option>
				                   			<option value = "5">18 (May)</option>
				                   			<option value = "6">19 (Jun)</option>
				                   			<option value = "7">20 (Jul)</option>
				                   			<option value = "8">22 (Aug)</option>
				                   			<option value = "9">23 (Sep)</option>
				                   			<option value = "10">24 (Oct)</option>
				                   			<option value = "11">25 (Nov)</option>
				                   			<option value = "12">26 (Dec)</option>
				                        </select>
									</div>
								</div>
								
								<div class="sec_num">
									<div>
										<input type="text" name="ccv" value="" id="ccv" placeholder="security code" data-trigger="change" data-validation-minlength="3" data-type="name" data-required="true" data-error-message="Enter Your Card Security Code"/>
									</div>
								</div>
							</div>		
						</form>
					</div>
					<div class="right">
						<div class="accepted">
							<span id="AmericanExpress"><img src="<c:url value="/img/Z5HVIOt.png" />"></span>
							<%-- <span><img src="<c:url value="/img/Le0Vvgx.png"/>"></span> --%>
							<span id="Discover"><img src="<c:url value="/img/D2eQTim.png"/>"></span>
							<span id="Maestro"><img src="<c:url value="/img/Pu4e7AT.png"/>"></span>
							<span id="Master"><img src="<c:url value="/img/ewMjaHv.png"/>"></span>
							<span id="Visa"><img src="<c:url value="/img/3LmmFFV.png"/>"></span>
						</div>
						<div class="secured">
							<img class="lock" src="<c:url value="/img/lock.png"/>">
							<p class="security info">What, well you mean like a date? Doc? Am I to understand you're still hanging around with Doctor Emmett Brown, McFly? Tardy slip for you, Miss Parker. And one for you McFly I believe that makes four in a row. Now let me give you a nickle's worth of advice, young man. This so called Doctor Brown is dangerous, he's a real nuttcase.</p>
						</div>
						
					</div>
					<a class="continue" href="#">Proceed to checkout</a>
				</div>
				<div class="step" id="step4">
					<div class="number">
						<span>3</span>
					</div>
					<div class="title">
						<h1>Finalize Order</h1>
					</div>
				</div>
				<div class="content" id="final_products">
					<div class="left" id="ordered">
						<div class="products">
							<div class="product_details">
								<!-- <span class="product_name">Cherry Bikini</span>
								<span class="quantity">1</span>
								<span class="price">$45.00</span> -->
								<table>
	                				<tr>
	                  					<th>From</th>
	                  					<th>To</th>
	                  					<th>Date</th>
	                  					<th>Price</th>
	               					</tr>
	               					<tbody id="CartTicket">
				  					</tbody>
								</table>
							</div>
						</div>
						<div class="totals">
							
						</div>
						<div class="final">
							<div><b>Subtotal:</b><span id="subtotal" class="sum js-subtotal">$0</span></div>
							<div><b>Taxes (5%):</b><span id="taxes" class='sum js-taxes'>$0</span></div>
							<div><b>Total:</b><span id="total" class='sum js-total'>$0</span></div>
						</div>
					</div>
					 <div class="right" id="reviewed">
						<div id="complete">
							<a class="big_button" id="complete" href="#complete">Complete Order</a>
							<span class="sub">By selecting this button you agree to the purchase and subsequent payment for this order.</span> 
						</div>
					</div> 
				</div>
			</div>
		</div>
	</body>
</html>