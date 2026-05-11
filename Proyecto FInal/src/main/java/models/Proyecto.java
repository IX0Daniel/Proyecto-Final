package models;

public class Proyecto {

    private int id_proyecto;
    private int id_cliente;
    private String titulo;
    private String descripcion;
    private int id_categoria;
    private double presupuesto;
    private String fecha_limite;
    private String estado;

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id) {
        this.id_proyecto = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setIdCliente(int idCliente) {
        this.id_cliente = idCliente;
    }

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
        return id_categoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.id_categoria = idCategoria;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getFechaLimite() {
        return fecha_limite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fecha_limite = fechaLimite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
