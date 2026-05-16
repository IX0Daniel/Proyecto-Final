package servlet;

import com.google.gson.Gson;
import dto.contrato.ContratoListResponse;
import dto.contrato.ContratoResponse;
import dto.contrato.CrearContratoRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ContratoService;
import utils.JwtUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


@WebServlet("/api/contratos/*")
public class ContratoServlet extends HttpServlet {



    private final Gson gson = new Gson();
    private final ContratoService service = new ContratoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {

            String token = req.getHeader("Authorization").replace("Bearer ", "");
            int idCliente = JwtUtil.obtenerId(token);

            BufferedReader br = req.getReader();
            CrearContratoRequest dto = gson.fromJson(br, CrearContratoRequest.class);

            int idContrato = service.crearContrato(idCliente, dto.getIdPropuesta());

            resp.getWriter().print(
                    gson.toJson(new ContratoResponse(idContrato, "a"))
            );

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        String path = req.getPathInfo();

        try {

            String token = req.getHeader("Authorization")
                    .replace("Bearer ", "");

            int idUsuario = JwtUtil.obtenerId(token);

            if ("/freelancer".equals(path)) {

                List<ContratoListResponse> lista =
                        service.listarContratosFreelancer(idUsuario);

                resp.getWriter().print(gson.toJson(lista));

            }else if ("/cliente".equals(path)) {

                List<ContratoListResponse> lista =
                        service.listarContratosCliente(idUsuario);

                resp.getWriter().print(gson.toJson(lista));
            }



        } catch (Exception e) {

            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        }
    }

}
