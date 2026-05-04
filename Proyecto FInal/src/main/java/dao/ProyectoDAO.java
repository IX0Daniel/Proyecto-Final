package dao;

import models.Proyecto;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

    public int crear(Proyecto p) throws Exception {

        String sql = "INSERT INTO proyecto (titulo, descripcion, id_categoria, presupuesto, fecha_limite, estado, id_cliente) VALUES (?, ?, ?, ?, ?, 'ABIERTO', ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getIdCategoria());
            ps.setDouble(4, p.getPresupuesto());
            ps.setString(5, p.getFechaLimite());
            ps.setInt(6, p.getIdCliente());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }

        return -1;
    }

    public void insertarHabilidades(int idProyecto, List<Integer> habilidades) throws Exception {

        String sql = "INSERT INTO proyecto_habilidad VALUES (?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int idHab : habilidades) {



                ps.setInt(1, idProyecto);
                ps.setInt(2, idHab);
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    public List<Proyecto> listarAbiertos() throws Exception {

        String sql = "SELECT * FROM proyecto WHERE estado = 'ABIERTO'";

        List<Proyecto> lista = new ArrayList<>();

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Proyecto p = new Proyecto();

                p.setId(rs.getInt("id_proyecto"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPresupuesto(rs.getDouble("presupuesto"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }
        }

        return lista;
    }

    public boolean actualizar(Proyecto p) throws Exception {

        String sql = "UPDATE proyecto SET titulo=?, descripcion=?, presupuesto=?, fecha_limite=? WHERE id_proyecto=? AND estado='ABIERTO'";


        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPresupuesto());
            ps.setString(4, p.getFechaLimite());
            ps.setInt(5, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean cancelar(int idProyecto) throws Exception {

        String sql = "UPDATE proyecto SET estado='CANCELADO' WHERE id_proyecto=? AND estado='ABIERTO'";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProyecto);

            return ps.executeUpdate() > 0;
        }
    }
}
