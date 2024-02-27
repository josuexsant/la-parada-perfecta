package model;

public class Usuario {
    private int id;
    private String nombre;
    private String password;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int numeroTelefono;
    private String correoElectronico;
    private int idGenero;
    private int idCiudad;

    public Usuario(int id, String nombre, String password, String apellidoPaterno, String apellidoMaterno, int numeroTelefono, String correoElectronico, int idGenero, int idCiudad) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
        this.idGenero = idGenero;
        this.idCiudad = idCiudad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    /**
     * @author: Josue Santamaria
     * Este metodo busca en la base de dato el correo electronico del usuario
     * @return
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }
}