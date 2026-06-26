package com.ocean.demoJSPJwt.service;

import com.ocean.demoJSPJwt.model.AppUser;
import com.ocean.demoJSPJwt.repo.AppUserRepo;
import com.ocean.demoJSPJwt.util.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    AppUserRepo repo;

    @Autowired
    PasswordEncoder passwordEncoder;  // 20/05/2026 : Inject the password encoder
    
    // Get all users
    public List<AppUser> getAllAppUser(){
        return repo.findAll();
    }
    
    // Get user by ID
    public AppUser getUserById(Integer id){
        Optional<AppUser> user = repo.findById(id);
        return user.orElse(null);
    }
    
    
    //# 21/05/2026
    // Find user by username
    public AppUser getUserByUserName(String userName) {
        return repo.findByUserName(userName);
    }
    
    
    // Save or update user
    public AppUser saveAppUser(AppUser appUser){
    	
    	System.out.println("=== Service: Saving User ===");
        System.out.println("User ID: " + appUser.getUserId());
        
        Date now = new Date();
        
        // For new user (ID is null or 0)
        if (appUser.getUserId() == null || appUser.getUserId() == 0) {
            System.out.println("Creating new user");
            appUser.setCreateDt(now);
            appUser.setCreateUser("system");
            
            // Hash the password before saving
            if (appUser.getUserPassword() != null && !appUser.getUserPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(appUser.getUserPassword());
                appUser.setUserPassword(hashedPassword);
                System.out.println("Password hashed and stored");
            } else {
                throw new RuntimeException("Password cannot be null or empty for new user");
            }
            
        } else {
            // For existing user
            System.out.println("Updating existing user with ID: " + appUser.getUserId());
            
            // If a new password is provided, hash it
            if (appUser.getUserPassword() != null && !appUser.getUserPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(appUser.getUserPassword());
                appUser.setUserPassword(hashedPassword);
                System.out.println("New password hashed and updated");
            } else {
                // Keep existing password - fetch it from database
                AppUser existingUser = getUserById(appUser.getUserId());
                if (existingUser != null) {
                	appUser.setUserPassword(existingUser.getUserPassword());
                    System.out.println("Keeping existing password");
                }
            }
        }
        
        // Always update the update date
        appUser.setUpdateDt(new Date());  		// Gets current date and time
        appUser.setUpdateUser("system");
        
        return repo.save(appUser);
    }
    
    // Delete user by ID
    public void deleteAppUser(Integer id){
        repo.deleteById(id);
    }
    
    // Check if user exists
    public boolean appUserExists(Integer id){
        return repo.existsById(id);
    }
    
    
    //# 20/05/2026
    // Method to verify user login
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
}
