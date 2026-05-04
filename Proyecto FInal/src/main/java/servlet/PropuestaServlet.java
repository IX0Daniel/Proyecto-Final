package servlet;

import com.google.gson.Gson;
import dto.propuesta.CrearPropuestRequest;
import dto.propuesta.PropuestaService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Propuesta;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/api/propuestas/*")
public class PropuestaServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final PropuestaService service = new PropuestaService();

    private int getId(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ", "");
        return JwtUtil.obtenerId(token);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();

        try {

            int idFreelancer = getId(req);

            if ("/".equals(path) || path == null) {

                BufferedReader br = req.getReader();
                CrearPropuestRequest dto = gson.fromJson(br, CrearPropuestRequest.class);

                Propuesta p = new Propuesta();
                p.setIdProyecto(dto.getIdProyecto());
                p.setMonto(dto.getMonto());
                p.setPlazoDias(dto.getPlazoDias());
                p.setIdFreelancer(idFreelancer);

                boolean ok = service.enviar(p);

                resp.getWriter().print(ok);

            }

        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print(e.getMessage());
        }

/*        catch (Exception e) {
            resp.sendError(500);


        }*/
    }
}
