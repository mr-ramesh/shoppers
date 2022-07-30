package com.shoppers.order.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.shoppers.order.model.Cart;
import com.shoppers.order.model.Item;

@Repository
public class CartRepositoryImpl implements CartRepository {

	private Map<String, List<Item>> cartCache = new HashMap<>();

	public Cart addItemToCart(Optional<String> cartId, Item item) {
		String id = cartId.isPresent() ? cartId.get() : UUID.randomUUID().toString();
		List<Item> cartItems = null != cartCache.get(id) ? cartCache.get(id) : new ArrayList<>();
		cartItems.add(item);
		cartCache.put(id, cartItems);
		return new Cart(id, cartItems);
	}

	public Cart deleteItemFromCart(String cartId, Item item) {
		List<Item> cartItems = cartCache.get(cartId);
		if (null != cartItems) {
			cartItems.remove(item);
			cartCache.put(cartId, cartItems);
		}
		return new Cart(cartId, cartItems);
	}

	public Cart getCart(String cartId) {
		List<Item> cartItems = null != cartCache.get(cartId) ? cartCache.get(cartId) : new ArrayList<>();
		return new Cart(cartId, cartItems);
	}

	public void deleteCart(String cartId) {
		cartCache.remove(cartId);
	}
}
