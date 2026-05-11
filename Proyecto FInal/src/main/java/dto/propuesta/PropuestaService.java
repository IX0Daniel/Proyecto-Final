package dto.propuesta;

import dao.PropuestaDAO;
import models.Propuesta;

import java.util.List;

public class PropuestaService {


    private final PropuestaDAO dao = new PropuestaDAO();

    public boolean enviar(Propuesta p) throws Exception {

        return dao.insertar(p);
    }

    public List<PropuestaDTO> porProyecto(int idProyecto, int idUsuario) throws Exception {
        return dao.listarPorProyecto(idProyecto, idUsuario);
    }

    public List<Propuesta> misPropuestas(int idFreelancer) throws Exception {
        return dao.obtenerPorFreelancer(idFreelancer);
    }

    public boolean cambiarEstado(int idPropuesta, String estado) throws Exception {
        return dao.cambiarEstado(idPropuesta, estado);
    }

}
