package com.ocean.demoJSPJwt.interceptor;

import com.ocean.demoJSPJwt.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // List of URLs that don't require login
    private static final String[] ALLOWED_URLS = {
        "/login",
        "/logout", 
        "/login/api",           // JWT login API endpoint
        "/introduction",
        "/login-content",
        "/introduction-content",
        "/fee-content",
        "/webjars/",
        "/css/",
        "/js/",
        "/images/"
    };
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        logger.info("=== INTERCEPTOR CHECKING URL: {}", requestURI);
        
        // Check if the URL is allowed without login
        for (String allowedUrl : ALLOWED_URLS) {
            if (requestURI.contains(allowedUrl) || requestURI.equals("/demoJSP_JWT/") || requestURI.equals("/demoJSP_JWT")) {
                logger.info("Allowing access to: {}", requestURI);
                return true;
            }
        }
        
        
        System.out.println("*** Just Before JWT Checking ");
        logger.info("*** Just Before JWT Checking");
        // --- JWT Authentication Check ---
        // Check Authorization header for Bearer token
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        	
        	logger.info("*** JWT 111 !");
        	
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
            	
            	logger.info("*** JWT 222 !");
            	
                String username = jwtUtil.getUsernameFromToken(token);
                logger.info("JWT AUTHORIZED - User: {}, URL: {}", username, requestURI);
                // Store username in request attribute so controllers can access it
                request.setAttribute("jwtUser", username);
                return true;
            } else {
            	
            	logger.info("*** JWT 333 !");
            	
                logger.warn("JWT TOKEN INVALID - URL: {}", requestURI);
            }
        }
        
        // --- Session Authentication Check (original) ---
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            logger.warn("UNAUTHORIZED ACCESS - No logged in user, redirecting to login. URL: {}", requestURI);
            response.sendRedirect("/demoJSP_JWT/login");
            return false;
        }
        
        logger.info("SESSION AUTHORIZED - User: {}, URL: {}", session.getAttribute("userName"), requestURI);
        return true;
    }
}
