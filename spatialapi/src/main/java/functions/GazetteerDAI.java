package functions;

import classes.GazetteerData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GazetteerDAI {

	//private String host = "localhost:8084";
	private static String host = "chronontology.i3mainz.hs-mainz.de";
	
	public static StringBuffer getResultsFromDaiGazetteer(String upperleftLat, String upperleftLon, String upperrightLat, String upperrightLon, String lowerrightLat, String lowerrightLon, String lowerleftLat, String lowerleftLon) throws Exception {
		String url = "http://gazetteer.dainst.org/search.json";
		String urlParameters = "?";
		urlParameters += "polygonFilterCoordinates=" + upperleftLon + "&polygonFilterCoordinates=" + upperleftLat + "&polygonFilterCoordinates=" + upperrightLon + "&polygonFilterCoordinates=" + upperrightLat;
		urlParameters += "&polygonFilterCoordinates=" + lowerrightLon + "&polygonFilterCoordinates=" + lowerrightLat + "&polygonFilterCoordinates=" + lowerleftLon + "&polygonFilterCoordinates=" + lowerleftLat;
		urlParameters += "&q=*";
		urlParameters += "&fq=types:populated-place";
		url += urlParameters;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("DAI Gazetteer Response Code : " + responseCode);
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response;
		} else {
			return null;
		}
	}

	public static List<GazetteerData> ParseDAIJSON(StringBuffer JSONLD) throws IOException, ParseException {
		List<GazetteerData> resultList = new ArrayList<GazetteerData>();
		if (JSONLD != null && !JSONLD.toString().equals("")) {
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(JSONLD.toString());
			JSONArray result = (JSONArray) jsonObject.get("result");
			for (Object element : result) {
				JSONObject daiEntry = (JSONObject) element;
				String URI = (String) daiEntry.get("@id");
				JSONObject prefName = (JSONObject) daiEntry.get("prefName");
				String NAME = (String) prefName.get("title");
				JSONObject prefLocation = (JSONObject) daiEntry.get("prefLocation");
				JSONArray coordinates = (JSONArray) prefLocation.get("coordinates");
				String LAT = (String) String.valueOf(coordinates.get(1));
				String LON = (String) String.valueOf(coordinates.get(0));
				resultList.add(new GazetteerData(URI, NAME, LAT, LON, "daigazetteer"));
			}
		} else {
			resultList.add(new GazetteerData("", "DAI Gazetteer Server Error", "0", "0", ""));
		}
		return resultList;
	}

	public static GazetteerData getgeoJSONFromDaiGazetteer(String id) throws Exception {
		boolean dai = true;
		String url = "";
		if (id.contains("getWorldJSON") || id.equals("2042600")) {
			url = "http://"+host+"/stc/getWorldJSON";
			dai = false;
		} else {
			url = "https://gazetteer.dainst.org/doc/#id#.geojson";
		}
		url = url.replace("#id#", id);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setInstanceFollowRedirects(true);
		HttpURLConnection.setFollowRedirects(true);
		int responseCode = con.getResponseCode();
		System.out.println("DAI Gazetteer Response Code : " + responseCode + " - " + url);
		if (responseCode < 400) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// create object
			GazetteerData gd = null;
			String URI = "";
			String NAME = "";
			String GEOMETRY = "";
			if (response != null && !response.toString().equals("")) {
				JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
				if (dai) {
					// parse geometries
					JSONObject geometry = (JSONObject) jsonObject.get("geometry");
					JSONArray geometries = (JSONArray) geometry.get("geometries");
					for (Object element : geometries) {
						JSONObject geomEntry = (JSONObject) element;
						// use only multipolygons
						if (geomEntry.get("type").equals("MultiPolygon")) {
							GEOMETRY = (String) geomEntry.toString();
						} else if (geomEntry.get("type").equals("Point")) {
							GEOMETRY = (String) geomEntry.toString();
						}
					}
					JSONObject properties = (JSONObject) jsonObject.get("properties");
					URI = (String) properties.get("@id");
					JSONObject prefName = (JSONObject) properties.get("prefName");
					NAME = (String) prefName.get("title");
					gd = new GazetteerData(URI, NAME, GEOMETRY, "daigazetteer");
				} else {
					// parse geometries
					JSONArray features = (JSONArray) jsonObject.get("features");
					for (Object element : features) {
						JSONObject geomEntry = (JSONObject) element;
						GEOMETRY = (String) geomEntry.get("geometry").toString();
					}
					URI = "	http://gazetteer.dainst.org/place/2042600";
					NAME = "Welt";
					gd = new GazetteerData(URI, NAME, GEOMETRY, "daigazetteer");
				}
			}
			return gd;
		} else {
			return null;
		}
	}

	public static List<GazetteerData> ParseDAIgeoJSON(StringBuffer json) throws IOException, ParseException {
		List<GazetteerData> resultList = new ArrayList<GazetteerData>();
		String URI = "";
		String NAME = "";
		String GEOMETRY = "";
		if (json != null && !json.toString().equals("")) {
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(json.toString());
			JSONObject geometry = (JSONObject) jsonObject.get("geometry");
			GEOMETRY = (String) geometry.toString();
			JSONObject properties = (JSONObject) jsonObject.get("properties");
			URI = (String) properties.get("@id");
			JSONObject prefName = (JSONObject) properties.get("prefName");
			NAME = (String) prefName.get("title");
			resultList.add(new GazetteerData(URI, NAME, GEOMETRY, "daigazetteer"));
		} else {
			resultList.add(new GazetteerData("", "DAI Gazetteer Server Error", "0", "0", ""));
		}
		return resultList;
	}

}
