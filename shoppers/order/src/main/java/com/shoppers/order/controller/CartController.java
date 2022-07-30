package com.shoppers.order.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppers.order.model.Cart;
import com.shoppers.order.model.Item;
import com.shoppers.order.service.CartService;

@RestController
@CrossOrigin()
@RequestMapping("/order/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@GetMapping(value = "{cartId}")
	public ResponseEntity<?> getCart(@PathVariable(value = "cartId") String cartId) {
		List<Item> items = cartService.getCartItems(cartId);
		if (!items.isEmpty()) {
			return new ResponseEntity<>(new Cart(cartId, items), HttpStatus.OK);
		}
		return new ResponseEntity<>("Cart not found with " + cartId, HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = { "", "{cartId}" }, params = { "productId", "quantity" })
	public ResponseEntity<?> addItemToCart(@RequestParam("productId") Long productId,
			@RequestParam("quantity") Integer quantity, @PathVariable(value = "cartId") Optional<String> cartId,
			HttpServletRequest request) {
		try {
			return new ResponseEntity<>(cartService.addItemToCart(cartId, productId, quantity), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "{cartId}", params = "productId")
	public ResponseEntity<Cart> removeItemFromCart(@RequestParam("productId") Long productId,
			@PathVariable(value = "cartId") String cartId) {
		return new ResponseEntity<>(cartService.deleteItemFromCart(cartId, productId), HttpStatus.OK);
	}
}
