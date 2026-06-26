<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="login-container" style="max-width: 450px; margin: 0 auto; padding: 20px;">
    <div class="login-body" style="background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
        <h2 style="color: #4CAF50; text-align: center; margin-bottom: 30px;">Login</h2>
        
        <form action="/demoJSP_JWT/login" method="post">
            <div style="margin-bottom: 20px;">
                <label style="display: block; margin-bottom: 8px; font-weight: bold;">Username</label>
                <input type="text" 
                       name="userName" 
                       required 
                       style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box;">
            </div>
            
            <div style="margin-bottom: 20px;">
                <label style="display: block; margin-bottom: 8px; font-weight: bold;">Password</label>
                <input type="password" 
                       name="userPassword" 
                       required 
                       style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box;">
            </div>
            
            <button type="submit" style="width: 100%; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; padding: 12px; font-size: 16px; border-radius: 4px; cursor: pointer;">Login</button>
        </form>
        
        <div style="text-align: center; margin-top: 20px;">
            <button onclick="location.reload()" style="background: #666; color: white; border: none; padding: 8px 20px; border-radius: 4px; cursor: pointer;">Back to Home</button>
        </div>
    </div>
</div>
