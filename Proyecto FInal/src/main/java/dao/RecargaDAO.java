package dao;

import dto.recarga.RecargaResponse;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecargaDAO {

    public Connection getConnection() throws Exception {
        return ConexionDB.getConexion();
    }

    public void insertarRecarga(int idUsuario, double monto, Connection con) throws Exception {

        String sql = "INSERT INTO recarga_saldo (id_usuario, monto) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setDouble(2, monto);
            ps.executeUpdate();
        }
    }

    public void actualizarSaldo(int idUsuario, double monto, Connection con) throws Exception {

        String sql = "UPDATE usuario SET saldo = saldo + ? WHERE id_usuario = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        }
    }

    public List<RecargaResponse> listar(int idUsuario, Connection con) throws Exception {

        List<RecargaResponse> lista = new ArrayList<>();

        String sql = "SELECT id_recarga, monto, fecha FROM recarga_saldo WHERE id_usuario = ? ORDER BY fecha DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new RecargaResponse(
                        rs.getInt("id_recarga"),
                        rs.getDouble("monto"),
                        rs.getString("fecha")
                ));
            }
        }

        return lista;
    }
}
