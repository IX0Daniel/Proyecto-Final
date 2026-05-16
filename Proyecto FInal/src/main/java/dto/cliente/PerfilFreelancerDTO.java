package dto.cliente;

public class PerfilFreelancerDTO {
    String biografia;
    String nivel_experiencia;
    double tarifa_hora;


    public PerfilFreelancerDTO(String biografia, String nivel_experiencia, double tarifa_hora) {
        this.biografia = biografia;
        this.nivel_experiencia = nivel_experiencia;
        this.tarifa_hora = tarifa_hora;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getNivel_experiencia() {
        return nivel_experiencia;
    }

    public void setNivel_experiencia(String nivel_experiencia) {
        this.nivel_experiencia = nivel_experiencia;
    }

    public double getTarifa_hora() {
        return tarifa_hora;
    }

    public void setTarifa_hora(double tarifa_hora) {
        this.tarifa_hora = tarifa_hora;
    }
}
