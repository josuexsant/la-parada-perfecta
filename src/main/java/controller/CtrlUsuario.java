package controller;
import model.Log;
import model.Sesion;
import model.Usuario;
import java.sql.SQLException;
import static model.Usuario.usuarioExiste;

public class    CtrlUsuario {
    private static Usuario usuario;
    public Sesion sesion;

    public boolean registrarUsuario(String nombre, String password, String apellidoPaterno, String apellidoMaterno, String numeroTelefono, String correoElectronico, int idGenero, int idCiudad) throws SQLException {
        Usuario p = new Usuario(nombre, password, apellidoPaterno, apellidoPaterno, numeroTelefono, correoElectronico, idGenero, idCiudad);

        if (usuarioExiste(correoElectronico)) {
            Log.warn("El correo electronico ya esta registado");
        } else {
            p.setNombre(nombre);
            p.setPassword(password);
            p.setApellidoPaterno(apellidoPaterno);
            p.setApellidoMaterno(apellidoMaterno);
            // FIXME Crear una condiccional sobre el telefono para que acepte solamente numeros
            p.setNumeroTelefono(numeroTelefono);
            p.setIdGenero(idGenero);
            p.setIdCiudad(idCiudad);
            p.setCorreoElectronico(correoElectronico);

            try {
                if (Usuario.registrar(p)) {
                    iniciarSesion(correoElectronico, password);
                    Log.success("Usuario creado en el controler");
                    return true;
                }
                Log.error("Registro de usuario fallido");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * Este metodo consta de dos pasos, validar el correo y validar contraseña, una vez que ambos
     * metodos han sido validados entonces se toma en usuario que se inicializo en validarCorreoelectronico()
     * y se toma como la sesion activa en el sistema, para ellos usamos un Singleton Sesion para acceder
     * a un único usuario en toda la aplicacion desde cualquier parte
     * @param correoElectronico: String que es dada por el usuario
     * @param password: String que es dada por el usuario
     * @return: true en caso de concluir los dos pasos.
     */
    public boolean iniciarSesion(String correoElectronico, String password) {
        try {
            if (validarEmail(correoElectronico)) {
                if (validarPassword(password)) {
                    sesion = Sesion._instance();
                    sesion.setUsuario(usuario); //SESION ACTIVA
                    Log.success("Inicio de sesión exitoso");
                    Log.trace("Sesion activa: " + sesion.getUsuario().getCorreoElectronico());
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Este metodo valida un correo electronico, una vez que fue validado se inicializa el atributo usuario
     * para que en el siguiente paso se pueda acceder a usuario.getPassword y validar la contraseña, en caso
     * de que el correo no este registrado no se puede pasar al siguiente paso.
     * @param correoElectronico: Es un String que debe coicidir exactamente con el correo registrado en la base.
     * @throws SQLException
     */
    public boolean validarEmail(String correoElectronico) throws SQLException {
        if (usuarioExiste(correoElectronico)) {
            int idUsuario = Usuario.obtenerIdUsuario(correoElectronico);
            usuario = new Usuario(idUsuario);
            Log.trace("Usuario a validar: " + usuario.getCorreoElectronico());
            Log.info("Correo valido");
            return true;
        }
        Log.error("Correo no registrado");
        return false;
    }

    /**
     * Este metodo usa el atributo usuario que se inicializo en el paso anterior "validarCorreoElectronico()",
     * usa el metodo de modelo.Usuario.getPassword() para compara la String que el usuario coloque en el campo
     * y la compara con la base de datos.
     * En caso de no validar la contraseña entonces tomara el atritubo usuario y lo volvera null para que el
     * proceso vuelva a empezar.
     * @param password: Un String que debe coincidir con la password registrada en la tabla "passwords"
     * @return true en caso de que los campos id_usuario y passwords guardados en la tabla "passwords"
     * sean los mismos.
     * @throws SQLException
     */
    public boolean validarPassword(String password) throws SQLException {
        if (usuario.getPassword().equals(password)){
            Log.info("Contraseña valida");
            return true;
        }
        usuario = null; // Si no se puede validar la contraseña en usuario vuelve a null
        Log.error("Contraseña incorrecta");
        Log.trace("Usuario vuelve a null");
        return false;
    }

    public String getnombreC() throws SQLException {
        int idUsuario = usuario.getId();
        String nombreCompleto = usuario.nombreCompleto(idUsuario);
        return nombreCompleto;
    }

    public boolean cerrarSesion(){
        Sesion._instance().setUsuario(null);
        Log.warn("Sesión cerrada");
        return true;
    }
}
