package functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tools.world;

public class ChronOntology {

	public static JSONArray getSpatialData(String uri) throws Exception {
		// init output
		JSONArray spatialData = new JSONArray();
		// get data from chronontology
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
			JSONObject data = (JSONObject) new JSONParser().parse(response.toString());
			JSONObject resource = (JSONObject) data.get("resource");
			String[] types = {"spatiallyPartOfRegion", "isNamedAfter", "hasCoreArea"};
			for (String item : types) {
				JSONArray spatial = (JSONArray) resource.get(item);
				if (spatial != null) {
					for (Object element : spatial) {
						URL daiURL = new URL("https://gazetteer.dainst.org/doc/" + element.toString().replace("http://gazetteer.dainst.org/place/", "") + ".geojson");
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
							while (geometriesDAI.size() == 0) {
								// of geometry is empty get geometry from parent and loop it
								String parentURLStrOrigin = parentURLStr;
								parentURLStr = parentURLStr.replace("/place/", "/doc/");
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
										parentGeometry.put("url", parentURLStrOrigin);
										parentGeometry.put("name", prefNameTitleParent);
										geometriesDAI = geometriesDAIparent;
									}
								}
							}
							JSONObject feature = new JSONObject();
							feature.put("type", "Feature");
							JSONObject properties = new JSONObject();
							properties.put("name", (String) prefName.get("title"));
							properties.put("relation", item);
							properties.put("homepage", (String) dataDAI.get("id"));
							if (parentGeometry.size() == 0) {
								properties.put("parentGeometry", false);
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
							spatialData.add(feature);
						}
					}
				}
			}
		}
		// if no geom available load world json
		if (spatialData.size() == 0) {
			BufferedReader reader = new BufferedReader(new FileReader(ChronOntology.class.getClassLoader().getResource("world.json").getFile()));
			String line;
			String json = "";
			while ((line = reader.readLine()) != null) {
				json += line;
			}
			JSONObject dataWORLD = (JSONObject) new JSONParser().parse(json.toString());
			JSONArray featureWorldArray = (JSONArray) dataWORLD.get("features");
			JSONObject featureWorld = (JSONObject) featureWorldArray.get(0);
			JSONObject feature = new JSONObject();
			feature.put("type", "Feature");
			JSONObject properties = new JSONObject();
			properties.put("name", "world");
			properties.put("relation", "none");
			properties.put("homepage", "none");
			feature.put("properties", properties);
			feature.put("geometry", dataWORLD);
			spatialData.add(featureWorld);
		}
		return spatialData;
	}

}
