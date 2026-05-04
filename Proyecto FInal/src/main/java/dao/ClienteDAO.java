package dao;

import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDAO {

    public boolean completarPerfil(int idUsuario, String telefono, String direccion) throws Exception {

        String sql = "UPDATE usuario SET telefono = ?, direccion = ? WHERE id_usuario = ? ";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, telefono);
            ps.setString(2, direccion);
            ps.setInt(3, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean recargarSaldo(int idUsuario, double monto) throws Exception {

        String sql = "UPDATE usuario SET saldo = saldo + ? WHERE id_usuario = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, monto);
            ps.setInt(2, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean registrarRecarga(int idUsuario, double monto) throws Exception {

        String sql = "INSERT INTO recarga (id_usuario, monto) VALUES (?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setDouble(2, monto);

            return ps.executeUpdate() > 0;
        }
    }

    public double obtenerSaldo(int idUsuario) throws Exception {

        String sql = "SELECT saldo FROM usuario WHERE id_usuario = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        }

        return 0;
    }
}
