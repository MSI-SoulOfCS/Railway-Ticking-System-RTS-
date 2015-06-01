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
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	
	<title>Payment</title>
	</head>
	<body>
		<div class="headline">
			<h1>Payment Page</h1>
		</div>
	
		<div id="wrap">
			<div id="accordian">
				<div class="step" id="step1">
					<div class="number">
						<span>1</span>
					</div>
					<div class="title">
						<h1>Email Address</h1>
					</div>
				</div>
				<div>
					<div class="content" id="email">
						<form class="go-right">
							<div>
		        				<input type="email" name="email" value="" id="email-address" placeholder="Email Address" data-trigger="change" data-validation-minlength="1" data-type="email" data-required="true" data-error-message="Enter a valid email address."/>
		   
		       				</div>
							<button class="login">Login</button>
							<button class="create">Create Account</button>
						</form>
						<a class="continue" href="#step2">Continue</a>
					</div>
				</div>
				<div class="step" id="step2">
					<div class="number">
						<span>2</span>
					</div>
					<div class="title">
						<h1>Billing Information</h1>
					</div>
				</div>
				<div class="content" id="address">
					<form class="go-right">
						<div>
							<input type="text" name="first_name" value="" id="first_name" placeholder="John" data-trigger="change" data-validation-minlength="1" data-type="name" data-required="true" data-error-message="Enter Your First Name"/>
				        </div>
						<div>
							<input type="text" name="last_name" value="" id="last_name" placeholder="Smith" data-trigger="change" data-validation-minlength="1" data-type="name" data-required="true" data-error-message="Enter Your Last Name"/>
						</div>
						<div>
							<input type="text" name="telephone" value="" id="telephone" placeholder="(555)-867-5309" data-trigger="change" data-validation-minlength="1" data-type="number" data-required="true" data-error-message="Enter Your Telephone Number"/>
						</div>
						<div>
							<input type="text" name="company" value="" id="company" placeholder="Company" data-trigger="change" data-validation-minlength="1" data-type="name" data-required="false"/>
						</div>
						<div>
							<input type="text" name="address" value="" id="address" placeholder="123 Main Street" data-trigger="change" data-validation-minlength="1" data-type="text" data-required="true" data-error-message="Enter Your Billing Address"/>
						</div>
						<div>
							<input type="text" name="city" value="" id="city" placeholder="Everytown" data-trigger="change" data-validation-minlength="1" data-type="text" data-required="true" data-error-message="Enter Your Billing City"/>
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
							<input type="text" name="zip" value="" id="zip" placeholder="12345" data-trigger="change" data-validation-minlength="1" data-type="text" data-required="true" data-error-message="Enter Your Billing Zip Code"/>
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
						<span>3</span>
					</div>
					<div class="title">
						<h1>Payment Information</h1>
					</div>
				</div>
				<div class="content" id="payment">
					<div class="left credit_card">
						<form class="go-right">
							<div>
								<input type="text" name="card_number" value="" id="card_number" placeholder="xxxx-xxxx-xxxx-xxxx" data-trigger="change" data-validation-minlength="1" data-type="name" data-required="true" data-error-message="Enter Your Credit Card Number"/>
							</div>
							<div>
								<div class="expiry">	
									<div class="month_select">
										<select name="exp_month"  id="exp_month"  data-trigger="change" data-type="name" data-required="true" data-error-message="Enter Your Credit Card Expiration Date">
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
										<input type="text" name="ccv" value="" id="ccv" placeholder="123" data-trigger="change" data-validation-minlength="3" data-type="name" data-required="true" data-error-message="Enter Your Card Security Code"/>
									</div>
								</div>
							</div>		
						</form>
					</div>
					<div class="right">
						<div class="accepted">
							<span><img src="<c:url value="/img/Z5HVIOt.png" />"></span>
							<span><img src="<c:url value="/img/Le0Vvgx.png"/>"></span>
							<span><img src="<c:url value="/img/D2eQTim.png"/>"></span>
							<span><img src="<c:url value="/img/Pu4e7AT.png"/>"></span>
							<span><img src="<c:url value="/img/ewMjaHv.png"/>"></span>
							<span><img src="<c:url value="/img/3LmmFFV.png"/>"></span>
						</div>
						<div class="secured">
							<img class="lock" src="<c:url value="/img/lock.png"/>">
							<p class="security info">What, well you mean like a date? Doc? Am I to understand you're still hanging around with Doctor Emmett Brown, McFly? Tardy slip for you, Miss Parker. And one for you McFly I believe that makes four in a row. Now let me give you a nickle's worth of advice, young man. This so called Doctor Brown is dangerous, he's a real nuttcase.</p>
						</div>
					</div>
					<a class="continue" href="#step4">Continue</a>
				</div>
				<div class="step" id="step4">
					<div class="number">
						<span>4</span>
					</div>
					<div class="title">
						<h1>Finalize Order</h1>
					</div>
				</div>
				<div class="content" id="final_products">
					<div class="left" id="ordered">
						<div class="products">
							<div class="product_image">
							<!-- TODO -->
							</div>
							<div class="product_details">
								<span class="product_name">Cherry Bikini</span>
								<span class="quantity">1</span>
								<span class="price">$45.00</span>
							</div>
						</div>
						<div class="totals">
							<span class="subtitle">Subtotal <span id="sub_price">$45.00</span></span>
							<span class="subtitle">Tax <span id="sub_tax">$2.00</span></span>
							<span class="subtitle">Shipping <span id="sub_ship">$4.00</span></span>
						</div>
						<div class="final">
							<span class="title">Total <span id="calculated_total">$51.00</span></span>
						</div>
					</div>
					<div class="right" id="reviewed">
						<div class="billing">
							<span class="title">Billing:</span>
							<div class="address_reviewed">
								<span class="name">John Smith</span>
								<span class="address">123 Main Street</span>
								<span class="location">Everytown, USA, 12345</span>
								<span class="phone">(123)867-5309</span>
							</div>
						</div>
						<div class="payment">
							<span class="title">Payment:</span>
							<div class="payment_reviewed">
								<span class="method">Visa</span>
								<span class="number_hidden">xxxx-xxxx-xxxx-1111</span>
							</div>
						</div>
						<div id="complete">
							<a class="big_button" id="complete" href="#">Complete Order</a>
							<span class="sub">By selecting this button you agree to the purchase and subsequent payment for this order.</span> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>