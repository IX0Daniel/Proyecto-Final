package dao;

import models.Categoria;
import utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {


    public List<Categoria> obtenerCategorias() throws Exception {

        String sql = "SELECT * FROM categoria";
        List<Categoria> categorias = new ArrayList<>();


        try(
                Connection connection = ConexionDB.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                Categoria categoria = new Categoria(
                        resultSet.getInt("id_categoria"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getBoolean("activo")
                );

                categorias.add(categoria);
            }
            return categorias;

        }
    }


}
