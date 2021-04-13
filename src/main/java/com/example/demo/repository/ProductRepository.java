package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.ProductEntity;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);

    Optional<ProductEntity> findByProductId(String productId);

    long deleteByProductId(String productId);

}