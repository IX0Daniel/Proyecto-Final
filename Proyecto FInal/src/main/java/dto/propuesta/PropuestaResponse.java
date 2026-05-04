package dto.propuesta;

public class PropuestaResponse {

    private int id;
    private int idProyecto;
    private double monto;
    private int plazoDias;
    private String estado;

    public PropuestaResponse(int id, int idProyecto, double monto, int plazoDias, String estado) {
        this.id = id;
        this.idProyecto = idProyecto;
        this.monto = monto;
        this.plazoDias = plazoDias;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public double getMonto() {
        return monto;
    }

    public int getPlazoDias() {
        return plazoDias;
    }

    public String getEstado() {
        return estado;
    }
}
