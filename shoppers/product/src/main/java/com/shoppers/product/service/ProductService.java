package com.shoppers.product.service;

import java.util.List;
import java.util.Optional;

import com.shoppers.product.model.Product;

public interface ProductService {

	public Product addProduct(Product product);

	public Product updateProduct(long productId, int orderedCount);

	public List<Product> getAllProducts();

	public Optional<Product> getProductById(long id);

	public Product getProductByName(String productName);

	public List<Product> getProductByCategory(String category);

	public void deleteProduct(long id);
}
