package dao;

import models.Usuario;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioDAO {


    public Usuario obtenerPerfil(int idUsuario) throws Exception {

        String sql = "SELECT id_usuario,nombre_completo,username,correo, telefono,direccion,rol,saldo,saldo_bloqueado FROM usuario WHERE id_usuario = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();

                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setUsername(rs.getString("username"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                u.setDireccion(rs.getString("direccion"));
                u.setRol(rs.getString("rol"));
                u.setSaldo(rs.getDouble("saldo"));
                u.setSaldoBloqueado(rs.getDouble("saldo_bloqueado"));

                return u;
            }
        }

        return null;
    }

    public boolean actualizarPerfil(Usuario u) throws Exception {

        String sql = "UPDATE usuario SET nombre_completo=?, correo=?, telefono=?, direccion=? WHERE id_usuario=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getDireccion());
            ps.setInt(5, u.getIdUsuario());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean cambiarPassword(int idUsuario, String nueva) throws Exception {

        String sql = "UPDATE usuario SET password=? WHERE id_usuario=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nueva);
            ps.setInt(2, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }


    public boolean registrarUsuario(Usuario u) throws Exception {

        String sql = "INSERT INTO usuario " +
                "(nombre_completo, username, password, correo, telefono, direccion, cui, fecha_nacimiento, rol) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha = formato.parse(u.getFechaNacimiento());
            java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getDireccion());
            ps.setString(7, u.getCui());
            ps.setDate(8, sqlDate);
            ps.setString(9, u.getRol());

            return ps.executeUpdate() > 0;
        }
    }


}
