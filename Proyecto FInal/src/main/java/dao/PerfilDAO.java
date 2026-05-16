package dao;

import dto.cliente.PerfilClienteDTO;
import dto.cliente.PerfilFreelancerDTO;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PerfilDAO {

    public void insertCliente(int id, PerfilClienteDTO dto) throws Exception {

        String sql = "INSERT INTO usuario_cliente (id_usuario, descripcion, sector, sitio_web) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, dto.getDescripcion());
            ps.setString(3, dto.getSector());
            ps.setString(4, dto.getSitio_web());

            ps.executeUpdate();
        }
    }

    public void insertFreelancer(int id, PerfilFreelancerDTO dto) throws Exception {

        String sql = "INSERT INTO usuario_freelancer (id_usuario, biografia, nivel_experiencia, tarifa_hora) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, dto.getBiografia());
            ps.setString(3, dto.getNivel_experiencia());
            ps.setDouble(4, dto.getTarifa_hora());

            ps.executeUpdate();
        }
    }


    public boolean existeCliente(int id) throws Exception {

        String sql = "SELECT 1 FROM usuario_cliente WHERE id_usuario=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }
    public boolean existeFreelancer(int id) throws Exception {

        String sql = "SELECT 1 FROM usuario_freelancer WHERE id_usuario=?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }
}
