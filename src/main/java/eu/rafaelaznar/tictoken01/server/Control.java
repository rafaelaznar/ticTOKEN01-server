package eu.rafaelaznar.tictoken01.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafael aznar
 */
public class Control extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException, Exception {

        try ( PrintWriter out = response.getWriter()) {
            String ob = request.getParameter("ob");
            String op = request.getParameter("op");
            String data = "";
            GsonBuilder oGsonBuilder = new GsonBuilder();
            Gson gson = oGsonBuilder.create();
            if (("".equalsIgnoreCase(ob) && "".equalsIgnoreCase(op)) || (ob == null && op == null)) {
                response.setContentType("application/json;charset=UTF-8");
                data = gson.toJson("Object or Operation Error");
                out.print(data);
                out.flush();
            } else {
                if (request.getParameter("ob").equals("user")) {
                    if (request.getParameter("op").equals("login")) {
                        response.setContentType("application/json;charset=UTF-8");
                        if (request.getParameter("username").equals("daw") && request.getParameter("password").equalsIgnoreCase("ausias")) {
                            data = gson.toJson(Jwt.generateJWT(request.getParameter("username")));
                        } else {
                            data = gson.toJson("ERROR: Wrong login or password");
                        }
                        out.print(data);
                        out.flush();
                    }
                }
                if (request.getParameter("ob").equals("user")) {
                    if (request.getParameter("op").equals("check")) {
                        response.setContentType("application/json;charset=UTF-8");
                        data = gson.toJson(request.getAttribute("nombreusuario"));
                        out.print(data);
                        out.flush();
                    }
                }
                if (request.getParameter("ob").equals("user")) {
                    if (request.getParameter("op").equals("secret")) {
                        response.setContentType("application/json;charset=UTF-8");                        
                        if (request.getAttribute("nombreusuario").equals("daw")) {
                            data = "KPD" + randInt(1000, 10000) + "ZX" + randInt(1000, 10000) + "A";
                        } else {
                            data = "ERROR";
                        }
                        out.print(gson.toJson(data));
                        out.flush();
                    }
                }
                if ((request.getParameter("ob") == null) || (request.getParameter("op") == null)) {
                    getServletContext().getRequestDispatcher("/index.html").forward(request, response);
                }
            }
        } catch (Exception ex) {
            response.setContentType("application/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(ex.getMessage());
            out.println("--------");
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
