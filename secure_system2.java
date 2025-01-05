import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

class InputValidator {

    private static final String SAFE_STRING_PATTERN = "^[a-zA-Z0-9_@.]*$";

    public static boolean isValid(String input) {
        return input != null && Pattern.matches(SAFE_STRING_PATTERN, input);
    }
}

public class secure_system2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String dbname = "testdb"; // replace with your database name
        String dbuser = "root";  // replace with your server user name
        String dbpass = ""; // replace with your server password

        try {
            
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/"+dbname+"?allowMultiQueries=true",dbuser,dbpass);
            Statement statement = connection.createStatement();
            // Vulnerable query
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            if (InputValidator.isValid(username) && InputValidator.isValid(password)) {
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    System.out.println("Login successful! Welcome " + resultSet.getString("username"));
                } 
                else {
                    System.out.println("Invalid username or password.");
                }
                System.out.println("Query executed successfully!");
                connection.close();
            } else {
                System.out.println("Invalid input detected.");
            }            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}