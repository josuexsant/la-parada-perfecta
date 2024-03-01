package model;

public class Sesion {
    private static Sesion instancia;
    private Usuario usuario;

    private Sesion() {
        // Constructor privado para evitar instanciación externa
    }

    public static Sesion _instance() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
