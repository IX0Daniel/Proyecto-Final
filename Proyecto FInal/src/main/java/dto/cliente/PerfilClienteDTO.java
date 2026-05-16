package dto.cliente;

public class PerfilClienteDTO {

    String descripcion;
    String sector;
    String sitio_web;

    public PerfilClienteDTO(String descripcion, String sector, String sitio_web) {
        this.descripcion = descripcion;
        this.sector = sector;
        this.sitio_web = sitio_web;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSitio_web() {
        return sitio_web;
    }

    public void setSitio_web(String sitio_web) {
        this.sitio_web = sitio_web;
    }
}
