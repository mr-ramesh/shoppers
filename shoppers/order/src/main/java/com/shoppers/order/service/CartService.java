package com.shoppers.order.service;

import java.util.List;
import java.util.Optional;

import com.shoppers.order.model.Cart;
import com.shoppers.order.model.Item;

public interface CartService {

	public Cart addItemToCart(Optional<String> cartId, Long productId, Integer quantity) throws Exception;

	public List<Item> getCartItems(String cartId);

	public Cart changeItemQuantity(String cartId, Long productId, Integer quantity);

	public Cart deleteItemFromCart(String cartId, Long productId);

	public boolean checkIfItemIsExist(String cartId, Long productId);

	public void deleteCart(String cartId);
}
