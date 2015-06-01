<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8" />
<link href="<c:url value="/css/shopping.css" />" rel="stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="js/shopping.js"/>"></script>
<title>Shopping Cart</title>
</head>

<body>
	<ul class='item-list'>
		<li class='item'>
			<div class='item__information'>
				<div class='item__image'>
	       			 <!-- <img src='http://upload.wikimedia.org/wikipedia/en/thumb/6/67/30_STM_-_Love_Lust_Faith_%2B_Dreams.jpeg/220px-30_STM_-_Love_Lust_Faith_%2B_Dreams.jpeg'> -->
				</div>
				<div class='item__body'>
					<h2 class='item__title'>Love, Lust, Faith and Dreams</h2>
			        <p class='item__description'>Love, Lust, Faith and Dreams is the fourth studio album from American rock band Thirty Seconds to Mars.</p>
				</div>
      			<div class='item__price js-item-price' data-price='11.99'>$1199</div>
			</div>
			<div class='item__interactions'>
				<p class='item__quantity'>
			        <a class='js-item-increase' title='Add another copy'>+</a>
			        <a class='js-item-decrease decrease--disabled' title='Remove a copy'>-</a>
			        <span data-quantity='1'>
	          			<b>1</b>
	         			copy
	       			</span>
	        			at $11.99
				</p>
				<a class='item__remove js-item-remove' title='Remove this item'>&times;</a>
			</div>
		</li>
		<li class='item'>
			<div class='item__information'>
				<div class='item__image'>
					<img src='http://upload.wikimedia.org/wikipedia/en/a/a7/Random_Access_Memories.jpg'>
				</div>
				<div class='item__body'>
					<h2 class='item__title'>Random Access Memories</h2>
					<p class='item__description'>Random Access Memories is the fourth studio album by French electronic music duo Daft Punk.</p>
				</div>
				<div class='item__price js-item-price' data-price='11.88'>$11.88</div>
			</div>
			<div class='item__interactions'>
				<p class='item__quantity'>
					<a class='js-item-increase' title='Add another copy'>+</a>
			        <a class='js-item-decrease decrease--disabled' title='Remove a copy'>-</a>
			        <span data-quantity='1'>
						<b>1</b>
						copy
					</span>
						at $11.88
				</p>
				<a class='item__remove js-item-remove' title='Remove this item'>&times;</a>
			</div>
		</li>
		<li class='item'>
			<div class='item__information'>
				<div class='item__image'>
					<img src='http://upload.wikimedia.org/wikipedia/en/a/a7/Random_Access_Memories.jpg'>
				</div>
				<div class='item__body'>
					<h2 class='item__title'>Random Access Memories</h2>
					<p class='item__description'>Random Access Memories is the fourth studio album by French electronic music duo Daft Punk.</p>
				</div>
				<div class='item__price js-item-price' data-price='11.88'>$11.88</div>
			</div>
			<div class='item__interactions'>
				<p class='item__quantity'>
					<a class='js-item-increase' title='Add another copy'>+</a>
					<a class='js-item-decrease decrease--disabled' title='Remove a copy'>-</a>
					<span data-quantity='1'>
						<b>1</b>
						copy
					</span>
					at $11.88
				</p>
				<a class='item__remove js-item-remove' title='Remove this item'>&times;</a>
			</div>
		</li>
		<li class='item'>
			<div class='item__information'>
				<div class='item__image'>
					<img src='http://upload.wikimedia.org/wikipedia/en/e/e4/The_Heist_Macklemore.jpeg'>
				</div>
				<div class='item__body'>
					<h2 class='item__title'>The Heist</h2>
					<p class='item__description'>The Heist is the debut studio album by American rapper Macklemore and producer Ryan Lewis.</p>
				</div>
				<div class='item__price js-item-price' data-price='8.99'>$8.99</div>
			</div>
			<div class='item__interactions'>
				<p class='item__quantity'>
					<a class='js-item-increase' title='Add another copy'>+</a>
					<a class='js-item-decrease decrease--disabled' title='Remove a copy'>-</a>
					<span data-quantity='1'>
						<b>1</b>
						copy
					</span>
					at $8.99
				</p>
				<a class='item__remove js-item-remove' title='Remove this item'>&times;</a>
			</div>
		</li>
	</ul>
	<div class='summary js-summary'>
		<ul class='steps'>
			<li>
				<b>Subtotal:</b>
				<span class='sum js-subtotal'>$32.86</span>
			</li>
			<li>
				<b>Taxes (5%):</b>
				<span class='sum js-taxes'>$1.64</span>
			</li>
			<li>
				<b>Shipping:</b>
				<span class='sum js-shipping'>$5.00</span>
			</li>
		</ul>
		<ul class='checkout'>
			<li>
				<b>Total:</b>
				<span class='sum js-total'>$39.50</span>
			</li>
			<li>
				<a class='button js-checkout-button'>Checkout</a>
			</li>
		</ul>
	</div>	
</body>
</html>