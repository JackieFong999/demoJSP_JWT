<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to Product Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    
    <script>
        $(document).ready(function() {
            // Function to load content dynamically
            window.loadContent = function(url) {
                console.log("Loading content from: " + url);
                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'html',
                    success: function(data) {
                        console.log("Content loaded successfully");
                        $('.content-wrapper').html(data);
                    },
                    error: function(xhr, status, error) {
                        console.error("Error loading content: " + error);
                        $('.content-wrapper').html('<div style="padding: 50px; text-align: center;">' +
                            '<h2>Error loading page</h2>' +
                            '<p>Please try again later.</p>' +
                            '<button onclick="location.reload()" class="btn-primary">Refresh Page</button>' +
                            '</div>');
                    }
                });
            };    
            // Reset to home content (original page)
            window.resetToHome = function() {
                // Reload the page to reset everything
                window.location.href = '/demoJSP_JWT/';
            };
        });
    </script>
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/WEB-INF/views/header.jsp" />  
    <!-- Scrollable Content -->
    <div class="scrollable-content">
        <div class="content-wrapper" id="mainContent">
            <div class="feature-section">
                <!-- Original Set 1 -->
                <div class="feature-card">
                    <div class="feature-icon">📦</div>
                    <h3>Product Management</h3>
                    <p>Manage your products with advanced features including AG Grid, jqGrid, and HTML table views</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">👥</div>
                    <h3>User Management</h3>
                    <p>Complete user management system with secure password hashing</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">🔒</div>
                    <h3>Secure Access</h3>
                    <p>Role-based access control and encrypted password storage</p>
                </div>
                
                <!-- Duplicate Set 2 - Different Icons -->
                <div class="feature-card">
                    <div class="feature-icon">🛍️</div>
                    <h3>Product Management</h3>
                    <p>Manage your products with advanced features including AG Grid, jqGrid, and HTML table views</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">👨‍💼</div>
                    <h3>User Management</h3>
                    <p>Complete user management system with secure password hashing</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">🛡️</div>
                    <h3>Secure Access</h3>
                    <p>Role-based access control and encrypted password storage</p>
                </div>
                
                <!-- Duplicate Set 3 - More Different Icons -->
                <div class="feature-card">
                    <div class="feature-icon">📊</div>
                    <h3>Product Management</h3>
                    <p>Manage your products with advanced features including AG Grid, jqGrid, and HTML table views</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">🧰</div>
                    <h3>User Management</h3>
                    <p>Complete user management system with secure password hashing</p>
                </div>
                
                <div class="feature-card">
                    <div class="feature-icon">🎁</div>
                    <h3>Secure Access</h3>
                    <p>Role-based access control and encrypted password storage</p>
                </div>
            </div>
            
            <div class="button-section">
                <button onclick="loadContent('/demoJSP_JWT/login-content')" class="btn-login">Login to System</button>
                <button onclick="loadContent('/demoJSP_JWT/introduction-content')" class="btn-introduction">Introduction</button>  
                <button onclick="loadContent('/demoJSP_JWT/fee-content')" class="btn-standard">Fee</button>
                 
            </div>
        </div>
    </div>
    
    <!-- Include Footer -->
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
</body>
</html>







