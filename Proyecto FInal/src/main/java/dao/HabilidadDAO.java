package dao;

import models.Habilidad;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HabilidadDAO {

    public List<Habilidad> obtenerHablidades() throws Exception{

        String sql = "SELECT * FROM habilidad";

        try(
                Connection connection = ConexionDB.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            List<Habilidad> habilidades =  new ArrayList<>();


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                Habilidad habilidad = new Habilidad(
                        resultSet.getInt("id_habilidad"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getBoolean("activo")
                );

                habilidades.add(habilidad);
            }

            return habilidades;

        }

    }

}
