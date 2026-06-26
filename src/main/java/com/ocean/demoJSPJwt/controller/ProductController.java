package com.ocean.demoJSPJwt.controller;

import com.ocean.demoJSPJwt.model.AppUser;
import com.ocean.demoJSPJwt.model.Product;
import com.ocean.demoJSPJwt.service.ProductService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


// 19/05/2026 : @RestController

@Controller  				//# Changed from @RestController
@RequestMapping("/product")		//# @RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/list")
    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
    public String getAllProducts(Model model, HttpSession session) {
    	
    	System.out.println("Controller: Fetching all products");
        List<Product> products = service.getAllProducts();
        System.out.println(products);
        
        model.addAttribute("products", products);
        return "products";  	//# Returns products.jsp
        
        
    }
    
    //# For use the jqGrid to show the data
    @GetMapping("/products-grid")
    public String productsGrid() {
        return "products-grid";  // jqGrid version
    }
    
    
    //# 19/05/2026 : Use the AG Grid to show the data
    @GetMapping("/products-agrid")
    public String productsAGrid() {
        return "products-agrid";
    }
    
    
    @GetMapping("/api/products")
    @ResponseBody
    public Map<String, Object> getProductsForGrid() {
        List<Product> products = service.getAllProducts();
        
        Map<String, Object> response = new HashMap<>();
        response.put("page", 1);
        response.put("total", 1);
        response.put("records", products.size());
        response.put("rows", products);
        
        return response;
    }

}
