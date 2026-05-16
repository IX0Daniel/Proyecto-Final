package servlet;

import com.google.gson.Gson;
import dto.registro.RegisterDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.Usuario;
import service.AuthService;
import utils.JwtUtil;


@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {



    private final Gson gson = new Gson();
    private final AuthService service = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {

            if ("/login".equals(path)) {
                login(req, resp);

            } else if ("/register".equals(path)) {
                register(req, resp);

            } else if ("/logout".equals(path)) {
                logout(resp);

            } else {
                resp.sendError(404, "Ruta no encontrada");
            }

        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();

        if ("/me".equals(path)) {

            try {
                String auth = req.getHeader("Authorization");

                if (auth == null || !auth.startsWith("Bearer ")) {
                    resp.sendError(401, "Token requerido");
                    return;
                }

                String token = auth.replace("Bearer ", "");

                int id = JwtUtil.obtenerId(token);
                String username = JwtUtil.obtenerUsername(token);
                String rol = JwtUtil.obtenerRol(token);

                Map<String, Object> data = new HashMap<>();
                data.put("id", id);
                data.put("username", username);
                data.put("rol", rol);

                resp.setContentType("application/json");
                resp.getWriter().print(gson.toJson(data));

            } catch (Exception e) {
                resp.sendError(401, "Token inválido");
            }

        } else {
            resp.sendError(404);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        BufferedReader br = req.getReader();
        LoginDTO dto = gson.fromJson(br, LoginDTO.class);

        Usuario usuario = service.login(dto.username, dto.password);

        if (usuario == null) {
            resp.sendError(401, "Credenciales inválidas");
            return;
        }

        String token = JwtUtil.generarToken(
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getRol()
        );

        Map<String, Object> result = new HashMap<>();

        result.put("token", token);

        Map<String, Object> user = new HashMap<>();
        user.put("id", usuario.getIdUsuario());
        user.put("username", usuario.getUsername());
        user.put("rol", usuario.getRol());

        result.put("usuario", user);

        resp.getWriter().print(gson.toJson(result));
    }

    private void register(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {


        System.out.println(" se  va a registrar a un usuario");
        BufferedReader br = req.getReader();
        RegisterDTO dto = gson.fromJson(br, RegisterDTO.class);

        boolean creado = service.register(dto);

        if (!creado) {
            resp.sendError(400, "Usuario o correo ya existente");
            return;
        }

        Map<String, String> result = new HashMap<>();
        result.put("mensaje", "Usuario registrado correctamente");

        resp.getWriter().print(gson.toJson(result));
    }

    private void logout(HttpServletResponse resp)
            throws IOException {

        Map<String, String> result = new HashMap<>();
        result.put("mensaje", "Logout exitoso");

        resp.getWriter().print(gson.toJson(result));
    }

    class LoginDTO {
        String username;
        String password;
    }


}
