package models;

public class EntregaData {
    private int idContrato;
    private double monto;
    private double comision;

    public EntregaData(int idContrato, double monto, double comision) {
        this.idContrato = idContrato;
        this.monto = monto;
        this.comision = comision;
    }

    public int getIdContrato() { return idContrato; }
    public double getMonto() { return monto; }
    public double getComision() { return comision; }
}
