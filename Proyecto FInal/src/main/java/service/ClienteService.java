package service;

import dao.ClienteDAO;

public class ClienteService {


    private final ClienteDAO dao = new ClienteDAO();

    public boolean completarPerfil(int idUsuario, String telefono, String direccion) throws Exception {
        return dao.completarPerfil(idUsuario, telefono, direccion);
    }

    public double recargar(int idUsuario, double monto) throws Exception {

        if (monto <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }

        dao.recargarSaldo(idUsuario, monto);
        dao.registrarRecarga(idUsuario, monto);

        return dao.obtenerSaldo(idUsuario);
    }
}
