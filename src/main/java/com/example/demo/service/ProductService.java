package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.shared.dto.ProductDto;

public interface ProductService {
	List<ProductDto> getProducts();
	Optional<ProductDto> getByProductId(String id);
	ProductDto createProduct(ProductDto productDetails);
	Optional<ProductDto> updateProduct(String id, ProductDto productDtoIn);
	boolean deleteProduct(String id);
}
