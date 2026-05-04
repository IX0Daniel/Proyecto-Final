package models;

public class PagoData {

    private int idCliente;
    private int idFreelancer;

    public PagoData(int idCliente, int idFreelancer) {
        this.idCliente = idCliente;
        this.idFreelancer = idFreelancer;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public int getIdFreelancer() {
        return idFreelancer;
    }
}
