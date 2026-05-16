package service;

import dao.RecargaDAO;
import dto.recarga.RecargaResponse;

import java.sql.Connection;
import java.util.List;

public class RecargaService {



    private final RecargaDAO dao = new RecargaDAO();

    public void recargar(int idUsuario, double monto) throws Exception {

        if (monto <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }

        Connection con = dao.getConnection();

        try {
            con.setAutoCommit(false);

            dao.insertarRecarga(idUsuario, monto, con);
            dao.actualizarSaldo(idUsuario, monto, con);

            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }

    public List<RecargaResponse> historial(int idUsuario) throws Exception {

        try (Connection con = dao.getConnection()) {
            return dao.listar(idUsuario, con);
        }
    }

}
