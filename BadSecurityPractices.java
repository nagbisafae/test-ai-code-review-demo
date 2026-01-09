public class BadSecurityPractices {
    
    // Hardcoded credentials
    private static final String API_KEY = "sk-1234567890abcdef";
    private static final String DB_PASSWORD = "admin123";
    
    // SQL Injection
    public void login(String user, String pass) {
        String query = "SELECT * FROM users WHERE username = '" + user + "' AND password = '" + pass + "'";
        connection.createStatement().execute(query);
    }
    
    // Command Injection
    public void runSystemCommand(String filename) {
        Runtime.getRuntime().exec("cat " + filename);
    }
    
    // Path Traversal
    public File readFile(String path) {
        return new File("/data/" + path);
    }
}
