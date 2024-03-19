package model;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class ReservaGarantizada {

        private int id;
        private int idAutomovil;
        private String fecha_inicio;
        private  String fecha_fin;
        private int idCajon;
        private int idUsuario;
        private static DBManager dbManager = new DBManager();

        public ReservaGarantizada(int id, int idAutomovil, String fecha_inicio, String fecha_fin, int idCajon, int idUsuario) {
            this.id = id;
            this.idAutomovil = idAutomovil;
            this.fecha_inicio = fecha_inicio;
            this.fecha_fin = fecha_fin;
            this.idCajon = idCajon;
            this.idUsuario = idUsuario;
        }

        public boolean guardarReserva() throws SQLException {
            Connection conn = dbManager.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String query = "INSERT INTO reservaciones_garantizadas (id_automovil, fecha_inicio, fecha_fin, id_cajon, id_usuario) VALUES (?, ?, ?, ?,?)";
                stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, idAutomovil);
                stmt.setString(2,fecha_inicio);
                stmt.setString(3, fecha_fin);
                stmt.setInt(4, idCajon);
                stmt.setInt(5, idUsuario);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating reserva failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating reserva failed, no ID obtained.");
                    }
                }
                Log.success("Se guardo la reserva con el ID: " + this.id);
                return true; // Se pudo guardar la reserva exitosamente
            } catch (SQLException e) {
                e.printStackTrace();
                Log.error("No se pudo guarda la reserva");
                return false; // Hubo un error al guardar la reserva
            } finally {
                conn.close();
            }
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdAutomovil() {
            return idAutomovil;
        }

        public void setIdAutomovil(int idAutomovil) {
            this.idAutomovil = idAutomovil;
        }

        public String getFechainicio() {
            return fecha_inicio;
        }

        public void setFechainicio(String fechainicio) {
            this.fecha_inicio = fechainicio;
        }

        public String getFechafin() {
            return fecha_fin;
        }

        public void setFechafin(String fechafin) {
            this.fecha_fin  = fechafin;
        }

        public int getIdCajon() {
            return idCajon;
        }

        public void setIdCajon(int idCajon) {
            this.idCajon = idCajon;
        }

        public int getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
        }
    }


