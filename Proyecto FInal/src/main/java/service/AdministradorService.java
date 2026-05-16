package service;

import dao.AdministradorDAO;
import models.Categoria;
import models.Habilidad;
import models.Usuario;

import java.util.List;

public class AdministradorService {


    private final AdministradorDAO dao = new AdministradorDAO();

    public List<Categoria> getCategorias() throws Exception {
        return dao.getCategorias();
    }

    public List<Habilidad> getHabilidades() throws Exception {
        return dao.getHabilidades();
    }

    public List<Usuario> getUsuarios() throws Exception {
        return dao.getUsuarios();
    }

    public void crearCategoria(String nombre, String descripcion) throws Exception {
        dao.insertCategoria(nombre, descripcion);
    }

    public void crearHabilidad(String nombre, int idCategoria) throws Exception {
        dao.insertHabilidad(nombre, idCategoria);
    }

    public void toggleUsuario(int id) throws Exception {
        dao.toggleUsuario(id);
    }

    public void toggleCategoria(int id) throws Exception {
        dao.toggleCategoria(id);
    }

    public void toggleHabilidad(int id) throws Exception {
        dao.toggleHabilidad(id);
    }
}

