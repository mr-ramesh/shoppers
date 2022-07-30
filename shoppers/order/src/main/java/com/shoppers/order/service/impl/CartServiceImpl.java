package com.shoppers.order.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppers.order.client.ProductClient;
import com.shoppers.order.model.Cart;
import com.shoppers.order.model.Item;
import com.shoppers.order.model.Product;
import com.shoppers.order.repository.CartRepository;
import com.shoppers.order.service.CartService;
import com.shoppers.order.utils.CartUtilities;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductClient productClient;

	@Autowired
	private CartRepository cartRepository;

	@Override
	public Cart addItemToCart(Optional<String> cartId, Long productId, Integer quantity) throws Exception {
		if (cartId.isEmpty() || !checkIfItemIsExist(cartId.get(), productId)) {
			int avilableCount = productClient.getProductQty(productId);
			if (avilableCount < quantity) {
				throw new Exception("Not enough stock for this product. Only " + avilableCount + " stocks available");
			}
			Product product = productClient.getProductById(productId);
			Item item = new Item();
			item.setQuantity(quantity);
			item.setProduct(product);
			item.setSubTotal(CartUtilities.getSubTotalForItem(product, quantity));
			return cartRepository.addItemToCart(cartId, item);
		} else {
			return changeItemQuantity(cartId.get(), productId, quantity);
		}
	}

	@Override
	public List<Item> getCartItems(String cartId) {
		Cart cart = cartRepository.getCart(cartId);
		return null != cart ? cart.getItems() : null;
	}

	@Override
	public boolean checkIfItemIsExist(String cartId, Long productId) {
		Cart cart = cartRepository.getCart(cartId);
		for (Item item : cart.getItems()) {
			if ((item.getProduct().getId()).equals(productId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Cart changeItemQuantity(String cartId, Long productId, Integer quantity) {
		Cart cart = cartRepository.getCart(cartId);
		for (Item item : cart.getItems()) {
			if ((item.getProduct().getId()).equals(productId)) {
				cartRepository.deleteItemFromCart(cartId, item);
				item.setQuantity(quantity);
				item.setSubTotal(CartUtilities.getSubTotalForItem(item.getProduct(), quantity));
				return cartRepository.addItemToCart(Optional.of(cartId), item);
			}
		}
		return new Cart(cartId, cart.getItems());
	}

	@Override
	public Cart deleteItemFromCart(String cartId, Long productId) {
		Cart cart = cartRepository.getCart(cartId);
		for (Item item : cart.getItems()) {
			if ((item.getProduct().getId()).equals(productId)) {
				return cartRepository.deleteItemFromCart(cartId, item);
			}
		}
		return cart;
	}

	@Override
	public void deleteCart(String cartId) {
		cartRepository.deleteCart(cartId);
	}
}
