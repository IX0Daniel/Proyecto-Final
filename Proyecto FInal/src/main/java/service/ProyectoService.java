package service;

import dao.ProyectoDAO;
import models.Proyecto;

import java.util.List;

public class ProyectoService {

    private final ProyectoDAO dao = new ProyectoDAO();

    public int crear(Proyecto p, List<Integer> habilidades) throws Exception {


        System.out.println("Agregando HAbillidades");
        int id = dao.crear(p);

        if (id > 0 && habilidades != null) {

            System.out.println("2Se van a agregar las habilidades");

            for (Integer valor: habilidades) {

                System.out.println(valor);


            }


            dao.insertarHabilidades(id, habilidades);
        }

        return id;
    }

    public List<Proyecto> listarAbiertos() throws Exception {
        return dao.listarAbiertos();
    }

    public boolean actualizar(Proyecto p) throws Exception {
        return dao.actualizar(p);
    }

    public boolean cancelar(int id) throws Exception {
        return dao.cancelar(id);
    }
}
