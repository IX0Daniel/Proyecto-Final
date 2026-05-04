package models;

public class PropuestaData {

    private int idProyecto;
    private double monto;
    private String estadoPropuesta;
    private String estadoProyecto;

    public PropuestaData(int idProyecto, double monto, String estadoPropuesta, String estadoProyecto) {
        this.idProyecto = idProyecto;
        this.monto = monto;
        this.estadoPropuesta = estadoPropuesta;
        this.estadoProyecto = estadoProyecto;
    }

    public int getIdProyecto() {
        return idProyecto;
    }
    public double getMonto() {
        return monto;
    }
    public String getEstadoPropuesta() {
        return estadoPropuesta;
    }
    public String getEstadoProyecto() {
        return estadoProyecto;
    }
}
