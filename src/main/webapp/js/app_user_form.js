/**
 * User Form Validation - Detects mode automatically
 */

// Password strength validation function
function validatePassword(password) {
    if (password.length < 8) {
        return { valid: false, message: "Password must be at least 8 characters long!" };
    }
    
    var hasNumber = /\d/;
    var hasSpecial = /[!@#$%^&*(),.?":{}|<>]/;
    
    if (!hasNumber.test(password)) {
        return { valid: false, message: "Password must contain at least one number!" };
    }
    
    if (!hasSpecial.test(password)) {
        return { valid: false, message: "Password must contain at least one special character!" };
    }
    
    return { valid: true, message: "" };
}

// Check if this is edit mode (has userId hidden field)
function isEditMode() {
    var userIdField = document.querySelector('input[name="userId"]');
    return userIdField && userIdField.value && userIdField.value !== '';
}

// Setup form validation
function setupFormValidation() {
    var form = document.querySelector('form');
    if (!form) return;
    
    form.addEventListener('submit', function(e) {
        var password = document.getElementById('userPassword').value;
        var isEdit = isEditMode();
        
        if (!isEdit) {
            // Add mode - password is required
            var confirmPassword = document.getElementById('confirmPassword').value;
            
            // Validate password strength
            var passwordValidation = validatePassword(password);
            if (!passwordValidation.valid) {
                e.preventDefault();
                alert(passwordValidation.message);
                return false;
            }
            
            // Validate password confirmation
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Password and Confirm Password do not match!');
                return false;
            }
        } else {
            // Edit mode - password is optional
            if (password.length > 0 && password.length < 8) {
                e.preventDefault();
                alert('Password must be at least 8 characters long!');
                return false;
            }
        }
    });
}

// Initialize when page loads
document.addEventListener('DOMContentLoaded', function() {
    setupFormValidation();
});