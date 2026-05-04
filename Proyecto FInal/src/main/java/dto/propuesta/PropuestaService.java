package dto.propuesta;

import dao.PropuestaDAO;
import models.Propuesta;

import java.util.List;

public class PropuestaService {


    private final PropuestaDAO dao = new PropuestaDAO();

    public boolean enviar(Propuesta p) throws Exception {

        return dao.insertar(p);
    }

    public List<Propuesta> porProyecto(int idProyecto) throws Exception {
        return dao.obtenerPorProyecto(idProyecto);
    }

    public List<Propuesta> misPropuestas(int idFreelancer) throws Exception {
        return dao.obtenerPorFreelancer(idFreelancer);
    }

    public boolean cambiarEstado(int idPropuesta, String estado) throws Exception {
        return dao.cambiarEstado(idPropuesta, estado);
    }

}
