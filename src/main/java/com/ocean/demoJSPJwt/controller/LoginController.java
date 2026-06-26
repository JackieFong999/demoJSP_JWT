package com.ocean.demoJSPJwt.controller;

import com.ocean.demoJSPJwt.model.AppUser;
import com.ocean.demoJSPJwt.service.AppUserService;
import com.ocean.demoJSPJwt.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    
    @Autowired
    private AppUserService appUserService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    // Process login (session-based, original)
    @PostMapping("/login")
    public String processLogin(@RequestParam("userName") String userName,
                               @RequestParam("userPassword") String userPassword,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        System.out.println("=== Login Attempt ===");
        System.out.println("Username: " + userName);
        
        try {
            AppUser user = appUserService.getUserByUserName(userName);
            
            if (user == null) {
                model.addAttribute("errorMessage", "Invalid username or password!");
                return "login";
            }
            
            boolean passwordMatches = appUserService.verifyPassword(userPassword, user.getUserPassword());
            
            if (passwordMatches) {
                System.out.println("Login successful for user: " + userName);
                
                // Generate JWT token
                String jwtToken = jwtUtil.generateToken(user.getUserName());
                System.out.println("JWT Token generated: " + jwtToken);
                
                // Store user info in session
                session.setAttribute("loggedInUser", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("userEmail", user.getUserEmail());
                session.setAttribute("jwtToken", jwtToken);  // Store JWT in session
                
                redirectAttributes.addFlashAttribute("successMessage", "Welcome back, " + user.getUserName() + "!");
                
                return "redirect:/home";
            } else {
                model.addAttribute("errorMessage", "Invalid username or password!");
                return "login";
            }
            
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred during login. Please try again.");
            return "login";
        }
    }
    
    // JWT API login endpoint (returns JSON token for API/SPA clients)
    @PostMapping("/login/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiLogin(@RequestParam("userName") String userName,
                                                         @RequestParam("userPassword") String userPassword) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            AppUser user = appUserService.getUserByUserName(userName);
            
            if (user == null) {
                response.put("success", false);
                response.put("message", "Invalid username or password!");
                return ResponseEntity.status(401).body(response);
            }
            
            boolean passwordMatches = appUserService.verifyPassword(userPassword, user.getUserPassword());
            
            if (passwordMatches) {
                String jwtToken = jwtUtil.generateToken(user.getUserName());
                
                response.put("success", true);
                response.put("message", "Login successful!");
                response.put("token", jwtToken);
                response.put("userName", user.getUserName());
                response.put("userEmail", user.getUserEmail());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password!");
                return ResponseEntity.status(401).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred during login.");
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "You have been logged out successfully!");
        return "redirect:/login";
    }
}
