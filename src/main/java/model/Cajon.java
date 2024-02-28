package model;

public class Cajon {
    enum Estado {
        OCUPADO,
        LIBRE,
        RESERVADO
    }
    private int id;
    private int piso;
    private Estado estado;

    // Constructor
    public Cajon(int id, int piso, Estado estado) {
        this.id = id;
        this.piso = piso;
        this.estado = estado;
    }

    // Getters y setters para los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
