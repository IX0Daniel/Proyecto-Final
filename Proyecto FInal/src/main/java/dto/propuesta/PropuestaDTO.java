package dto.propuesta;

public class PropuestaDTO {

    int id_propuesta;
    double monto_ofertado;
    int plazo_dias;
    private String carta_presentacion;
    private String estado;
    private String nombre_freelancer;


    public int getIdPpropuesta() {
        return id_propuesta;
    }

    public void setIdPropuesta(int id_propuesta) {
        this.id_propuesta = id_propuesta;
    }

    public double getMontoOfertado() {
        return monto_ofertado;
    }

    public void setMontoOfertado(double monto_ofertado) {
        this.monto_ofertado = monto_ofertado;
    }

    public int getPlazo_dias() {
        return plazo_dias;
    }

    public void setPlazoDias(int plazo_dias) {
        this.plazo_dias = plazo_dias;
    }

    public String getCarta_presentacion() {
        return carta_presentacion;
    }

    public void setCartaPresentacion(String carta_presentacion) {
        this.carta_presentacion = carta_presentacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_freelancer() {
        return nombre_freelancer;
    }

    public void setNombreFreelancer(String nombre_freelancer) {
        this.nombre_freelancer = nombre_freelancer;
    }
}
