package service;

import dao.UsuarioDAO;
import dto.registro.RegisterDTO;
import dto.usuario.UsuarioResponse;
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

    public void registrar(RegisterDTO dto) throws Exception {


        if (dto.getUsername() == null || dto.getPassword() == null) {
            throw new IllegalArgumentException("Datos incompletos");
        }

        String password = dto.getPassword();

        Usuario user = new Usuario();
        user.setNombreCompleto(dto.getNombreCompleto());
        user.setUsername(dto.getUsername());
        user.setPassword(password);
        user.setCorreo(dto.getCorreo());
        user.setTelefono(dto.getTelefono());
        user.setDireccion(dto.getDireccion());
        user.setCui(dto.getCui());
        user.setRol(dto.getRol());
        user.setFechaNacimiento(dto.getFechaNacimiento());

        dao.registrarUsuario(user);
    }


    public UsuarioResponse obtenerUsuario(int id) throws Exception {

        Usuario u = dao.obtenerPerfil(id);

        if (u == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        return new UsuarioResponse(
                u.getNombreCompleto(),
                u.getRol(),
                u.getSaldo()
        );
    }



}
