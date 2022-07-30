package com.shoppers.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppers.order.model.Product;

@FeignClient("product")
public interface ProductClient {

	@GetMapping(value = "/product")
	public Product getProductById(@RequestParam(value = "id") Long productId);

	@PutMapping(value = "/product", params = { "productId", "orderedCount" })
	public Product updateProduct(@RequestParam("productId") Long productId,
			@RequestParam("orderedCount") int orderedCount);

	@GetMapping(value = "/product/quantity", params = { "productId" })
	public int getProductQty(@RequestParam("productId") Long productId);
}
