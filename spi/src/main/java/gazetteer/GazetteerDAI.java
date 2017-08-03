package gazetteer;

import classes.GazetteerData;
import static chronontology.ChronOntology.GAZETTEER_DATA_INTERFACE;
import static chronontology.ChronOntology.GAZETTEER_HOST;
import static chronontology.ChronOntology.GAZETTEER_RESOURCE_INTERFACE;
import static chronontology.ChronOntology.WELT_URI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
			url = "http://" + host + "/stc/getWorldJSON";
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

	/*public static void bla() throws MalformedURLException {
		String url = "";
		url = "https://gazetteer.dainst.org/doc/#id#.geojson";
		url = url.replace("#id#", "2052457");
		URL daiURL = new URL(url);
		HttpURLConnection con2 = (HttpURLConnection) daiURL.openConnection();
		con2.setRequestMethod("GET");
		con2.setRequestProperty("Accept", "application/json");
		if (con2.getResponseCode() < 400) {
			BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream(), "UTF-8"));
			String inputLine2;
			StringBuilder response2 = new StringBuilder();
			while ((inputLine2 = in2.readLine()) != null) {
				response2.append(inputLine2);
			}
			in2.close();
			// parse data
			JSONObject dataDAI = (JSONObject) new JSONParser().parse(response2.toString());
			JSONObject propertiesDAI = (JSONObject) dataDAI.get("properties");
			JSONObject prefName = (JSONObject) propertiesDAI.get("prefName");
			String parentURLStr = (String) propertiesDAI.get("parent");
			JSONObject geometryDAI = (JSONObject) dataDAI.get("geometry");
			JSONArray geometriesDAI = (JSONArray) geometryDAI.get("geometries");
			JSONObject parentGeometry = new JSONObject();
			while (geometriesDAI.isEmpty()) {
				// of geometry is empty get geometry from parent and loop it
				String parentURLStrOrigin = parentURLStr;
				// if URI is "Welt" quit loop
				if (parentURLStrOrigin.equals(WELT_URI)) {
					// set spatialData to length zero if uri is "Welt"
					spatialData = new JSONArray();
					break;
				}
				parentURLStr = parentURLStr.replace("/" + GAZETTEER_RESOURCE_INTERFACE + "/", "/" + GAZETTEER_DATA_INTERFACE + "/");
				parentURLStr += ".geojson";
				URL parentURL = new URL(parentURLStr);
				HttpURLConnection con3 = (HttpURLConnection) parentURL.openConnection();
				con3.setRequestMethod("GET");
				con3.setRequestProperty("Accept", "application/json");
				if (con3.getResponseCode() < 400) {
					BufferedReader in3 = new BufferedReader(new InputStreamReader(con3.getInputStream(), "UTF-8"));
					String inputLine3;
					StringBuilder response3 = new StringBuilder();
					while ((inputLine3 = in3.readLine()) != null) {
						response3.append(inputLine3);
					}
					in3.close();
					// parse data
					JSONObject dataDAIparent = (JSONObject) new JSONParser().parse(response3.toString());
					JSONObject geometryDAIparent = (JSONObject) dataDAIparent.get("geometry");
					JSONArray geometriesDAIparent = (JSONArray) geometryDAIparent.get("geometries");
					JSONObject propertiesDAIparent = (JSONObject) dataDAIparent.get("properties");
					JSONObject prefNameParent = (JSONObject) propertiesDAIparent.get("prefName");
					String prefNameTitleParent = (String) prefNameParent.get("title");
					parentURLStr = (String) propertiesDAIparent.get("parent");
					if (geometriesDAIparent.size() > 0) {
						parentGeometry.put("uri", parentURLStrOrigin);
						String[] idSplit = parentURLStrOrigin.split("/");
						parentGeometry.put("id", idSplit[idSplit.length - 1]);
						parentGeometry.put("name", prefNameTitleParent);
						geometriesDAI = geometriesDAIparent;
					}
				}
			}
			// create output geojson
			JSONObject feature = new JSONObject();
			feature.put("type", "Feature");
			JSONObject properties = new JSONObject();
			properties.put("name", (String) prefName.get("title"));
			properties.put("relation", item);
			properties.put("uri", (String) dataDAI.get("id"));
			String idStr = (String) dataDAI.get("id");
			String[] idSplit = idStr.split("/");
			properties.put("id", idSplit[idSplit.length - 1]);
			if (parentGeometry.isEmpty()) {
				parentGeometry.put("uri", null);
				parentGeometry.put("id", null);
				parentGeometry.put("name", "geom origin");
				properties.put("parentGeometry", parentGeometry);
			} else {
				properties.put("parentGeometry", parentGeometry);
			}
			feature.put("properties", properties);
			for (Object geom : geometriesDAI) {
				JSONObject geomEntry = (JSONObject) geom;
				if (geomEntry.get("type").equals("MultiPolygon")) {
					feature.put("geometry", geomEntry);
				} else if (geomEntry.get("type").equals("Point")) {
					feature.put("geometry", geomEntry);
				}
			}
			if (geometriesDAI.isEmpty()) {
				BufferedReader reader = new BufferedReader(new FileReader(ChronOntology.class.getClassLoader().getResource("world.json").getFile()));
				String line;
				String json = "";
				while ((line = reader.readLine()) != null) {
					json += line;
				}
				JSONObject dataWORLD = (JSONObject) new JSONParser().parse(json.toString());
				JSONArray featureWorldArray = (JSONArray) dataWORLD.get("features");
				JSONObject featureWorld = (JSONObject) featureWorldArray.get(0);
				feature.put("geometry", featureWorld.get("geometry"));
				JSONObject propertiesWorld = (JSONObject) feature.get("properties");
				propertiesWorld.remove("parentGeometry");
				JSONObject parentGeometryWorld = new JSONObject();
				parentGeometryWorld.put("uri", "https://gazetteer.dainst.org/place/2042600");
				parentGeometryWorld.put("id", "2042600");
				parentGeometryWorld.put("name", "Welt");
				propertiesWorld.put("parentGeometry", parentGeometryWorld);
			}
			spatialData.add(feature);
		}
	}

	public static GazetteerData getGeoJSON(String id) throws Exception {
		String url = "";
		url = "https://gazetteer.dainst.org/doc/#id#.geojson";
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
			}
			return gd;
		} else {
			return null;
		}
	}*/

}
