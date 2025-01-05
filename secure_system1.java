import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class secure_system1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", "root", ""); 
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            // change the parameters of the system(username & password) with the placeholders
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful! Welcome " + resultSet.getString("username"));
            } else {
                System.out.println("Invalid username or password.");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
