package filter;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JwtUtil;

import java.io.IOException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        System.out.println("JWT FILTER");

        if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("OPTIONS")) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
             return;
        }

        String path = req.getRequestURI();

        if (path.contains("/auth/login") || path.contains("/auth/register")) {

            chain.doFilter(request, response);
            return;
        }

        if( path.contains("/administrador")){
            chain.doFilter(request, response);
            return;

        }

        String auth = req.getHeader("Authorization");

        System.out.println("HEADER AUTH: " + auth);

        if (auth == null || !auth.startsWith("Bearer ")) {


            System.out.println(auth);
            resp.sendError(401, "Token requerido");
            System.out.println("Error 1");
            return;
        }

        try {

            String token = auth.replace("Bearer ", "");

            DecodedJWT decoded = JwtUtil.validarToken(token);

            int idUsuario = decoded.getClaim("id").asInt();
            String rol = decoded.getClaim("rol").asString();


            req.setAttribute("idUsuario", idUsuario);
            req.setAttribute("rol", rol);

            chain.doFilter(request, response);

        } catch (Exception e) {
            resp.sendError(401, "Token inválido");
        }


/*
        try {
            String token = auth.replace("Bearer ", "");
            JwtUtil.validarToken(token);
            chain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("Error 2");
            resp.sendError(401, "Token inválido");
        }*/
    }
}