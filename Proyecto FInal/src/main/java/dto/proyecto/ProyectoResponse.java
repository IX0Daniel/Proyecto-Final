package dto.proyecto;

public class ProyectoResponse {

    private int id;
    private String titulo;
    private String descripcion;
    private double presupuesto;
    private String estado;


    public ProyectoResponse(int id, String titulo, String descripcion, double presupuesto, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupuesto = presupuesto;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public String getEstado() {
        return estado;
    }
}
