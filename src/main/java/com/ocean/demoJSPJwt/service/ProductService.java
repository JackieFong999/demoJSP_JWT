package com.ocean.demoJSPJwt.service;

import com.ocean.demoJSPJwt.model.Product;
import com.ocean.demoJSPJwt.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProducts(){
        return repo.findAll();
    }
}
