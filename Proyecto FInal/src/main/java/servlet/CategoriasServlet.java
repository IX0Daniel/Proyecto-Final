package servlet;

import com.google.gson.Gson;
import dao.CategoriaDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;

import java.io.IOException;
import java.util.List;


@WebServlet("/api/categorias/*")
public class CategoriasServlet extends HttpServlet {


    CategoriaDAO service = new CategoriaDAO();
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{


        try{
            System.out.println("CARGANDO categorias");
            List<Categoria> categorias = service.obtenerCategorias();
            response.getWriter().print(gson.toJson(categorias));


        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print(e.getMessage());
        }


    }


}
