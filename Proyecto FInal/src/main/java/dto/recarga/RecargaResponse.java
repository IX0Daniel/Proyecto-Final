package dto.recarga;

public class RecargaResponse {

    private int idRecarga;
    private double monto;
    private String fecha;

    public RecargaResponse(int idRecarga, double monto, String fecha) {
        this.idRecarga = idRecarga;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getIdRecarga() { return idRecarga; }
    public double getMonto() { return monto; }
    public String getFecha() { return fecha; }
}
