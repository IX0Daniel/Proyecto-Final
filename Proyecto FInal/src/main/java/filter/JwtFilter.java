package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JwtUtil;

import java.io.IOException;


@WebFilter("/api/*")
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if (path.contains("/auth/login") ||
                path.contains("/auth/register")) {

            chain.doFilter(request, response);
            return;
        }

        String auth = req.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            resp.sendError(401, "Token requerido");
            return;
        }

        try {

            String token = auth.replace("Bearer ", "");
            JwtUtil.validarToken(token);

            chain.doFilter(request, response);

        } catch (Exception e) {
            resp.sendError(401, "Token inválido");
        }
    }


}
