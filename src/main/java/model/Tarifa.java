package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Tarifa {
    int id;
    float precio;
    Concepto concepto;
    private static DBManager dbManager = new DBManager();

    public Tarifa(int id, float precio, Concepto concepto) {
        this.id = id;
        this.precio = precio;
        this.concepto = concepto;
    }


    public Tarifa(String concepto) {
        String query = "SELECT " +
                "    id," +
                "    precio " +
                "FROM " +
                "    Tarifa " +
                "WHERE " +
                "    concepto = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, concepto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.precio = resultSet.getFloat("precio");
                    this.concepto = Concepto.valueOf(concepto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Método para obtener la lista de conceptos
    public static List<Concepto> getConceptos() {
        List<Concepto> conceptos = new ArrayList<>();
        for (Concepto concepto : Concepto.values()) {
            conceptos.add(concepto);
        }
        return conceptos;
    }

    //visualizar
    public static Map<String, Double> visualizarTarifas() throws SQLException {
        Map<String, Double> tarifasMap = new HashMap<>();

        String query = "SELECT concepto, precio FROM Tarifa";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String concepto = resultSet.getString("concepto");
                double precio = resultSet.getDouble("precio");
                tarifasMap.put(concepto, precio);
            }
        }

        return tarifasMap;
    }


    public void guardarTarifa(float nuevoPrecio) throws SQLException {
        String query = "UPDATE Tarifa " +
                "SET precio = ? " +
                "WHERE concepto = ?";
        Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try {
            pstmt.setFloat(1, nuevoPrecio);  // Utiliza el nuevo precio pasado como parámetro
            pstmt.setString(2, this.concepto.toString()); // Utiliza el concepto del objeto actual
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Tarifa.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }







    // Getters y setters

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}

