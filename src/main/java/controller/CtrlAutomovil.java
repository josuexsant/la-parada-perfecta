package controller;

import model.Automovil;
import model.Log;
import model.Sesion;
import model.Usuario;

import java.sql.SQLException;
import java.util.LinkedList;

public class CtrlAutomovil {
    private static Automovil automovil;

    public LinkedList<String> getMatriculas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<String> matriculas = Automovil.getPlacas(idUsuario);
        return matriculas;
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

    public LinkedList<String> obtenerMatriculas() throws SQLException {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        LinkedList<String> matriculas = Automovil.getPlacas(idUsuario);
        return matriculasString(matriculas);
    }

    public LinkedList<String> matriculasString(LinkedList<String> matriculas) throws SQLException {
        LinkedList<String> matriculasStrings = new LinkedList<>();

        for (String automovil : matriculas) {
            String matriculaString = "-Id: " + Automovil.getId() +
                    "  ,-Automovil: " + Automovil.obtenerMarca(Automovil.getId()) +
                    "  ,-Placa: " + Automovil.getPlaca();
            matriculasStrings.add(matriculaString);
        }
        return matriculasStrings;
    }

    public LinkedList<String> getMarcas() {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        automovil = new Automovil();
        int idMarca = automovil.getIdMarca();


        LinkedList<String> marcas = Automovil.obtenerMarcas();

        for (String marca : marcas) {
            System.out.println(marca);
        }
        return marcas;
    }


    public void agregarMatricula(int idMarca, String placa) {
        Sesion sesion = Sesion._instance();
        Usuario usuario = sesion.getUsuario();
        int idUsuario = usuario.getId();
        Automovil automovil1 = new Automovil();
        String nombreMarca = Automovil.obtenerMarca(idMarca);
        if (idMarca != 0) {
            automovil1.guardarAutomovil(idUsuario, idMarca, placa);
            System.out.println("Matrícula creada exitosamente.");
        } else {
            System.out.println("No se pudo encontrar la marca especificada.");
        }
    }

    public boolean eliminarMatricula(String matricula) {
        Automovil automovil1 = new Automovil();
        return automovil1.eliminarMatricula(matricula);
    }

    public boolean modificarMatricula(int nuevoIdMarca, String nuevaPlaca, String matricula) {
        int idAutomovil = Automovil.getIdConMatricula(matricula);
        Log.debug("id:" + idAutomovil);
        automovil = new Automovil(idAutomovil);
        if (automovil != null) {
            automovil.setIdMarca(nuevoIdMarca);
            Log.debug(String.valueOf(automovil.getIdMarca()));
            automovil.setPlaca(nuevaPlaca);
            Log.debug(automovil.getPlaca());
            automovil.modificarPlaca();
        } else {
            return false;
        }
        return false;
    }


    /**
     * Metodo para cuando el usuario tiene varios autos asociados a su cuenta
     *
     * @return: Una lista de objetos tipo auto
     */


}
