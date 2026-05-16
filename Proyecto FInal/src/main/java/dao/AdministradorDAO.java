package dao;

import models.Categoria;
import models.Habilidad;
import models.Usuario;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {



    public Connection getConnection() throws Exception {
        return ConexionDB.getConexion();
    }

    public List<Categoria> getCategorias() throws Exception {

        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT * FROM categoria";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBoolean("activo")
                );

                lista.add(c);
            }


        }

        return lista;
    }

    public List<Habilidad> getHabilidades() throws Exception {

        List<Habilidad> lista = new ArrayList<>();

        String sql = "SELECT * FROM habilidad";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Habilidad h = new Habilidad(
                    rs.getInt("id_habilidad"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("id_categoria"),
                    rs.getBoolean("activo")
                );


                lista.add(h);
            }
        }

        return lista;
    }

    public List<Usuario> getUsuarios() throws Exception {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT id_usuario, username, rol, activo FROM usuario";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setRol(rs.getString("rol"));
                u.setActivo(rs.getBoolean("activo"));
                lista.add(u);
            }
        }

        return lista;
    }

    public void insertCategoria(String nombre, String descripcion) throws Exception {

        String sql = "INSERT INTO categoria(nombre, descripcion, activo) VALUES (?, ?, true)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.executeUpdate();
        }
    }

    public void insertHabilidad(String nombre, int idCategoria) throws Exception {

        String sql = "INSERT INTO habilidad(nombre, id_categoria, activo) VALUES (?, ?, true)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idCategoria);
            ps.executeUpdate();
        }
    }

    public void toggleUsuario(int id) throws Exception {

        String sql = "UPDATE usuario SET activo = NOT activo WHERE id_usuario=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void toggleCategoria(int id) throws Exception {

        String sql = "UPDATE categoria SET activo = NOT activo WHERE id_categoria=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void toggleHabilidad(int id) throws Exception {

        String sql = "UPDATE habilidad SET activo = NOT activo WHERE id_habilidad=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
