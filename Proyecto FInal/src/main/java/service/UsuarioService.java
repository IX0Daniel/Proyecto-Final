package service;

import dao.UsuarioDAO;
import models.Usuario;

public class UsuarioService {


    UsuarioDAO dao = new UsuarioDAO();

    public Usuario obtenerPerfil(int id) throws Exception {
        return dao.obtenerPerfil(id);
    }

    public boolean actualizarPerfil(Usuario u) throws Exception {
        return dao.actualizarPerfil(u);
    }

    public boolean cambiarPassword(int id, String pass) throws Exception {
        return dao.cambiarPassword(id, pass);
    }
}
