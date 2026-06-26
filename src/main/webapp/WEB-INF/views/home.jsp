<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    
</head>
<body>
    <div class="container">
        <!-- User Info Section -->
        <div class="user-info">
            <div>
                Welcome, <strong>${sessionScope.userName}</strong>!
            </div>
            <a href="/demoJSP_JWT/logout" class="logout-btn">Logout</a>
        </div>
        
        <h1>Welcome to Product Management System</h1>
        <p>${message}</p>
        <p>${successMessage}</p>
        
        <div>
            <a href="${pageContext.request.contextPath}/product/list" class="button button-html">HTML Table View</a>
            <a href="${pageContext.request.contextPath}/product/products-grid" class="button button-jqgrid">jqGrid View</a>
            <a href="${pageContext.request.contextPath}/product/products-agrid" class="button button-agrid">AG Grid View</a>
            <a href="${pageContext.request.contextPath}/app_user/list" class="button button-agrid">App User</a>
			<a href="/demoJSP_JWT/" class="button">Back to Home</a>
        </div>
    </div>
</body>
</html>