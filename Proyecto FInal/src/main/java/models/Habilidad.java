package models;

public class Habilidad


{
    private int idHabilidad;
    private String nombre;
    private String descripcion;
    private int idCategoria;
    private boolean activo;

    public Habilidad(int idHabilidad, String nombre, String descripcion, int idCategoria, boolean activo) {

        this.idHabilidad = idHabilidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.activo = activo;
    }

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setIdHabilidad(int idHabilidad) {
        this.idHabilidad = idHabilidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

