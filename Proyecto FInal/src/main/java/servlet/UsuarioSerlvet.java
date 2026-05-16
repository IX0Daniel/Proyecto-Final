package servlet;

import com.google.gson.Gson;
import dto.registro.RegisterDTO;

import dto.usuario.UsuarioResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Usuario;
import service.UsuarioService;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/api/usuarios/*")
public class UsuarioSerlvet extends HttpServlet {


    private final Gson gson = new Gson();
    UsuarioService service = new UsuarioService();

    private int getId(HttpServletRequest request) {

        String auth = request.getHeader("Authorization");
        String token = auth.replace("Bearer ", "");

        int id = JwtUtil.obtenerId(token);

        return id;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {

            int id = getId(req);
            String path = req.getPathInfo();

            resp.setContentType("application/json");

            if ("/perfil".equals(path)) {

                Usuario usuario = service.obtenerPerfil(id);
                resp.getWriter().print(gson.toJson(usuario));

            } else if ("/saldo".equals(path)) {

                Usuario usuario = service.obtenerPerfil(id);
                resp.getWriter().print(gson.toJson(usuario));

            } else if ("/informacion".equals(path)) {

                UsuarioResponse usuario = service.obtenerUsuario(id);
                resp.getWriter().print(gson.toJson(usuario));

            }

        } catch (Exception e) {
            resp.sendError(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {

            int id = getId(req);
            String path = req.getPathInfo();

            if ("/perfil".equals(path)) {

                BufferedReader br = req.getReader();
                Usuario u = gson.fromJson(br, Usuario.class);
                u.setIdUsuario(id);

                boolean ok = service.actualizarPerfil(u);

                resp.getWriter().print(ok);

            } else if ("/cambiar-password".equals(path)) {

                BufferedReader br = req.getReader();
                PasswordDTO dto = gson.fromJson(br, PasswordDTO.class);

                boolean ok = service.cambiarPassword(id, dto.nuevaPassword);

                resp.getWriter().print(ok);
            }

        } catch (Exception e) {
            resp.sendError(500);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {

            String path = req.getPathInfo();

            if (path == null || "/register".equals(path)) {

                BufferedReader br = req.getReader();
                RegisterDTO dto = gson.fromJson(br, RegisterDTO.class);

                service.registrar(dto);

                resp.getWriter().print("OK");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        }
    }



    class PasswordDTO {
        String nuevaPassword;
    }


}
