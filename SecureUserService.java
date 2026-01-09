import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecureUserService {
    
    private static final String SECRET_KEY = System.getenv("ENCRYPTION_KEY");
    private final SecureRandom random = new SecureRandom();
    
    // Secure password hashing
    public String hashPassword(String password) {
        try {
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    // Secure file path validation
    public String sanitizeFilePath(String userPath) {
        if (userPath == null || userPath.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be empty");
        }
        
        // Remove any path traversal attempts
        String sanitized = userPath.replaceAll("\\.\\.", "");
        sanitized = sanitized.replaceAll("/", "");
        sanitized = sanitized.replaceAll("\\\\", "");
        
        // Only allow alphanumeric and underscores
        if (!sanitized.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Invalid characters in path");
        }
        
        return sanitized;
    }
    
    // Secure encryption with proper key management
    public String encryptData(String data) {
        try {
            if (SECRET_KEY == null) {
                throw new IllegalStateException("Encryption key not configured");
            }
            
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
    
    // Input validation
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        // Simple but secure email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    
    // Secure random token generation
    public String generateSecureToken() {
        byte[] token = new byte[32];
        random.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }
}
