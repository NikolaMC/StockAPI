package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.request.ProductDetailsRequestModel;
import com.example.demo.model.response.ProductResponseModel;
import com.example.demo.service.ProductService;
import com.example.demo.shared.dto.ProductDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;

@RestController
@RequestMapping("products")

public class StockController {
	private final ProductService productService;

    public StockController(ProductService productService) {
        this.productService = productService;
    }
	
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseModel> getProducts() {

        List<ProductDto> productDtos = productService.getProducts();
        ArrayList<ProductResponseModel> responseList = new ArrayList<>();

        for (ProductDto productDto : productDtos){
            ProductResponseModel responseModel = new ProductResponseModel();
            BeanUtils.copyProperties(productDto,responseModel);
            responseList.add(responseModel);
        }
        
        return responseList;

    }
	
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseModel getProduct(@PathVariable String id) {
        ProductResponseModel responseModel = new ProductResponseModel();
        Optional<ProductDto> optionalProductDto = productService.getByProductId(id);
        
        if (optionalProductDto.isPresent()) {
            ProductDto productDto = optionalProductDto.get();
            BeanUtils.copyProperties(productDto, responseModel);
            return responseModel;
        }
        
        throw new NotFoundException("No product found with the id: " + id);
    }	  
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ProductResponseModel> createProduct(@RequestBody ProductDetailsRequestModel productDetails) {
		ProductDto productDtoIn = new ProductDto();
		BeanUtils.copyProperties(productDetails, productDtoIn);
		
		if (productDtoIn.getCost() < 0) {
			throw new BadRequestException("Cost can not be a negative number");
		} else {
			ProductDto productDtoOut = productService.createProduct(productDtoIn);
			
			ProductResponseModel response = new ProductResponseModel();
			BeanUtils.copyProperties(productDtoOut, response);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductResponseModel updateProduct(@PathVariable String id, @RequestBody ProductDetailsRequestModel requestData) {
        ProductDto productDtoIn = new ProductDto();
        BeanUtils.copyProperties(requestData, productDtoIn);
        
        if (productDtoIn.getCost() < 0) {
			throw new BadRequestException("Cost can not be a negative number");
		} else {
	        Optional<ProductDto> productDtoOut = productService.updateProduct(id, productDtoIn);
	        
	        if (productDtoOut.isEmpty()) {
	            throw new NotFoundException("No product found with the id: " + id);
	        }
	        
	        ProductDto productDto = productDtoOut.get();
	        
	        ProductResponseModel responseModel = new ProductResponseModel();
	        BeanUtils.copyProperties(productDto, responseModel);
	        return responseModel;
		}
    }
	
	@DeleteMapping("/{id}")
	String deleteProduct(@PathVariable String id) {
	
	    boolean deleted = productService.deleteProduct(id);
	    if (deleted) {
	        return "Deleted successfully";
	    }
	    
	    throw new NotFoundException("No product found with the id: " + id);
	
	}
}

