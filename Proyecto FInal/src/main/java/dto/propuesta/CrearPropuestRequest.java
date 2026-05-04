package dto.propuesta;

public class CrearPropuestRequest {

    private int idProyecto;
    private double monto;
    private int plazoDias;
    private String cartaPresentacion;

    public int getIdProyecto() { return idProyecto; }
    public void setIdProyecto(int idProyecto) { this.idProyecto = idProyecto; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public int getPlazoDias() { return plazoDias; }
    public void setPlazoDias(int plazoDias) { this.plazoDias = plazoDias; }

    public String getCartaPresentacion() {
        return cartaPresentacion;
    }

    public void setCartaPresentacion(String cartaPresentacion) {
        this.cartaPresentacion = cartaPresentacion;
    }
}
