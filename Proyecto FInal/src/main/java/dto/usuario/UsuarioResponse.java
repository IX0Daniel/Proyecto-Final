package dto.usuario;

public class UsuarioResponse {


    private String nombre;
    private String rol;
    private double saldo;

    public UsuarioResponse(String nombre, String rol, double saldo) {
        this.nombre = nombre;
        this.rol = rol;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public double getSaldo() {
        return saldo;
    }
}
