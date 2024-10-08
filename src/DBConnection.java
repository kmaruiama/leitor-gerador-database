import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/paradigmas_database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection conectaDb() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao se conectar à base de dados: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Classe nao encontrada: " + e);
        }
        return connection;
    }
}
