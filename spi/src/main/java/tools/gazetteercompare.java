package tools;

import classes.BoundingBox;
import classes.GazetteerData;
import errorlog.Logging;
import gazetteer.GazetteerDAI;
import gazetteer.GazetteerGeonames;
import gazetteer.GazetteerGettyTGN;
import functions.Functions;
import functions.StringSimilarity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class gazetteercompare extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			// PARSE REQUEST
			String req_lat = "49.9987";
			String req_lon = "8.27399";
			String req_name = "Mainzer Dom";
			//String repo = "all";
			if (request.getParameter("lat") != null) {
				req_lat = request.getParameter("lat");
			}
			if (request.getParameter("lon") != null) {
				req_lon = request.getParameter("lon");
			}
			if (request.getParameter("name") != null) {
				req_name = request.getParameter("name");
			}
			// GET BOUNDINGBOX
			BoundingBox bb = new BoundingBox();
			bb.getBoundingBoxFromLatLon(Double.parseDouble(req_lat), Double.parseDouble(req_lon), 0.1);
			// HARVEST DATA
			//StringBuffer resultTGN = getResultsFromGettyTGN(lowerleftSplit[0], lowerleftSplit[1], upperrightSplit[0], upperrightSplit[1]);
			StringBuffer resultTGN = GazetteerGettyTGN.getResultsFromGettyTGN(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()));
			List<GazetteerData> tgn = GazetteerGettyTGN.ParseTGNJSON(resultTGN);
			StringBuffer resultGEONAMES = GazetteerGeonames.getResultsFromGeonames(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lon()));
			List<GazetteerData> geonames = GazetteerGeonames.ParseGEONAMESJSON(resultGEONAMES);
			//StringBuffer resultDAI = getResultsFromDaiGazetteer(upperleftSplit[0], upperleftSplit[1], upperrightSplit[0], upperrightSplit[1], lowerrightSplit[0], lowerrightSplit[1], lowerleftSplit[0], lowerleftSplit[1]);
			StringBuffer resultDAI = GazetteerDAI.getResultsFromDaiGazetteer(String.valueOf(bb.getUpperleft_lat()), String.valueOf(bb.getUpperleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()), String.valueOf(bb.getLowerright_lat()), String.valueOf(bb.getLowerright_lon()), String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()));
			List<GazetteerData> daigazetteer = GazetteerDAI.ParseDAIJSON(resultDAI);
			// CALCULATE AND SET DISTANCES
			// SET GETTY TGN
			for (GazetteerData element : tgn) {
				// Point1: Point, Point2: Data
				double distance = Functions.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
				element.setDISTANCE(Functions.round(distance, 3));
				double levenshtein = StringSimilarity.Levenshtein(req_name, element.getNAME());
				double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(req_name, element.getNAME());
				double dameraulevenshtein = StringSimilarity.Damerau(req_name, element.getNAME());
				double jarowinkler = StringSimilarity.JaroWinkler(req_name, element.getNAME());
				element.setLevenshtein(Functions.round(levenshtein, 2));
				element.setNormalizedLevenshtein(Functions.round(normalizedlevenshtein, 2));
				element.setDamerauLevenshtein(Functions.round(dameraulevenshtein, 2));
				element.setJaroWinkler(Functions.round(jarowinkler, 2));
			}
			// SET GEONAMES
			for (GazetteerData element : geonames) {
				// Point1: Point, Point2: Data
				double distance = Functions.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
				element.setDISTANCE(Functions.round(distance, 3));
				double levenshtein = StringSimilarity.Levenshtein(req_name, element.getNAME());
				double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(req_name, element.getNAME());
				double dameraulevenshtein = StringSimilarity.Damerau(req_name, element.getNAME());
				double jarowinkler = StringSimilarity.JaroWinkler(req_name, element.getNAME());
				element.setLevenshtein(Functions.round(levenshtein, 2));
				element.setNormalizedLevenshtein(Functions.round(normalizedlevenshtein, 2));
				element.setDamerauLevenshtein(Functions.round(dameraulevenshtein, 2));
				element.setJaroWinkler(Functions.round(jarowinkler, 2));
			}
			// DAI GAZETTEER
			for (GazetteerData element : daigazetteer) {
				// Point1: Point, Point2: Data
				double distance = Functions.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
				element.setDISTANCE(Functions.round(distance, 3));
				double levenshtein = StringSimilarity.Levenshtein(req_name, element.getNAME());
				double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(req_name, element.getNAME());
				double dameraulevenshtein = StringSimilarity.Damerau(req_name, element.getNAME());
				double jarowinkler = StringSimilarity.JaroWinkler(req_name, element.getNAME());
				element.setLevenshtein(Functions.round(levenshtein, 2));
				element.setNormalizedLevenshtein(Functions.round(normalizedlevenshtein, 2));
				element.setDamerauLevenshtein(Functions.round(dameraulevenshtein, 2));
				element.setJaroWinkler(Functions.round(jarowinkler, 2));
			}
			// CREATE GEOJSON
			JSONObject outObject = new JSONObject();
			outObject.put("type", "FeatureCollection");
			// SET PLACE
			JSONObject place = new JSONObject();
			place.put("lat", Double.parseDouble(req_lat));
			place.put("lon", Double.parseDouble(req_lon));
			place.put("name", req_name);
			outObject.put("place", place);
			// SET ELEMENTS
			JSONObject elements = new JSONObject();
			elements.put("gettyTGN", tgn.size());
			elements.put("geonames", geonames.size());
			elements.put("daiGazetteer", daigazetteer.size());
			outObject.put("elements", elements);
			// SET FEATURES
			JSONArray features = new JSONArray();
			// SET START POINT
			JSONObject start = new JSONObject();
			start.put("type", "Feature");
			JSONObject geomS = new JSONObject();
			geomS.put("type", "Point");
			JSONArray coordinatesS = new JSONArray();
			coordinatesS.add(Double.parseDouble(req_lon));
			coordinatesS.add(Double.parseDouble(req_lat));
			geomS.put("coordinates", coordinatesS);
			JSONObject propertiesS = new JSONObject();
			propertiesS.put("type", "startpoint");
			propertiesS.put("name", req_name);
			start.put("geometry", geomS);
			start.put("properties", propertiesS);
			features.add(start);
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
				properties.put("distance", element.getDISTANCE());
				JSONObject sim = new JSONObject();
				sim.put("levenshtein", element.getLevenshtein());
				sim.put("normalizedlevenshtein", element.getNormalizedLevenshtein());
				sim.put("dameraulevenshtein", element.getDamerauLevenshtein());
				sim.put("jarowinkler", element.getJaroWinkler());
				properties.put("similarity", sim);
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
				properties.put("distance", element.getDISTANCE());
				JSONObject sim = new JSONObject();
				sim.put("levenshtein", element.getLevenshtein());
				sim.put("normalizedlevenshtein", element.getNormalizedLevenshtein());
				sim.put("dameraulevenshtein", element.getDamerauLevenshtein());
				sim.put("jarowinkler", element.getJaroWinkler());
				properties.put("similarity", sim);
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
				properties.put("distance", element.getDISTANCE());
				JSONObject sim = new JSONObject();
				sim.put("levenshtein", element.getLevenshtein());
				sim.put("normalizedlevenshtein", element.getNormalizedLevenshtein());
				sim.put("dameraulevenshtein", element.getDamerauLevenshtein());
				sim.put("jarowinkler", element.getJaroWinkler());
				properties.put("similarity", sim);
				feature.put("geometry", geom);
				feature.put("properties", properties);
				features.add(feature);
			}
			outObject.put("features", features);
			out.print(outObject);
		} catch (Exception e) {
			out.print(Logging.getMessageJSON(e, "tools.gazetteercompare"));
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
