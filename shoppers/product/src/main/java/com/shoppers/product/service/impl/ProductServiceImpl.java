package com.shoppers.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppers.product.model.Product;
import com.shoppers.product.repository.ProductRepository;
import com.shoppers.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepo;

	public Product addProduct(Product product) {
		Product prod = productRepo.findByProductName(product.getProductName());
		if (null != prod)
			product.setId(prod.getId());
		return productRepo.save(product);
	}

	public Product updateProduct(long productId, int orderedCount) {
		Optional<Product> product = productRepo.findById(productId);
		if (product.isPresent()) {
			int newCount = product.get().getAvailability() - orderedCount;
			product.get().setAvailability(newCount);
			return productRepo.save(product.get());
		}
		return null;
	}

	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	public Optional<Product> getProductById(long id) {
		return productRepo.findById(id);
	}

	public Product getProductByName(String productName) {
		return productRepo.findByProductName(productName);
	}

	public List<Product> getProductByCategory(String category) {
		return productRepo.findAllByCategory(category);
	}

	public void deleteProduct(long id) {
		productRepo.deleteById(id);
	}
}
