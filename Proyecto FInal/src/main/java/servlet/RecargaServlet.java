package servlet;

import com.google.gson.Gson;
import dto.cliente.RecargaRequest;
import dto.recarga.RecargaResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.RecargaService;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/recargas/*")
public class RecargaServlet extends HttpServlet {


    private final Gson gson = new Gson();
    private final RecargaService service = new RecargaService();

    private int getId(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ", "");
        return JwtUtil.obtenerId(token);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int idUsuario = getId(req);

            List<RecargaResponse> lista = service.historial(idUsuario);

            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(lista));

        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int idUsuario = getId(req);

            BufferedReader br = req.getReader();
            RecargaRequest dto = gson.fromJson(br, RecargaRequest.class);

            service.recargar(idUsuario, dto.getMonto());

            resp.getWriter().print("OK");

        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

}
