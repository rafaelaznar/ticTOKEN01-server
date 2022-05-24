package eu.rafaelaznar.tictoken01.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafa
 */
public class JWTFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //PrintWriter writer = response.getWriter();
        String auth = request.getHeader("Authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (auth == null || !auth.startsWith("Bearer ")) {
//                    writer.println("ERROR doFilter ");
//                    writer.close();
//                    return;
            } else {
                String token = auth.substring(7);
                try {
                    String nombre = Jwt.validateJWT(token);
                    request.setAttribute("nombreusuario", nombre);
                } catch (Exception e) {
                    throw new ServletException("Invalid token");
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
//            writer.close();
        }
    }

    @Override
    public void destroy() {

    }
}
