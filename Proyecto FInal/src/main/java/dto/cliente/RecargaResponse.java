package dto.cliente;

public class RecargaResponse {
    private double saldo;

    public RecargaResponse(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }
}
