package dto.contrato;

public class ContratoListResponse {


    private int idContrato;
    private String tituloProyecto;
    private String nombreCliente;
    private double monto;
    private String estado;

    public ContratoListResponse(
            int idContrato,
            String tituloProyecto,
            String nombreCliente,
            double monto,
            String estado
    ) {
        this.idContrato = idContrato;
        this.tituloProyecto = tituloProyecto;
        this.nombreCliente = nombreCliente;
        this.monto = monto;
        this.estado = estado;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }


}
