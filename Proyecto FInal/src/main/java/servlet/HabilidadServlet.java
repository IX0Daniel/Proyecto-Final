package servlet;

import com.google.gson.Gson;
import dao.HabilidadDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Habilidad;

import java.io.IOException;
import java.util.List;


@WebServlet("/api/habilidades/*")
public class HabilidadServlet extends HttpServlet {

    HabilidadDAO service = new HabilidadDAO();
    Gson gsom = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{

            List<Habilidad> habilidades= service.obtenerHablidades();
            response.getWriter().print(gsom.toJson(habilidades));


        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print(e.getMessage());
            response.sendError(500);

        }

    }

}
