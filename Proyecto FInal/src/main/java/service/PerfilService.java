package service;

import dao.PerfilDAO;
import dto.cliente.PerfilClienteDTO;
import dto.cliente.PerfilFreelancerDTO;

public class PerfilService {

    private PerfilDAO dao = new PerfilDAO();

    public void crearPerfilCliente(int id, PerfilClienteDTO dto) throws Exception {
        dao.insertCliente(id, dto);
    }

    public void crearPerfilFreelancer(int id, PerfilFreelancerDTO dto) throws Exception {
        dao.insertFreelancer(id, dto);
    }


    public boolean existeCliente(int id) throws Exception {
        return dao.existeCliente(id);
    }

    public boolean existeFreelancer(int id) throws Exception {
        return dao.existeFreelancer(id);
    }
}
