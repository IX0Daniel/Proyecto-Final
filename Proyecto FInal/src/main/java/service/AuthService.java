package service;

import dao.AuthDAO;
import dto.RegisterDTO;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {


    private final AuthDAO dao = new AuthDAO();

    public Usuario login(String username, String password) throws Exception {

        Usuario usuario = dao.buscarPorUsername(username);

        if (usuario == null) {
            return null;
        }

        if (!usuario.isActivo()) {
            return null;
        }

        boolean ok = BCrypt.checkpw(password, usuario.getPassword());

        if (!ok) {
            return null;
        }

        return usuario;
    }

    public boolean register(RegisterDTO dto) throws Exception {

        if (dao.existeUsername(dto.getUsername())) {
            return false;
        }

        if (dao.existeCorreo(dto.getCorreo())) {
            return false;
        }

        String hash = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        Usuario u = new Usuario();

        u.setNombreCompleto(dto.getNombreCompleto());
        u.setUsername(dto.getUsername());
        u.setPassword(hash);
        u.setCorreo(dto.getCorreo());
        u.setTelefono(dto.getTelefono());
        u.setDireccion(dto.getDireccion());
        u.setCui(dto.getCui());
        u.setFechaNacimiento(dto.getFechaNacimiento());
        u.setRol(dto.getRol());

        return dao.insertar(u);
    }


}
