package dto.contrato;

public class ContratoResponse {

    private int idContrato;
    private String estado;

    public ContratoResponse(int idContrato, String estado) {
        this.idContrato = idContrato;
        this.estado = estado;
    }

    public int getIdContrato() { return idContrato; }
    public String getEstado() { return estado; }

}
