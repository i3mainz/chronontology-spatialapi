package functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ChronOntology {

	public static final String WELT_URI = "https://gazetteer.dainst.org/place/2042600";
	public static final String[] TYPES = {"spatiallyPartOfRegion", "isNamedAfter", "hasCoreArea"};
	public static final String GAZETTEER_HOST = "https://gazetteer.dainst.org";
	public static final String GAZETTEER_DATA_INTERFACE = "doc";
	public static final String GAZETTEER_RESOURCE_INTERFACE = "place";

	public static JSONArray getSpatialData(String uri) throws Exception {
		// init output
		JSONArray spatialData = new JSONArray();
		// get data from chronontology
		JSONObject data = null;
		JSONObject resource = null;
		String url = uri.replace("period", "data/period");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		if (con.getResponseCode() == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// parse data
			data = (JSONObject) new JSONParser().parse(response.toString());
			resource = (JSONObject) data.get("resource");
			for (String item : TYPES) {
				JSONArray spatial = (JSONArray) resource.get(item);
				if (spatial != null) {
					JSONArray spatialHTTPS = new JSONArray();
					for (Object element : spatial) {
						String tmp = element.toString().replace("http://", "https://");
						spatialHTTPS.add(tmp);
					}
					for (Object element : spatialHTTPS) {
						URL daiURL = new URL(GAZETTEER_HOST + "/" + GAZETTEER_DATA_INTERFACE + "/" + element.toString().replace(GAZETTEER_HOST + "/" + GAZETTEER_RESOURCE_INTERFACE + "/", "") + ".geojson");
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
							// add GeoJSON-T see https://github.com/kgeographer/geojson-t#adding-time
							feature.put("id", resource.get("id"));
							feature.put("when", new JSONObject());
							JSONObject properties = new JSONObject();
							properties.put("data", data);
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
				}
			}
		}
		// if no 200 OK available
		if (con.getResponseCode() > 200) {
			spatialData = new JSONArray();
			return spatialData;
		}
		// if no geom available load world json
		if (spatialData.isEmpty()) {
			BufferedReader reader = new BufferedReader(new FileReader(ChronOntology.class.getClassLoader().getResource("world.json").getFile()));
			String line;
			String json = "";
			while ((line = reader.readLine()) != null) {
				json += line;
			}
			JSONObject dataWORLD = (JSONObject) new JSONParser().parse(json.toString());
			JSONArray featureWorldArray = (JSONArray) dataWORLD.get("features");
			JSONObject featureWorld = (JSONObject) featureWorldArray.get(0);
			JSONObject properties = new JSONObject();
			properties.put("data", data);
			JSONObject parentGeometry = new JSONObject();
			parentGeometry.put("uri", null);
			parentGeometry.put("id", null);
			parentGeometry.put("name", "geom origin");
			properties.put("parentGeometry", parentGeometry);
			properties.put("name", "world");
			properties.put("relation", "unknown");
			properties.put("uri", "https://gazetteer.dainst.org/place/2042600");
			properties.put("id", "2042600");
			featureWorld.remove("properties");
			featureWorld.put("properties", properties);
			// add GeoJSON-T see https://github.com/kgeographer/geojson-t#adding-time
			featureWorld.put("id", resource.get("id"));
			featureWorld.put("when", new JSONObject());
			spatialData.add(featureWorld);
		}
		return spatialData;
	}

}
