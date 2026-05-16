package servlet;

import com.google.gson.Gson;
import dto.cliente.PerfilClienteDTO;
import dto.cliente.PerfilFreelancerDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PerfilService;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/perfil/*")
public class PerfilServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private PerfilService service = new PerfilService();



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String token = req.getHeader("Authorization").replace("Bearer ", "");
        int idUsuario = JwtUtil.obtenerId(token);
        String rol = JwtUtil.obtenerRol(token);

        String path = req.getPathInfo();

        try {

            BufferedReader br = req.getReader();

            if ("/cliente".equals(path)) {

                PerfilClienteDTO dto = gson.fromJson(br, PerfilClienteDTO.class);

                service.crearPerfilCliente(idUsuario, dto);

            } else if ("/freelancer".equals(path)) {

                PerfilFreelancerDTO dto = gson.fromJson(br, PerfilFreelancerDTO.class);

                service.crearPerfilFreelancer(idUsuario, dto);
            }

            resp.setContentType("application/json");
            resp.getWriter().print("{\"message\":\"OK\"}");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String token = req.getHeader("Authorization").replace("Bearer ", "");
        int idUsuario = JwtUtil.obtenerId(token);
        String rol = JwtUtil.obtenerRol(token);

        try {

            boolean tienePerfil = false;

            if ("cliente".equals(rol)) {
                tienePerfil = service.existeCliente(idUsuario);
            } else if ("freelancer".equals(rol)) {
                tienePerfil = service.existeFreelancer(idUsuario);
            }

            resp.setContentType("application/json");
            resp.getWriter().print("{\"tienePerfil\":" + tienePerfil + "}");

        } catch (Exception e) {
            resp.sendError(500);
        }
    }

}
