package com.ocean.demoJSPJwt.controller;

import com.ocean.demoJSPJwt.model.AppUser;
import com.ocean.demoJSPJwt.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import java.util.HashMap;
import java.util.List;
// import java.util.Map;


// 19/05/2026 : @RestController

@Controller  					//# Changed from @RestController
@RequestMapping("/app_user")		//@RequestMapping("/")		//# @RequestMapping("/api")
public class AppUserController {

    @Autowired
    AppUserService service;

    @GetMapping("/list")
    public String getAllAppUsers(Model model) {  	
    	System.out.println("Controller: Fetching all app_user");
        List<AppUser> app_users = service.getAllAppUser();
        System.out.println(app_users);
        
        if (app_users != null && !app_users.isEmpty()) {
            AppUser firstUser = app_users.get(0);
            System.out.println("First user - ID: " + firstUser.getUserId());
            System.out.println("First user - Name: " + firstUser.getUserName());
            System.out.println("First user - Email: " + firstUser.getUserEmail());
            System.out.println("First user - CreateDt: " + firstUser.getCreateDt());
        }
        
        model.addAttribute("app_users", app_users);
        return "app_users";  	//# Returns app_users.jsp
    }
    
    
    //# Delete user
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // AppUserService appUserService;
			if (service.appUserExists(id)) {
				service.deleteAppUser(id);
                redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/app_user/list";
    }
    
    
 // Show add user form (for Add function - will implement later)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("app_user", new AppUser());
        return "app_user_form";
    }

    /* 20/05/2026 : Due to password, create_dt and create_user become empty after Edit and Updated.
    // Save new user (for Add function - will implement later)
    @PostMapping("/save")
    public String saveUser(@ModelAttribute AppUser user, RedirectAttributes redirectAttributes) {
        try {
        	service.saveAppUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "User saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving user: " + e.getMessage());
        }
        return "redirect:/app_user/list";
    }
    */
    @PostMapping("/save")
    public String saveUser(@ModelAttribute AppUser appUser, 
                          @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                          RedirectAttributes redirectAttributes) {
        try {
            System.out.println("=== Saving User ===");
            System.out.println("Username: " + appUser.getUserName());
            System.out.println("Email: " + appUser.getUserEmail());
            System.out.println("Password from form: " + (appUser.getUserPassword() != null && !appUser.getUserPassword().isEmpty() ? "PROVIDED" : "EMPTY"));
            System.out.println("User ID: " + appUser.getUserId());
            
            // For existing user (edit mode)
            if (appUser.getUserId() != null && appUser.getUserId() > 0) {
                // Fetch the existing user from database
                AppUser existingUser = service.getUserById(appUser.getUserId());
                
                if (existingUser == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
                    return "redirect:/app_user/list";
                }
                
                // Preserve the original create_dt and create_user
                appUser.setCreateDt(existingUser.getCreateDt());
                appUser.setCreateUser(existingUser.getCreateUser());
                
                
                // Check if password was provided (not empty)
                if (appUser.getUserPassword() == null || appUser.getUserPassword().isEmpty()) {
                    // Keep existing password
                    appUser.setUserPassword(existingUser.getUserPassword());
                    System.out.println("Keeping existing password");
                } else {
                    // New password provided - use it
                    System.out.println("Updating with new password");
                }
                
                // Save the user
                service.saveAppUser(appUser);
                redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
                
            } else {
                // For new user (add mode)
                if (appUser.getUserPassword() == null || appUser.getUserPassword().isEmpty()) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Password is required!");
                    return "redirect:/app_user/add";
                }
                
                // Validate password confirmation
                if (confirmPassword != null && !appUser.getUserPassword().equals(confirmPassword)) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match!");
                    return "redirect:/app_user/add";
                }
                
                
                //# 20/05/2026
                //# Validate password strength
                //# Web Site - Hash checking : https://bcrypt-generator.com/
                if (!isValidPassword(appUser.getUserPassword())) {
                    redirectAttributes.addFlashAttribute("errorMessage", 
                        "Password must be at least 8 characters long and contain at least one number and one special character!");
                    return "redirect:/app_user/add";
                }
                
                // Save new user
                service.saveAppUser(appUser);
                redirectAttributes.addFlashAttribute("successMessage", "User added successfully!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving user: " + e.getMessage());
            if (appUser.getUserId() != null && appUser.getUserId() > 0) {
                return "redirect:/app_user/edit?id=" + appUser.getUserId();
            } else {
                return "redirect:/app_user/add";
            }
        }
        
        return "redirect:/app_user/list";
    }
    
    
    
    
    // Show edit user form (for Edit function - will implement later)
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        AppUser user = service.getUserById(id);
        if (user != null) {
            model.addAttribute("app_user", user);
            return "app_user_form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            return "redirect:/app_user/list";
        }
    }
    
    
    //# 20/05/2026
    // Password strength validation
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasNumber = false;
        boolean hasSpecialChar = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        
        return hasNumber && hasSpecialChar;
    }
    

}
