<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:if test="${empty app_user.userId}">Add New User</c:if><c:if test="${not empty app_user.userId}">Edit User</c:if></title>
  
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app_user_form.css">
    <script src="${pageContext.request.contextPath}/js/app_user_form.js"></script>
    
</head>
<body>
    <div class="container">
        <h1>
            <c:if test="${empty app_user.userId}">
                Add New User
            </c:if>
            <c:if test="${not empty app_user.userId}">
                Edit User
            </c:if>
        </h1>
        
        <!-- Display error messages if any -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        
        <!-- Display info message for edit mode -->
        <c:if test="${not empty app_user.userId}">
            <div class="info-message">
                Editing user: <strong>${app_user.userName}</strong> (ID: ${app_user.userId})
            </div>
        </c:if>
        
        <form action="/demoJSP_JWT/app_user/save" method="post">
            <!-- Hidden field for user ID (only for edit mode) -->
            <c:if test="${not empty app_user.userId}">
                <input type="hidden" name="userId" value="${app_user.userId}">
            </c:if>
            
            <!-- Username field -->
            <div class="form-group">
                <label for="userName" class="required">Username</label>
                <input type="text" 
                       id="userName" 
                       name="userName" 
                       value="${app_user.userName}" 
                       required 
                       <c:if test="${not empty app_user.userId}">readonly class="readonly-field"</c:if>
                       placeholder="Enter username">
                <c:if test="${not empty app_user.userId}">
                    <small style="color: #666;">Username cannot be changed</small>
                </c:if>
            </div>
            
            <!-- Email field -->
            <div class="form-group">
                <label for="userEmail" class="required">Email</label>
                <input type="email" 
                       id="userEmail" 
                       name="userEmail" 
                       value="${app_user.userEmail}" 
                       required 
                       placeholder="Enter email address">
            </div>
            
            <!-- Password field with requirements -->
            <div class="form-group">
                <label for="userPassword" class="required">
                    <c:if test="${empty app_user.userId}">Password</c:if>
                    <c:if test="${not empty app_user.userId}">New Password (leave blank to keep current)</c:if>
                </label>
                <input type="password" 
                       id="userPassword" 
                       name="userPassword" 
                       <c:if test="${empty app_user.userId}">required</c:if>
                       placeholder="<c:if test="${empty app_user.userId}">Enter password</c:if><c:if test="${not empty app_user.userId}">Enter new password (optional)</c:if>">
                <c:if test="${empty app_user.userId}">
                    <small style="color: #666;">
                        Password requirements: at least 8 characters, including at least one number and one special character
                    </small>
                </c:if>
                <c:if test="${not empty app_user.userId}">
                    <small style="color: #666;">Leave blank to keep the current password</small>
                </c:if>
            </div>
            
            <!-- Confirm Password field (for add mode) -->
            <c:if test="${empty app_user.userId}">
                <div class="form-group">
                    <label for="confirmPassword" class="required">Confirm Password</label>
                    <input type="password" 
                           id="confirmPassword" 
                           name="confirmPassword" 
                           required 
                           placeholder="Confirm password">
                </div>
            </c:if>
            
            <!-- Button group -->
            <div class="button-group">
                <button type="submit" class="btn btn-save">Save</button>
                <a href="/demoJSP_JWT/app_user/list" class="btn btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
    
    <!-- External JavaScript Files -->
    <script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
    
    <!-- Initialize validation based on mode -->
    <script>
        // This small script initializes the appropriate validation
        // based on whether we're in add mode or edit mode
        document.addEventListener('DOMContentLoaded', function() {
            <c:if test="${empty app_user.userId}">
                // Add mode
                setupAddModeValidation();
            </c:if>
            <c:if test="${not empty app_user.userId}">
                // Edit mode
                setupEditModeValidation();
            </c:if>
        });
    </script>
</body>
</html>