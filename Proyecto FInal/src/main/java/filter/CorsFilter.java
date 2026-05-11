package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Credentials", "true");

        if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
            res.setStatus(HttpServletResponse.SC_OK);
            System.out.println("Options");
            return;
        }
        chain.doFilter(request, response);
        System.out.println("CORS FILTER - ??");
    }
}
