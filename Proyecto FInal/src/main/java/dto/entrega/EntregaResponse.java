package dto.entrega;

public class EntregaResponse {

    private int id_entrega;
    private String fecha_entrega;
    private String descripcion;
    private String url_archivo;
    private String estado;
    private String motivo_rechazo;

    public EntregaResponse(int id_entrega, String fecha_entrega, String descripcion, String url_archivo, String estado, String motivo_rechazo) {

        this.id_entrega = id_entrega;
        this.fecha_entrega = fecha_entrega;
        this.descripcion = descripcion;
        this.url_archivo = url_archivo;
        this.estado = estado;
        this.motivo_rechazo = motivo_rechazo;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl_archivo() {
        return url_archivo;
    }

    public void setUrl_archivo(String url_archivo) {
        this.url_archivo = url_archivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo_rechazo() {
        return motivo_rechazo;
    }

    public void setMotivo_rechazo(String motivo_rechazo) {
        this.motivo_rechazo = motivo_rechazo;
    }
}

