package model;

public class Automovil {
    private int id;
    private int idUsuario;
    private int idMarca;
    private String placa;

    // Constructor
    public Automovil(int id, int idUsuario, int idMarca, String placa) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idMarca = idMarca;
        this.placa = placa;
    }


    // Getters y setters para los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }



}
