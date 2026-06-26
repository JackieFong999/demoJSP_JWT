<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Product Management System</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    
</head>
<body>

	<jsp:include page="/WEB-INF/views/header.jsp" />
	
    <div class="scrollable-content">
        <div class="content-wrapper">
            <!-- Login form content -->
            <div class="login-container">
				<div class="login-body">
		            <!-- Display error message if any -->
		            <c:if test="${not empty errorMessage}">
		                <div class="error-message">${errorMessage}</div>
		            </c:if>
		            
		            <!-- Display success message if any -->
		            <c:if test="${not empty successMessage}">
		                <div class="success-message">${successMessage}</div>
		            </c:if>
		            
		            <form action="/demoJSP_JWT/login" method="post">
		                <div class="form-group">
		                    <label for="userName">Username</label>
		                    <input type="text" 
		                           id="userName" 
		                           name="userName" 
		                           required 
		                           placeholder="Enter your username"
		                           value="${param.userName}">
		                </div>
		                
		                <div class="form-group">
		                    <label for="userPassword">Password</label>
		                    <input type="password" 
		                           id="userPassword" 
		                           name="userPassword" 
		                           required 
		                           placeholder="Enter your password">
		                </div>
		                
		                <button type="submit" class="btn-login">Login</button>
		            </form>
		            
		            <div class="back-link">
		                <a href="/demoJSP_JWT/">← Back to Home</a>
		            </div>
		            
		        </div>                
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
</body>
</html>