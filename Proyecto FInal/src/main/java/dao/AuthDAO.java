package dao;

import models.Usuario;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDAO {



    public Usuario buscarPorUsername(String username) throws Exception {

        String sql = "SELECT id_usuario, username, password, rol, activo FROM usuario WHERE username = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();

                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                u.setActivo(rs.getBoolean("activo"));

                return u;
            }
        }

        return null;
    }

    // ===============================
    // VERIFICAR USERNAME EXISTENTE
    // ===============================
    public boolean existeUsername(String username) throws Exception {

        String sql = "SELECT COUNT(*) FROM usuario WHERE username = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;
        }
    }

    // ===============================
    // VERIFICAR CORREO EXISTENTE
    // ===============================
    public boolean existeCorreo(String correo) throws Exception {

        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);

            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;
        }
    }

    // ===============================
    // INSERTAR USUARIO
    // ===============================
    public boolean insertar(Usuario u) throws Exception {

        String sql = "INSERT INTO usuario (nombre_completo, username, password, correo, telefono, direccion, cui, fecha_nacimiento, rol, activo, saldo, saldo_bloqueado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE, 0, 0)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getDireccion());
            ps.setString(7, u.getCui());
            ps.setString(8, u.getFechaNacimiento());
            ps.setString(9, u.getRol());

            return ps.executeUpdate() > 0;
        }
    }

}
