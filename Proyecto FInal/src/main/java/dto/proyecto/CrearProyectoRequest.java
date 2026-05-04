package dto.proyecto;

import java.util.List;

public class CrearProyectoRequest {

    private String titulo;
    private String descripcion;
    private int idCategoria;
    private double presupuesto;
    private String fechaLimite;
    private List<Integer> habilidades;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public List<Integer> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Integer> habilidades) {
        this.habilidades = habilidades;
    }
}
