public class VulnerableCode {
    
    public void authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        statement.execute(query);
    }
    
    public void deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE id = " + userId;
        database.executeUpdate(sql);
    }
    
    public void searchProducts(String searchTerm) {
        String query = "SELECT * FROM products WHERE name LIKE '%" + searchTerm + "%'";
        stmt.executeQuery(query);
    }
}
