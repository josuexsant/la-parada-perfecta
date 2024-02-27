import controller.CtrlUsuario;
import model.CreateConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            CreateConnection createConn = new CreateConnection();
            Connection conn = createConn.getConnection();
            String query = "show tables";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String tableName = rs.getString(1); // El nombre de la tabla es el primer valor en cada fila
                System.out.println(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CtrlUsuario ctrlUsuario = new CtrlUsuario();
        ctrlUsuario.iniciarSesion("josuexsanta@example.com","1234");

    }
}
