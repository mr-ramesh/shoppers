package com.shoppers.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppers.product.model.Product;
import com.shoppers.product.service.ProductService;

@RestController
@CrossOrigin()
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping
	public ResponseEntity<?> getAllProducts() {
		List<Product> allProducts = productService.getAllProducts();
		if (!allProducts.isEmpty()) {
			return ResponseEntity.ok(allProducts);
		}
		return new ResponseEntity<>("No Records found", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/quantity", params = { "productId" })
	public ResponseEntity<Integer> getProductQty(@RequestParam("productId") Long productId) {
		Optional<Product> product = productService.getProductById(productId);
		return new ResponseEntity<>(product.isPresent() ? product.get().getAvailability() : 0, HttpStatus.OK);
	}

	@GetMapping(params = "id")
	public ResponseEntity<?> getProductById(@RequestParam("id") long id) {
		Optional<Product> product = productService.getProductById(id);
		if (product.isPresent()) {
			return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>("No Records found with id: " + id, HttpStatus.NOT_FOUND);
	}

	@GetMapping(params = "name")
	public ResponseEntity<?> getProductByName(@RequestParam("name") String name) {
		Product product = productService.getProductByName(name);
		if (null != product) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		return new ResponseEntity<>("No Records found wit name: " + name, HttpStatus.NOT_FOUND);
	}

	@GetMapping(params = "category")
	public ResponseEntity<?> getProductByCategory(@RequestParam("category") String category) {
		List<Product> allProducts = productService.getProductByCategory(category);
		if (!allProducts.isEmpty()) {
			return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
		}
		return new ResponseEntity<>("No Records found wit category: " + category, HttpStatus.NOT_FOUND);
	}

	@PutMapping(params = { "productId", "orderedCount" })
	public ResponseEntity<?> updateProduct(@RequestParam("productId") Long productId,
			@RequestParam("orderedCount") int orderedCount) {
		return new ResponseEntity<Product>(productService.updateProduct(productId, orderedCount), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody Product product) {
		return new ResponseEntity<Product>(productService.addProduct(product), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteProductById(@RequestParam("category") long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
	}
}
