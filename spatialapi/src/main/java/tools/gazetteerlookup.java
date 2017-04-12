package tools;

import classes.BoundingBox;
import classes.GazetteerData;
import errorlog.Logging;
import functions.GazetteerDAI;
import functions.GazetteerGeonames;
import functions.GazetteerGettyTGN;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class gazetteerlookup extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			// PARSE REQUEST
			String upperleft = "50.082665;8.161050";
			String lowerleft = "50.082665;8.371850";
			String upperright = "49.903887;8.161050";
			String lowerright = "49.903887;8.371850";
			if (request.getParameter("upperleft") != null) {
				upperleft = request.getParameter("upperleft");
			}
			if (request.getParameter("lowerleft") != null) {
				lowerleft = request.getParameter("lowerleft");
			}
			if (request.getParameter("upperright") != null) {
				upperright = request.getParameter("upperright");
			}
			if (request.getParameter("lowerright") != null) {
				lowerright = request.getParameter("lowerright");
			}
			String[] upperleftSplit = upperleft.split(";"); //[0]=LAT [1]=LON
			String[] lowerleftSplit = lowerleft.split(";"); //[0]=LAT [1]=LON
			String[] upperrightSplit = upperright.split(";"); //[0]=LAT [1]=LON
			String[] lowerrightSplit = lowerright.split(";"); //[0]=LAT [1]=LON
			// GET BOUNDINGBOX
			BoundingBox bb = new BoundingBox(Double.parseDouble(upperleftSplit[0]), Double.parseDouble(upperleftSplit[1]), Double.parseDouble(lowerleftSplit[0]), Double.parseDouble(lowerleftSplit[1]), Double.parseDouble(upperrightSplit[0]), Double.parseDouble(upperrightSplit[1]), Double.parseDouble(lowerrightSplit[0]), Double.parseDouble(lowerrightSplit[1]));
			// HARVEST DATA
			StringBuffer resultTGN = GazetteerGettyTGN.getResultsFromGettyTGN(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()));
			List<GazetteerData> tgn = GazetteerGettyTGN.ParseTGNJSON(resultTGN);
			StringBuffer resultGEONAMES = GazetteerGeonames.getResultsFromGeonames(String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getUpperright_lon()), String.valueOf(bb.getLowerleft_lon()));
			List<GazetteerData> geonames = GazetteerGeonames.ParseGEONAMESJSON(resultGEONAMES);
			StringBuffer resultDAI = GazetteerDAI.getResultsFromDaiGazetteer(String.valueOf(bb.getUpperleft_lat()), String.valueOf(bb.getUpperleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()), String.valueOf(bb.getLowerright_lat()), String.valueOf(bb.getLowerright_lon()), String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()));
			List<GazetteerData> daigazetteer = GazetteerDAI.ParseDAIJSON(resultDAI);
			// CREATE GEOJSON
			JSONObject outObject = new JSONObject();
			outObject.put("type", "FeatureCollection");
			// SET ELEMENTS
			JSONObject elements = new JSONObject();
			elements.put("gettyTGN", tgn.size());
			elements.put("geonames", geonames.size());
			elements.put("daiGazetteer", daigazetteer.size());
			outObject.put("elements", elements);
			// SET FEATURES
			JSONArray features = new JSONArray();
			// SET BOUNDING BOX
			features.add(bb.getGeoJSON());
			// SET GETTY TGN
			for (GazetteerData element : tgn) {
				String lat = element.getLAT().replace("-.", "-0.");
				String lon = element.getLON().replace("-.", "-0.");
				JSONObject feature = new JSONObject();
				feature.put("type", "Feature");
				JSONObject geom = new JSONObject();
				geom.put("type", "Point");
				JSONArray coordinates = new JSONArray();
				coordinates.add(Double.parseDouble(lon));
				coordinates.add(Double.parseDouble(lat));
				geom.put("coordinates", coordinates);
				JSONObject properties = new JSONObject();
				properties.put("uri", element.getURI());
				properties.put("name", element.getNAME());
				properties.put("provenance", element.getPROVENANCE());
				feature.put("geometry", geom);
				feature.put("properties", properties);
				features.add(feature);
			}
			// SET GEONAMES
			for (GazetteerData element : geonames) {
				String lat = element.getLAT().replace("-.", "-0.");
				String lon = element.getLON().replace("-.", "-0.");
				JSONObject feature = new JSONObject();
				feature.put("type", "Feature");
				JSONObject geom = new JSONObject();
				geom.put("type", "Point");
				JSONArray coordinates = new JSONArray();
				coordinates.add(Double.parseDouble(lon));
				coordinates.add(Double.parseDouble(lat));
				geom.put("coordinates", coordinates);
				JSONObject properties = new JSONObject();
				properties.put("uri", element.getURI());
				properties.put("name", element.getNAME());
				properties.put("provenance", element.getPROVENANCE());
				feature.put("geometry", geom);
				feature.put("properties", properties);
				features.add(feature);
			}
			// SET DAI GAZETTEER
			for (GazetteerData element : daigazetteer) {
				String lat = element.getLAT().replace("-.", "-0.");
				String lon = element.getLON().replace("-.", "-0.");
				JSONObject feature = new JSONObject();
				feature.put("type", "Feature");
				JSONObject geom = new JSONObject();
				geom.put("type", "Point");
				JSONArray coordinates = new JSONArray();
				coordinates.add(Double.parseDouble(lon));
				coordinates.add(Double.parseDouble(lat));
				geom.put("coordinates", coordinates);
				JSONObject properties = new JSONObject();
				properties.put("uri", element.getURI());
				properties.put("name", element.getNAME());
				properties.put("provenance", element.getPROVENANCE());
				feature.put("geometry", geom);
				feature.put("properties", properties);
				features.add(feature);
			}
			outObject.put("features", features);
			out.print(outObject);
		} catch (Exception e) {
			out.print(Logging.getMessageJSON(e, "tools.gazetteerlookup"));
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
