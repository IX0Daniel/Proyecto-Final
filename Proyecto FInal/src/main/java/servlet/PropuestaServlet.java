package servlet;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import dto.propuesta.CrearPropuestRequest;
import dto.propuesta.PropuestaDTO;
import dto.propuesta.PropuestaService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Propuesta;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


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


        System.out.println("Se va a intentar crear una propuesta :V:V:V");
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
            System.out.println("No se pudo");
            resp.sendError(400, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print(e.getMessage());
            System.out.println("No se pudo");

        }

/*        catch (Exception e) {
            resp.sendError(500);


        }*/
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");







        try {

            String idProyectoParam = req.getParameter("idProyecto");

            if (idProyectoParam == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "idProyecto requerido");
                return;
            }

            int idProyecto = Integer.parseInt(idProyectoParam);

            int idUsuario = (int) req.getAttribute("idUsuario");

            List<PropuestaDTO> propuestas = service.porProyecto(idProyecto, idUsuario);

            String json = gson.toJson(propuestas);
            resp.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }




/*

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int idProyecto = Integer.parseInt(req.getParameter("idProyecto"));
            int idUsuario = (int) req.getAttribute("idUsuario");

            System.out.println(idProyecto);
            System.out.println(idUsuario);

            List<PropuestaDTO> propuestas = service.porProyecto(idProyecto, idUsuario);

            resp.getWriter().print(gson.toJson(propuestas));

        }
        catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print(e.getMessage());
        }

/*        catch (Exception e) {
            resp.sendError(500);


        }
*/
}
