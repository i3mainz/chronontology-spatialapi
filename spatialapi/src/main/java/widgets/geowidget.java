package widgets;

import classes.GazetteerData;
import errorlog.Logging;
import functions.ChronOntology;
import functions.GazetteerDAI;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class geowidget extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String req_uri = "";
			if (request.getParameter("uri") != null) {
				req_uri = request.getParameter("uri");
			}
			req_uri = URLDecoder.decode(req_uri, "UTF-8");
			List<String> geoitems = ChronOntology.ResultsFromChronOntology(req_uri);
			GazetteerData gd;
			List<GazetteerData> resultList = new ArrayList<>();
			for (String element : geoitems) {
				String[] tmp = element.split("%");
				gd = GazetteerDAI.getgeoJSONFromDaiGazetteer(tmp[0]);
				gd.setOPTIONAL(tmp[1]);
				if (gd != null && !gd.getGEOMETRY().equals("")) {
					resultList.add(gd);
				}
			}
			// create geojson
			JSONObject geojson = new JSONObject();
			geojson.put("type", "FeatureCollection");
			JSONArray features = new JSONArray();
			for (GazetteerData element : resultList) {
				JSONObject feature = new JSONObject();
				feature.put("type", "Feature");
				JSONObject geometry = (JSONObject) new JSONParser().parse(element.getGEOMETRY());
				feature.put("geometry", geometry);
				JSONObject properties = new JSONObject();
				properties.put("homepage", element.getURI());
				properties.put("name", element.getNAME());
				properties.put("relation", element.getOPTIONAL());
				feature.put("properties", properties);
				features.add(feature);
			}
			geojson.put("features", features);
			out.print(geojson);
		} catch (Exception e) {
			out.print(Logging.getMessageJSON(e, "widgets.geowidget"));
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
