package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.entity.ProductEntity;
import com.example.demo.service.ProductService;
import com.example.demo.shared.dto.ProductDto;
import com.example.demo.util.Util;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
    private Util util;

    public ProductServiceImpl(ProductRepository productRepository, Util util) {
        this.productRepository = productRepository;
        this.util = util;
    }
	
    @Override
    public List<ProductDto> getProducts() {
       Iterable<ProductEntity> productEntities = productRepository.findAll();
        ArrayList<ProductDto> productDtos = new ArrayList<>();
       for (ProductEntity productEntity : productEntities){
           ProductDto productDto = new ProductDto();
           BeanUtils.copyProperties(productEntity, productDto);
           productDtos.add(productDto);
       }

        return productDtos;
    }
	
    @Override
    public Optional<ProductDto> getByProductId(String id) {
        Optional<ProductEntity> productIdEntity = productRepository.findProductById(id);

        return productIdEntity.map(productEntity -> {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(productEntity, productDto);

            return productDto;
        });
    }
	
	@Override
	public ProductDto createProduct(ProductDto productDetails) {
		Optional<ProductEntity> checkNameEntity = productRepository.findProductByName(productDetails.getName());
		
        if (checkNameEntity.isPresent()) {
            throw new RuntimeException("That product already exists");
        }
        
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productDetails, productEntity);

        String productId = util.generateHashedProductId(productDetails.getName());
        productEntity.setProductId(productId.substring(3));

        ProductEntity productEntityOut = productRepository.save(productEntity);
        ProductDto productDtoOut = new ProductDto();

        BeanUtils.copyProperties(productEntityOut, productDtoOut);

        return productDtoOut;
	}
	
	public Optional<ProductDto> updateProduct(String id, ProductDto productDto) {

        Optional<ProductEntity> productIdEntity = productRepository.findProductById(id);
        if (productIdEntity.isEmpty()) {
            return Optional.empty();
        }
        
        return productIdEntity.map(productEntity -> {
            ProductDto response = new ProductDto();
            
            productEntity.setProductId(productDto.getProductId() != null ? util.generateHashedProductId(productDto.getName()).substring(3) : productEntity.getProductId());
            productEntity.setName(productDto.getName() != null ? productDto.getName() : productEntity.getName());
            productEntity.setCategory(productDto.getCategory() != null ? productDto.getCategory() : productEntity.getCategory());
            productEntity.setCost(productDto.getCost() != null ? productDto.getCost() : productEntity.getCost());
            
            ProductEntity updatedProductEntity = productRepository.save(productEntity);
            BeanUtils.copyProperties(updatedProductEntity, response);
            return response;
        });

    }
	
	@Transactional
    public boolean deleteProduct(String id) {
      Long removedProductCount = productRepository.deleteProductById(id);

      return removedProductCount >0;
    }
	
}
