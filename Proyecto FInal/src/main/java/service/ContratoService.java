package service;

import dao.ContratoDAO;
import dto.contrato.ContratoListResponse;
import dto.contrato.ContratoResponse;
import models.PropuestaData;
import utils.ConexionDB;

import java.sql.Connection;
import java.util.List;

public class ContratoService {


    private final ContratoDAO dao = new ContratoDAO();

    public int crearContrato(int idCliente, int idPropuesta) throws Exception {

        Connection con = dao.getConnection();

        try {

            con.setAutoCommit(false);



            PropuestaData data = dao.obtenerDatosPropuesta(idPropuesta, con);

            int idProyecto = data.getIdProyecto();
            double monto = data.getMonto();


            if (!"PENDIENTE".equals(data.getEstadoPropuesta())) {
                throw new IllegalArgumentException("La propuesta no está disponible");
            }

            if (!"ABIERTO".equals(data.getEstadoProyecto())) {
                throw new IllegalArgumentException("El proyecto no está abierto");
            }


            double saldo = dao.obtenerSaldo(idCliente, con);

            if (saldo < monto) {
                throw new IllegalArgumentException("Saldo insuficiente");
            }

            dao.bloquearSaldo(idCliente, monto, con);

            double comision = 10.0;

            int idContrato = dao.crearContrato(
                    idPropuesta,
                    idProyecto,
                    monto,
                    comision,
                    con
            );

            dao.actualizarPropuestaEstado(idPropuesta, "ACEPTADA", con);
            dao.rechazarOtrasPropuestas(idProyecto, idPropuesta, con);
            dao.actualizarProyectoEstado(idProyecto, con);

            con.commit();

            return idContrato;

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();
        }
    }


    public List<ContratoListResponse> listarContratosFreelancer(int idFreelancer)
            throws Exception {

        return dao.listarContratosFreelancer(idFreelancer);
    }

    public List<ContratoListResponse> listarContratosCliente(int idCliente)
            throws Exception {

        return dao.listarContratosCliente(idCliente);
    }

}
