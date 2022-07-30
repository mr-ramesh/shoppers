package com.shoppers.order.repository;

import java.util.Optional;

import com.shoppers.order.model.Cart;
import com.shoppers.order.model.Item;

public interface CartRepository {

	public Cart addItemToCart(Optional<String> cartId, Item item);

	public Cart deleteItemFromCart(String cartId, Item item);

	public Cart getCart(String cartId);

	public void deleteCart(String cartId);
}
