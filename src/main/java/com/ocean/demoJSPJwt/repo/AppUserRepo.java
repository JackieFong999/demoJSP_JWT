package com.ocean.demoJSPJwt.repo;

import com.ocean.demoJSPJwt.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Integer> {
	
    // Find user by username (for login)
    AppUser findByUserName(String userName);
    
    // Check if username exists
    boolean existsByUserName(String userName);
    
    // Check if email exists
    boolean existsByUserEmail(String userEmail);

}
