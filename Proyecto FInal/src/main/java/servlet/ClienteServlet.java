package servlet;

import com.google.gson.Gson;
import dto.cliente.CompletarPerfilRequest;
import dto.cliente.RecargaRequest;
import dto.cliente.RecargaResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ClienteService;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/clientes/*")
public class ClienteServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final ClienteService service = new ClienteService();

    private int getId(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ", "");
        return JwtUtil.obtenerId(token);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();

        try {

            int id = getId(req);

            if ("/perfil-completo".equals(path)) {

                BufferedReader br = req.getReader();
                CompletarPerfilRequest dto = gson.fromJson(br, CompletarPerfilRequest.class);

                boolean ok = service.completarPerfil(id, dto.getTelefono(), dto.getDireccion());

                resp.getWriter().print(ok);

            } else if ("/recargar".equals(path)) {

                BufferedReader br = req.getReader();
                RecargaRequest dto = gson.fromJson(br, RecargaRequest.class);

                double saldo = service.recargar(id, dto.getMonto());

                resp.getWriter().print(gson.toJson(new RecargaResponse(saldo)));

            } else {
                resp.sendError(404);
            }

        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());

        } catch (Exception e) {
            resp.sendError(500);
        }
    }
}
