<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        .container {
            padding: 20px;
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #333;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            margin: 4px 2px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .btn-delete {
            background-color: #f44336;
        }
        .btn-edit {
            background-color: #ff9800;
        }
        .btn-add {
            background-color: #2196F3;
            margin-bottom: 20px;
        }
        .btn:hover {
            opacity: 0.8;
        }
        .password-mask {
            font-family: monospace;
            letter-spacing: 2px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>User List</h1>
        
        <!-- Add User Button -->
        <a href="/demoJSP_JWT/app_user/add" class="btn btn-add">Add New User</a>
        
        <c:if test="${empty app_users}">
            <p>No app users found.</p>
        </c:if>
        
        <c:if test="${not empty app_users}">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Password</th>
                        <th>Created Date</th>
                        <th>Created By</th>
                        <th>Updated Date</th>
                        <th>Updated By</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${app_users}" var="app_user">
                        <tr>
                            <td>${app_user.userId}</td>
                            <td>${app_user.userName}</td>
                            <td>${app_user.userEmail}</td>
                            <td class="password-mask">••••••••</td>
                            <td>
                                <fmt:formatDate value="${app_user.createDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>${app_user.createUser}</td>
                            <td>
                                <fmt:formatDate value="${app_user.updateDt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>${app_user.updateUser}</td>
                            <td>
                                <a href="/demoJSP_JWT/app_user/edit?id=${app_user.userId}" class="btn btn-edit">Edit</a>
                                <a href="/demoJSP_JWT/app_user/delete?id=${app_user.userId}" 
                                   onclick="return confirm('Are you sure you want to delete user: ${app_user.userName}?')" 
                                   class="btn btn-delete">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <br/>
        <a href="/demoJSP_JWT/home">Back to Home</a>
    </div>
</body>
</html>