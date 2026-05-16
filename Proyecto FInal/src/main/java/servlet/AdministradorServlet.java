package servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import models.Habilidad;
import service.AdministradorService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/admin/*")
public class AdministradorServlet extends HttpServlet {



    private final Gson gson = new Gson();
    private final AdministradorService service = new AdministradorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo();

        try {

            if ("/categorias".equals(path)) {
                resp.getWriter().print(gson.toJson(service.getCategorias()));

            } else if ("/habilidades".equals(path)) {
                resp.getWriter().print(gson.toJson(service.getHabilidades()));

            } else if ("/usuarios".equals(path)) {
                resp.getWriter().print(gson.toJson(service.getUsuarios()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();

        try {

            BufferedReader br = req.getReader();

            if ("/categorias".equals(path)) {

                Categoria c = gson.fromJson(br, Categoria.class);
                service.crearCategoria(c.getNombre(), c.getDescripcion());

            } else if ("/habilidades".equals(path)) {

                Habilidad h = gson.fromJson(br, Habilidad.class);
                service.crearHabilidad(h.getNombre(), h.getIdCategoria());
            }
            resp.setContentType("application/json");
            resp.getWriter().print("{\"msg\":\"ok\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();

        try {

            if (path.startsWith("/usuarios/")) {

                int id = Integer.parseInt(path.split("/")[2]);
                service.toggleUsuario(id);

            } else if (path.startsWith("/categorias/")) {

                int id = Integer.parseInt(path.split("/")[2]);
                service.toggleCategoria(id);

            } else if (path.startsWith("/habilidades/")) {

                int id = Integer.parseInt(path.split("/")[2]);
                service.toggleHabilidad(id);
            }

            resp.setContentType("application/json");
            resp.getWriter().print("{\"msg\":\"ok\"}");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }


}
