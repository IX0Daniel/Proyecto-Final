package dao;

import dto.entrega.EntregaResponse;
import models.EntregaData;
import models.PagoData;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public Connection getConnection() throws Exception {
        return ConexionDB.getConexion();
    }

    public boolean insertarEntrega(int idContrato, String desc, String url, Connection con) throws Exception {

        String sql = "INSERT INTO entrega (id_contrato, descripcion, url_archivo) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idContrato);
            ps.setString(2, desc);
            ps.setString(3, url);
            return ps.executeUpdate() > 0;
        }
    }

    public EntregaData obtenerEntrega(int idEntrega, Connection con) throws Exception {

        String sql = "SELECT e.id_contrato, c.monto, c.porcentaje_comision FROM entrega e JOIN contrato c ON e.id_contrato = c.id_contrato WHERE e.id_entrega = ?";

        String sql1 ="";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEntrega);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new EntregaData(
                        rs.getInt("id_contrato"),
                        rs.getDouble("monto"),
                        rs.getDouble("porcentaje_comision")
                );
            }
        }

        throw new IllegalArgumentException("Entrega no encontrada");
    }

    public void aprobarEntrega(int idEntrega, Connection con) throws Exception {

        String sql = "UPDATE entrega SET estado='APROBADA' WHERE id_entrega=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEntrega);
            ps.executeUpdate();
        }
    }

    public void completarContrato(int idContrato, Connection con) throws Exception {

        String sql = "UPDATE contrato SET estado='COMPLETADO', fecha_fin=NOW() WHERE id_contrato=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idContrato);
            ps.executeUpdate();
        }
    }

    public void liberarDinero(int idContrato, double monto, double comision, Connection con) throws Exception {

        double pagoFreelancer = monto - (monto * (comision / 100));


        PagoData data = obtenerDatosPago(idContrato, con);

        // pagar freelancer
        String pago = "UPDATE usuario SET saldo = saldo + ? WHERE id_usuario = ?";

        try (PreparedStatement ps = con.prepareStatement(pago)) {
            ps.setDouble(1, pagoFreelancer);
            ps.setInt(2, data.getIdFreelancer());
            ps.executeUpdate();
        }

        // liberar saldo bloqueado cliente
        String liberar = "UPDATE usuario SET saldo_bloqueado = saldo_bloqueado - ? WHERE id_usuario = ?";

        try (PreparedStatement ps = con.prepareStatement(liberar)) {
            ps.setDouble(1, monto);
            ps.setInt(2, data.getIdCliente());
            ps.executeUpdate();
        }
    }


    public int obtenerClienteDelContrato(int idContrato, Connection con) throws Exception {

        String sql = "SELECT pr.id_cliente FROM contrato c JOIN propuesta p ON c.id_propuesta = p.id_propuesta JOIN proyecto pr ON p.id_proyecto = pr.id_proyecto WHERE c.id_contrato = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idContrato);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new IllegalArgumentException("Contrato inválido");
    }

    public PagoData obtenerDatosPago(int idContrato, Connection con) throws Exception {

        String sql = "SELECT pr.id_cliente, p.id_freelancer FROM contrato c JOIN propuesta p ON c.id_propuesta = p.id_propuesta JOIN proyecto pr ON p.id_proyecto = pr.id_proyecto WHERE c.id_contrato = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idContrato);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PagoData(
                        rs.getInt("id_cliente"),
                        rs.getInt("id_freelancer")
                );
            }
        }

        throw new IllegalArgumentException("Contrato inválido");
    }
    public List<EntregaResponse> listarPorContrato(int idContrato, Connection con) throws Exception {

        List<EntregaResponse> lista = new ArrayList<>();

        String sql = "SELECT id_entrega, fecha_entrega, descripcion, url_archivo, estado, motivo_rechazo FROM entrega WHERE id_contrato = ? ORDER BY id_entrega DESC ";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idContrato);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new EntregaResponse(
                        rs.getInt("id_entrega"),
                        rs.getString("fecha_entrega"),
                        rs.getString("descripcion"),
                        rs.getString("url_archivo"),
                        rs.getString("estado"),
                        rs.getString("motivo_rechazo")
                ));
            }
        }

        return lista;
    }


    public void rechazarEntrega(int idEntrega, String motivo, Connection con) throws Exception {

        String sql = "UPDATE entrega SET estado='RECHAZADA', motivo_rechazo=? WHERE id_entrega=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, motivo);
            ps.setInt(2, idEntrega);
            ps.executeUpdate();
        }
    }

    public void volverAEnProgreso(int idContrato, Connection con) throws Exception {

        String sql = "UPDATE proyecto p JOIN propuesta pr ON p.id_proyecto = pr.id_proyecto JOIN contrato c ON pr.id_propuesta = c.id_propuesta SET p.estado = 'EN_PROGRESO'WHERE c.id_contrato = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idContrato);
            ps.executeUpdate();
        }
    }
}
