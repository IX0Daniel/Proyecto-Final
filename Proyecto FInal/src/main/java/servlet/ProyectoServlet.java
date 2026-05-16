package servlet;

import com.google.gson.Gson;
import dto.proyecto.CrearProyectoRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Proyecto;
import service.ProyectoService;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


@WebServlet("/api/proyectos/*")
public class ProyectoServlet extends HttpServlet {


    private final Gson gson = new Gson();
    private final ProyectoService service = new ProyectoService();

    private int getId(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ", "");
        return JwtUtil.obtenerId(token);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            List<Proyecto> lista = service.listarAbiertos();
            resp.getWriter().print(gson.toJson(lista));

        }
        catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print(e.getMessage());
        }

/*        catch (Exception e) {
            resp.sendError(500);


        }*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        System.out.println("Se va a crear un proyecto");
        try {

            int idCliente = getId(req);

            BufferedReader br = req.getReader();
            System.out.println("GSON");


            CrearProyectoRequest dto = gson.fromJson(br, CrearProyectoRequest.class);
            System.out.println(dto.getIdCategoria());
            System.out.println(Integer.toString(dto.getIdCategoria()));

            Proyecto p = new Proyecto();
            p.setTitulo(dto.getTitulo());
            p.setDescripcion(dto.getDescripcion());
            p.setIdCategoria(dto.getIdCategoria());
            p.setPresupuesto(dto.getPresupuesto());
            p.setFechaLimite(dto.getFechaLimite());
            p.setIdCliente(idCliente);


            System.out.println("Habliidades");

            System.out.println(gson.toJson(dto));
            int id = service.crear(p, dto.getHabilidades());

            resp.getWriter().print("{\"id\":" + id + "}");

        }catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print(e.getMessage());
            System.out.println("No se creó");
            resp.sendError(500);

        }
    }
}
