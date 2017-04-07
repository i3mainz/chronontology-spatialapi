package tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import classes.BoundingBox;
import classes.GazetteerData;
import functions.DistanceCalc;
import functions.GazetteerDAI;
import functions.GazetteerGeonames;
import functions.GazetteerGettyTGN;
import functions.General;
import functions.StringDistanceCalc;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			bb.getBoundingBoxFromLatLon(req_lat, req_lon, "0.1");
			// HARVEST DATA
			//StringBuffer resultTGN = getResultsFromGettyTGN(lowerleftSplit[0], lowerleftSplit[1], upperrightSplit[0], upperrightSplit[1]);
			StringBuffer resultTGN = GazetteerGettyTGN.getResultsFromGettyTGN(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()));
			List<GazetteerData> tgn = GazetteerGettyTGN.ParseTGNJSON(resultTGN);
			System.out.println("TGN size: " + tgn.size());
			StringBuffer resultGEONAMES = GazetteerGeonames.getResultsFromGeonames(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lon()));
			List<GazetteerData> geonames = GazetteerGeonames.ParseGEONAMESJSON(resultGEONAMES);
			System.out.println("GEONAMES size: " + geonames.size());
			//StringBuffer resultDAI = getResultsFromDaiGazetteer(upperleftSplit[0], upperleftSplit[1], upperrightSplit[0], upperrightSplit[1], lowerrightSplit[0], lowerrightSplit[1], lowerleftSplit[0], lowerleftSplit[1]);
			StringBuffer resultDAI = GazetteerDAI.getResultsFromDaiGazetteer(String.valueOf(bb.getUpperleft_lat()), String.valueOf(bb.getUpperleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()), String.valueOf(bb.getLowerright_lat()), String.valueOf(bb.getLowerright_lon()), String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()));
			List<GazetteerData> daigazetteer = GazetteerDAI.ParseDAIJSON(resultDAI);
			System.out.println("DAI GAZETTEER size: " + daigazetteer.size());
			// CALCULATE AND SET DISTANCES
			// SET GETTY TGN
			for (GazetteerData element : tgn) {
				// Point1: Point, Point2: Data
				double distance = DistanceCalc.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
				element.setDISTANCE(General.round(distance, 3));
				double levenshtein = StringDistanceCalc.Levenshtein(req_name, element.getNAME());
				double normalizedlevenshtein = StringDistanceCalc.NormalizedLevenshtein(req_name, element.getNAME());
				double dameraulevenshtein = StringDistanceCalc.Damerau(req_name, element.getNAME());
				double jarowinkler = StringDistanceCalc.JaroWinkler(req_name, element.getNAME());
				element.setLevenshtein(General.round(levenshtein, 2));
				element.setNormalizedLevenshtein(General.round(normalizedlevenshtein, 2));
				element.setDamerauLevenshtein(General.round(dameraulevenshtein, 2));
				element.setJaroWinkler(General.round(jarowinkler, 2));
			}
			// SET GEONAMES
			for (GazetteerData element : geonames) {
				// Point1: Point, Point2: Data
				double distance = DistanceCalc.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
				element.setDISTANCE(General.round(distance, 3));
				double levenshtein = StringDistanceCalc.Levenshtein(req_name, element.getNAME());
				double normalizedlevenshtein = StringDistanceCalc.NormalizedLevenshtein(req_name, element.getNAME());
				double dameraulevenshtein = StringDistanceCalc.Damerau(req_name, element.getNAME());
				double jarowinkler = StringDistanceCalc.JaroWinkler(req_name, element.getNAME());
				element.setLevenshtein(General.round(levenshtein, 2));
				element.setNormalizedLevenshtein(General.round(normalizedlevenshtein, 2));
				element.setDamerauLevenshtein(General.round(dameraulevenshtein, 2));
				element.setJaroWinkler(General.round(jarowinkler, 2));
			}
			// DAI GAZETTEER
			for (GazetteerData element : daigazetteer) {
				// Point1: Point, Point2: Data
				double distance = DistanceCalc.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
				element.setDISTANCE(General.round(distance, 3));
				double levenshtein = StringDistanceCalc.Levenshtein(req_name, element.getNAME());
				double normalizedlevenshtein = StringDistanceCalc.NormalizedLevenshtein(req_name, element.getNAME());
				double dameraulevenshtein = StringDistanceCalc.Damerau(req_name, element.getNAME());
				double jarowinkler = StringDistanceCalc.JaroWinkler(req_name, element.getNAME());
				element.setLevenshtein(General.round(levenshtein, 2));
				element.setNormalizedLevenshtein(General.round(normalizedlevenshtein, 2));
				element.setDamerauLevenshtein(General.round(dameraulevenshtein, 2));
				element.setJaroWinkler(General.round(jarowinkler, 2));
			}
			// CREATE GEOJSON
			StringBuffer geojson = new StringBuffer();
			List<String> data = new ArrayList<String>();
			geojson.append("{ \"type\": \"FeatureCollection\", ");
			geojson.append("\"place\": { \"lat\": " + req_lat + ", \"lon\": " + req_lon + ", \"name\": \"" + req_name + "\"}, ");
			geojson.append("\"elements\": { \"gettyTGN\": " + tgn.size() + ", \"geonames\": " + geonames.size() + ", \"daiGazetteer\": " + daigazetteer.size() + " },");
			geojson.append("\"features\": [ ");
			// SET START POINT
			String startpoint = "{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\",\"coordinates\": [" + req_lon + "," + req_lat + "] }, \"properties\": {\"type\": \"startpoint\", \"name\": \"" + req_name + "\"} },";
			data.add(startpoint);
			// SET BOUNDING BOX
			//String bboxfeature = "{ \"type\": \"Feature\", \"geometry\": " + bb.getGeoJSON() + ", \"properties\": {\"type\": \"boundingbox\"} },";
			//data.add(bboxfeature);
			// SET GETTY TGN
			for (GazetteerData element : tgn) {
			 String lat = element.getLAT().replace("-.", "-0.");
			 String lon = element.getLON().replace("-.", "-0.");
			 String feature = "{ \"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + lon + ", " + lat + "]} , \"properties\": {\"uri\": \"" + element.getURI() + "\", \"name\": \"" + element.getNAME() + "\", \"provenance\": \"" + element.getPROVENANCE() + "\", \"distance\":" + element.getDISTANCE() + ", \"similarity\": { \"levenshtein\": " + element.getLevenshtein() + ", \"normalizedlevenshtein\": " + element.getNormalizedLevenshtein() + ", \"dameraulevenshtein\": " + element.getDamerauLevenshtein() + ", \"jarowinkler\": " + element.getJaroWinkler() + " }} },";
			 data.add(feature);
			 }
			// SET GEONAMES
			for (GazetteerData element : geonames) {
				String lat = element.getLAT().replace("-.", "-0.");
				String lon = element.getLON().replace("-.", "-0.");
				String feature = "{ \"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + lon + ", " + lat + "]} , \"properties\": {\"uri\": \"" + element.getURI() + "\", \"name\": \"" + element.getNAME() + "\", \"provenance\": \"" + element.getPROVENANCE() + "\", \"distance\":" + element.getDISTANCE() + ", \"similarity\": { \"levenshtein\": " + element.getLevenshtein() + ", \"normalizedlevenshtein\": " + element.getNormalizedLevenshtein() + ", \"dameraulevenshtein\": " + element.getDamerauLevenshtein() + ", \"jarowinkler\": " + element.getJaroWinkler() + " }} },";
				data.add(feature);
			}
			// SET DAI GAZETTEER
			for (GazetteerData element : daigazetteer) {
			 String lat = element.getLAT().replace("-.", "-0.");
			 String lon = element.getLON().replace("-.", "-0.");
			 String feature = "{ \"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" + lon + ", " + lat + "]} , \"properties\": {\"uri\": \"" + element.getURI() + "\", \"name\": \"" + element.getNAME() + "\", \"provenance\": \"" + element.getPROVENANCE() + "\", \"distance\":" + element.getDISTANCE() + ", \"similarity\": { \"levenshtein\": " + element.getLevenshtein() + ", \"normalizedlevenshtein\": " + element.getNormalizedLevenshtein() + ", \"dameraulevenshtein\": " + element.getDamerauLevenshtein() + ", \"jarowinkler\": " + element.getJaroWinkler() + " }} },";
			 data.add(feature);
			 }
			String lastline = data.get(data.size() - 1).substring(0, data.get(data.size() - 1).length() - 1);
			data.set(data.size() - 1, lastline);
			for (String dataelement : data) {
				geojson.append(dataelement);
			}
			geojson.append("] }");
			System.out.println(geojson);
			// pretty print JSON output (Gson)
			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(geojson.toString()).getAsJsonObject();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			out.print(gson.toJson(json));
		} catch (Exception e) {
			for (StackTraceElement element : e.getStackTrace()) {
				String message = "element: \"" + element.getClassName() + " / " + element.getMethodName() + "() / " + element.getLineNumber() + "\"";
				System.out.println(message);
				out.println(message);
			}
			response.setContentType("text/plain;charset=UTF-8");
			response.sendError(500);
		} finally {
			out.close();
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("UTF-8");
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
