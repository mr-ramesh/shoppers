package com.shoppers.order.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppers.order.client.ProductClient;
import com.shoppers.order.model.Item;
import com.shoppers.order.model.Order;
import com.shoppers.order.model.User;
import com.shoppers.order.service.CartService;
import com.shoppers.order.service.OrderService;
import com.shoppers.order.utils.OrderUtilities;

@RestController
@CrossOrigin()
@RequestMapping("/order/add")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductClient productClient;

	@PostMapping(value = "/{username}", params = { "cartId" })
	public ResponseEntity<?> saveOrder(@PathVariable("username") String userName,
			@RequestParam(value = "cartId") String cartId, HttpServletRequest request) {

		List<Item> cartItems = cartService.getCartItems(cartId);
		User user = new User();
		user.setUserName(userName);

		if (cartItems != null && user != null) {
			Order order = this.createOrder(cartItems, user);
			try {
				orderService.saveOrder(order);
				cartService.deleteCart(cartId);
				for (Item item : cartItems) {
					int avilableCount = productClient.getProductQty(item.getProduct().getId());
					if (avilableCount < item.getQuantity()) {
						String productName = item.getProduct().getProductName();
						return new ResponseEntity<>("Sorry, Porduct " + productName + " is not available right now!",
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
					productClient.updateProduct(item.getProduct().getId(), item.getQuantity());
				}
				return new ResponseEntity<Order>(order, HttpStatus.CREATED);
			} catch (Exception ex) {
				ex.printStackTrace();
				return new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
	}

	private Order createOrder(List<Item> cart, User user) {
		Order order = new Order();
		order.setItems(cart);
		order.setUser(user);
		order.setTotal(OrderUtilities.countTotalPrice(cart));
		order.setOrderedDate(LocalDate.now());
		order.setStatus("Payment Pending");
		return order;
	}
}
