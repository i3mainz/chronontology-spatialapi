package servelets;

import errorlog.Logging;
import functions.ChronOntology;
import functions.GazetteerDAI;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class GetGazetteerSearchResult extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        try {
            // parse params
            String req_type = "";
            if (request.getParameter("type") != null) {
                req_type = request.getParameter("type");
            }
            req_type = URLDecoder.decode(req_type, "UTF-8");
            String req_id = "";
            if (request.getParameter("id") != null) {
                req_id = request.getParameter("id");
            }
            req_id = URLDecoder.decode(req_id, "UTF-8");
            // get geojson
            JSONObject geojson = new JSONObject();
            geojson.put("type", "FeatureCollection");
            out.print(geojson);
        } catch (Exception e) {
            out.print(Logging.getMessageJSON(e, "servlets.GetGazetteerSearchResult"));
        } finally {
            out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
