package servlet;

import com.google.gson.Gson;
import dto.entrega.AprobarEntregaRequest;
import dto.entrega.CrearEntregaRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EntregaService;
import utils.JwtUtil;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/entregas/*")
public class EntregaSerevlet extends HttpServlet {


    private final Gson gson = new Gson();
    private final EntregaService service = new EntregaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String path = req.getPathInfo();


        String token = req.getHeader("Authorization").replace("Bearer ", "");
        int idCliente = JwtUtil.obtenerId(token);

        String rolCLiente = JwtUtil.obtenerRol(token);

        try {

            if ("/crear".equals(path)) {

                BufferedReader br = req.getReader();
                CrearEntregaRequest dto = gson.fromJson(br, CrearEntregaRequest.class);

                service.crearEntrega(
                        dto.getIdContrato(),
                        dto.getDescripcion(),
                        dto.getUrlArchivo()
                );

                resp.getWriter().print("OK");

            } else if ("/aprobar".equals(path)) {

                BufferedReader br = req.getReader();
                AprobarEntregaRequest dto = gson.fromJson(br, AprobarEntregaRequest.class);

                service.aprobar(dto.getIdEntrega(), idCliente, rolCLiente );

                resp.getWriter().print("OK");
            }

        }  catch (Exception e) {
        e.printStackTrace();
        resp.getWriter().print(e.getMessage());
        }

/*        catch (Exception e) {
            resp.sendError(500);


        }*/
    }

}
