package service;

import dao.EntregaDAO;
import models.EntregaData;

import java.sql.Connection;

public class EntregaService {

    private final EntregaDAO dao = new EntregaDAO();

    public void crearEntrega(int idContrato, String desc, String url) throws Exception {

        try (Connection con = dao.getConnection()) {
            dao.insertarEntrega(idContrato, desc, url, con);
        }
    }

    public void aprobar(int idEntrega, int userId, String rol) throws Exception {

        if (!"cliente".equals(rol)) {
            throw new IllegalArgumentException("Solo cliente puede aprobar");
        }



        Connection con = dao.getConnection();

        try {

            con.setAutoCommit(false);

            EntregaData data = dao.obtenerEntrega(idEntrega, con);


            int idClienteReal = dao.obtenerClienteDelContrato(data.getIdContrato(), con);

            if (idClienteReal != userId) {
                throw new IllegalArgumentException("No autorizado");
            }



            dao.aprobarEntrega(idEntrega, con);


            if (data.getMonto() <= 0) {
                throw new IllegalStateException("Monto inválido");
            }


            dao.liberarDinero(
                    data.getIdContrato(),
                    data.getMonto(),
                    data.getComision(),
                    con
            );

            dao.completarContrato(data.getIdContrato(), con);

            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }
}
