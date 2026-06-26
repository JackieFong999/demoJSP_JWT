package com.ocean.demoJSPJwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  				
@RequestMapping("/")		
public class IndexController {
	
  @GetMapping({"", "/"})
  public String greeting(Model model) {  				// Now Model is recognized   
	System.out.println("=== CONTROLLER: Root Called ===");
  	model.addAttribute("message", "Index Page : (25/05/2026)");
      return "index";  									//# Returns index.jsp
  }
  
  
  //# 22/05/2026
  @GetMapping("/login-content")
  public String loginContent() {
      System.out.println("=== CONTROLLER: login-content called ===");
      return "login-content";
  }

  @GetMapping("/introduction-content")
  public String introductionContent() {
      System.out.println("=== CONTROLLER: introduction-content called ===");
      return "introduction-content";
  }
  
  @GetMapping("/fee-content")
  public String feeContent() {
      System.out.println("=== CONTROLLER: fee-content called ===");
      return "fee-content";
  }
  
}
