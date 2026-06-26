package com.ocean.demoJSPJwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroductionController {
    
    @GetMapping("/introduction")
    public String introduction() {
        System.out.println("=== Introduction page accessed ===");
        return "introduction";
    }
}
