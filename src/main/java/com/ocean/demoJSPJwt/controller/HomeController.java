package com.ocean.demoJSPJwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//19/05/2026 : @RestController

@Controller  				
@RequestMapping("/home")		
public class HomeController {
	
    @GetMapping({"", "/"})
    public String greeting(Model model) {  					// Now Model is recognized    	
    	model.addAttribute("message", "Home Page : Hello World! (02/06/2026)");
        return "home";  									//# Returns home.jsp
    }

}
