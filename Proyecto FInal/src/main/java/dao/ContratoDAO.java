package dao;

import dto.contrato.ContratoListResponse;
import dto.contrato.ContratoResponse;
import models.PropuestaData;
import utils.ConexionDB;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {


    public Connection getConnection() throws Exception {
        return ConexionDB.getConexion();
    }

    public double obtenerSaldo(int idUsuario, Connection con) throws Exception {

        String sql = "SELECT saldo FROM usuario WHERE id_usuario=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble("saldo");
        }
    }

    public void bloquearSaldo(int idUsuario, double monto, Connection con) throws Exception {

        String sql = "UPDATE usuario SET saldo = saldo - ?, saldo_bloqueado = saldo_bloqueado + ? WHERE id_usuario = ? ";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setDouble(2, monto);
            ps.setInt(3, idUsuario);
            ps.executeUpdate();
        }
    }

    public int crearContrato(int idPropuesta, int idProyecto, double monto, double comision, Connection con) throws Exception {

        String sql = "INSERT INTO contrato (id_propuesta, monto, porcentaje_comision) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idPropuesta);
            ps.setDouble(2, monto);
            ps.setDouble(3, comision);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }

        return -1;
    }

    public void actualizarPropuestaEstado(int idPropuesta, String estado, Connection con) throws Exception {

        String sql = "UPDATE propuesta SET estado=? WHERE id_propuesta=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idPropuesta);
            ps.executeUpdate();
        }
    }

    public void rechazarOtrasPropuestas(int idProyecto, int idPropuestaAceptada, Connection con) throws Exception {

        String sql = "UPDATE propuesta SET estado='RECHAZADA' WHERE id_proyecto=? AND id_propuesta<>?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            ps.setInt(2, idPropuestaAceptada);
            ps.executeUpdate();
        }
    }

    public void actualizarProyectoEstado(int idProyecto, Connection con) throws Exception {

        String sql = "UPDATE proyecto SET estado='EN_PROGRESO' WHERE id_proyecto=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            ps.executeUpdate();
        }
    }



    public PropuestaData obtenerDatosPropuesta(int idPropuesta, Connection con) throws Exception {

        String sql = "SELECT p.id_proyecto, p.monto_ofertado, p.estado AS estado_propuesta, pr.estado AS estado_proyecto FROM propuesta p JOIN proyecto pr ON p.id_proyecto = pr.id_proyecto WHERE p.id_propuesta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPropuesta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new PropuestaData(
                        rs.getInt("id_proyecto"),
                        rs.getDouble("monto_ofertado"),
                        rs.getString("estado_propuesta"),
                        rs.getString("estado_proyecto")
                );
            }
        }

        throw new IllegalArgumentException("Propuesta no existe");
    }


    public List<ContratoListResponse> listarContratosFreelancer(int idFreelancer)
            throws Exception {

        List<ContratoListResponse> lista = new ArrayList<>();

        String sql = "SELECT c.id_contrato, p.titulo, u.nombre_completo, c.monto, c.estado FROM contrato c INNER JOIN propuesta pr ON c.id_propuesta = pr.id_propuesta INNER JOIN proyecto p ON pr.id_proyecto = p.id_proyecto INNER JOIN usuario u ON p.id_cliente = u.id_usuario WHERE pr.id_freelancer = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFreelancer);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                lista.add(new ContratoListResponse(
                        rs.getInt("id_contrato"),
                        rs.getString("titulo"),
                        rs.getString("nombre_completo"),
                        rs.getDouble("monto"),
                        rs.getString("estado")
                ));
            }
        }

        return lista;
    }


    public List<ContratoListResponse> listarContratosCliente(int idCliente)
            throws Exception {

        List<ContratoListResponse> lista = new ArrayList<>();

        String sql = "SELECT c.id_contrato, p.titulo, u.nombre_completo AS nombreFreelancer, c.monto, c.estado FROM contrato c INNER JOIN propuesta pr ON c.id_propuesta = pr.id_propuesta INNER JOIN proyecto p ON pr.id_proyecto = p.id_proyecto INNER JOIN usuario u ON pr.id_freelancer = u.id_usuario WHERE p.id_cliente = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                lista.add(new ContratoListResponse(
                        rs.getInt("id_contrato"),
                        rs.getString("titulo"),
                        rs.getString("nombreFreelancer"),
                        rs.getDouble("monto"),
                        rs.getString("estado")
                ));
            }
        }

        return lista;
    }

}
