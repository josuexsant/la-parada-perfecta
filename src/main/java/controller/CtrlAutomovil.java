package controller;

import model.Automovil;
import model.Log;
import model.Sesion;
import model.Usuario;
import view.ResgitroReserva;

import java.sql.SQLException;
import java.util.LinkedList;

public class CtrlAutomovil {
    private static ResgitroReserva resgitroReserva;
    private static Automovil automovil;

    public LinkedList<String> getMatriculas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();

        LinkedList<String> placas = Automovil.getPlacas(idUsuario);

        for (String placa : placas) {
            System.out.println(placa);
        }

        return placas;
    }

    public String obtenerNombre() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        String nombre = usuario.getNombre();
        String apellidoP = usuario.getApellidoPaterno();
        String apellidoM = usuario.getApellidoMaterno();
        String nombreCompleto = nombre + " " + apellidoP + " " + apellidoM;
        return nombreCompleto;
    }

    public void crearMatricula(int idMarca, String placa) {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        Automovil automovil1 = new Automovil();
        try {
            String nombreMarca = automovil1.obtenerMarca(idMarca);
            if (nombreMarca != null) {
                automovil1.guardarAutomovil(idUsuario, idMarca, placa);
                System.out.println("Matrícula creada exitosamente.");
            } else {
                System.out.println("No se pudo encontrar la marca especificada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la matrícula.");
        }
    }

    public void eliminarMatricula(String matricula) {
        Automovil automovil1 = new Automovil();
        automovil1.eliminarMatricula(matricula);
    }

    public boolean modificarAutomovil(int nuevoIdMarca, String nuevaPlaca, String matricula) {
        try {
            int idAutomovil = Automovil.getIdConMatricula(matricula);
            Log.debug("id:" + idAutomovil);
            automovil = new Automovil(idAutomovil);
            if (automovil != null) {
                automovil.setIdMarca(nuevoIdMarca);
                Log.debug(String.valueOf(automovil.getIdMarca()));
                automovil.setPlaca(nuevaPlaca);
                Log.debug(automovil.getPlaca());
                automovil.modificarAutomovil();
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static Automovil getAutomovil() {
        return automovil;
    }

    /**
     * Metodo para cuando el usuario tiene varios autos asociados a su cuenta
     *
     * @return: Una lista de objetos tipo auto
     */
    public LinkedList<Automovil> automovilesRegistrados() {
        return null;
    }


}
