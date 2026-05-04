package dao;

import models.Propuesta;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PropuestaDAO {

    public boolean existePropuesta(int idProyecto, int idFreelancer) throws Exception {

        String sql = "SELECT COUNT(*) FROM propuesta WHERE id_proyecto=? AND id_freelancer=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProyecto);
            ps.setInt(2, idFreelancer);

            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;
        }
    }

    public boolean insertar(Propuesta p) throws Exception {

        //String sql = "INSERT INTO propuesta (id_proyecto, id_freelancer, monto_ofertado, plazo_dias, estado) VALUES (?, ?, ?, ?, 'PENDIENTE')";

        String sql = "INSERT INTO propuesta (id_proyecto, id_freelancer, monto_ofertado, plazo_dias, carta_presentacion, estado) VALUES (?, ?, ?, ?, ?, 'PENDIENTE')";


        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdProyecto());
            ps.setInt(2, p.getIdFreelancer());
            ps.setDouble(3, p.getMonto());
            ps.setInt(4, p.getPlazoDias());
            ps.setString(5, p.getCartaPresentacion());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Propuesta> obtenerPorProyecto(int idProyecto) throws Exception {

        String sql = "SELECT * FROM propuesta WHERE id_proyecto=?";

        List<Propuesta> lista = new ArrayList<>();

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProyecto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Propuesta p = new Propuesta();

                p.setId(rs.getInt("id_propuesta"));
                p.setIdProyecto(rs.getInt("id_proyecto"));
                p.setIdFreelancer(rs.getInt("id_freelancer"));
                p.setMonto(rs.getDouble("monto_ofertado"));
                p.setPlazoDias(rs.getInt("plazo_dias"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }
        }

        return lista;
    }

    public boolean cambiarEstado(int idPropuesta, String estado) throws Exception {

        String sql = "UPDATE propuesta SET estado=? WHERE id_propuesta=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idPropuesta);

            return ps.executeUpdate() > 0;
        }
    }

    public List<Propuesta> obtenerPorFreelancer(int idFreelancer) throws Exception {

        String sql = "SELECT * FROM propuesta WHERE id_freelancer=?";

        List<Propuesta> lista = new ArrayList<>();

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFreelancer);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Propuesta p = new Propuesta();

                p.setId(rs.getInt("id_propuesta"));
                p.setIdProyecto(rs.getInt("id_proyecto"));
                p.setMonto(rs.getDouble("monto_ofertado"));
                p.setPlazoDias(rs.getInt("plazo_dias"));
                p.setEstado(rs.getString("estado"));
                p.setCartaPresentacion(rs.getString("carta_presentacion"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }
        }

        return lista;
    }

}
