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

    public static JSONArray getGeoJSON(String id) throws Exception {
        // init output
        JSONArray spatialData = new JSONArray();
        // get data from chronontology
        JSONObject data = null;
        JSONObject resource = null;
        String url = id = "http://chronontology.dainst.org/data/period/" + id;
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
                            JSONObject properties = new JSONObject();
                            properties.put("chronontology", data);
                            properties.put("name", (String) prefName.get("title"));
                            properties.put("relation", item);
                            properties.put("uri", (String) dataDAI.get("id"));
                            String idStr = (String) dataDAI.get("id");
                            String[] idSplit = idStr.split("/");
                            properties.put("id", idSplit[idSplit.length - 1]);
                            if (!parentGeometry.isEmpty()) {
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
            properties.put("chronontology", data);
            properties.put("name", "world");
            properties.put("relation", "unknown");
            properties.put("uri", "https://gazetteer.dainst.org/place/2042600");
            properties.put("id", "2042600");
            featureWorld.remove("properties");
            featureWorld.put("properties", properties);
            // add GeoJSON-T see https://github.com/kgeographer/geojson-t#adding-time
            spatialData.add(featureWorld);
        }
        return spatialData;
    }

    public static JSONArray getGeoJSONDummy(boolean multi) throws Exception {
        // example geoms from https://github.com/johan/world.geo.json/tree/master/countries
        JSONArray spatialData = new JSONArray();
        //feature 1
        JSONObject feature1 = new JSONObject();
        feature1.put("type", "Feature");
        JSONParser parser = new JSONParser();
        JSONObject geom1 = (JSONObject) parser.parse("{\"type\":\"Polygon\",\"coordinates\":[[[9.921906,54.983104],[9.93958,54.596642],[10.950112,54.363607],[10.939467,54.008693],[11.956252,54.196486],[12.51844,54.470371],[13.647467,54.075511],[14.119686,53.757029],[14.353315,53.248171],[14.074521,52.981263],[14.4376,52.62485],[14.685026,52.089947],[14.607098,51.745188],[15.016996,51.106674],[14.570718,51.002339],[14.307013,51.117268],[14.056228,50.926918],[13.338132,50.733234],[12.966837,50.484076],[12.240111,50.266338],[12.415191,49.969121],[12.521024,49.547415],[13.031329,49.307068],[13.595946,48.877172],[13.243357,48.416115],[12.884103,48.289146],[13.025851,47.637584],[12.932627,47.467646],[12.62076,47.672388],[12.141357,47.703083],[11.426414,47.523766],[10.544504,47.566399],[10.402084,47.302488],[9.896068,47.580197],[9.594226,47.525058],[8.522612,47.830828],[8.317301,47.61358],[7.466759,47.620582],[7.593676,48.333019],[8.099279,49.017784],[6.65823,49.201958],[6.18632,49.463803],[6.242751,49.902226],[6.043073,50.128052],[6.156658,50.803721],[5.988658,51.851616],[6.589397,51.852029],[6.84287,52.22844],[7.092053,53.144043],[6.90514,53.482162],[7.100425,53.693932],[7.936239,53.748296],[8.121706,53.527792],[8.800734,54.020786],[8.572118,54.395646],[8.526229,54.962744],[9.282049,54.830865],[9.921906,54.983104]]]}");
        feature1.put("geometry", geom1);
        JSONObject properties1 = new JSONObject();
        properties1.put("periodid", "xyz");
        JSONObject names1 = new JSONObject();
        JSONArray en1 = new JSONArray();
        en1.add("roman");
        names1.put("en", en1);
        JSONArray de1 = new JSONArray();
        de1.add("römisch");
        de1.add("Römisch");
        names1.put("de", de1);
        properties1.put("names", names1);
        properties1.put("relation", "hasCoreArea");
        properties1.put("@id", "https://gazetteer.dainst.org/place/abc");
        properties1.put("chronontology", new JSONObject());
        feature1.put("properties", properties1);
        spatialData.add(feature1);
        if (multi) {
            //feature 1
            JSONObject feature2 = new JSONObject();
            feature2.put("type", "Feature");
            JSONParser parser2 = new JSONParser();
            JSONObject geom2 = (JSONObject) parser2.parse("{\"type\":\"Polygon\",\"coordinates\":[[[-9.034818,41.880571],[-8.984433,42.592775],[-9.392884,43.026625],[-7.97819,43.748338],[-6.754492,43.567909],[-5.411886,43.57424],[-4.347843,43.403449],[-3.517532,43.455901],[-1.901351,43.422802],[-1.502771,43.034014],[0.338047,42.579546],[0.701591,42.795734],[1.826793,42.343385],[2.985999,42.473015],[3.039484,41.89212],[2.091842,41.226089],[0.810525,41.014732],[0.721331,40.678318],[0.106692,40.123934],[-0.278711,39.309978],[0.111291,38.738514],[-0.467124,38.292366],[-0.683389,37.642354],[-1.438382,37.443064],[-2.146453,36.674144],[-3.415781,36.6589],[-4.368901,36.677839],[-4.995219,36.324708],[-5.37716,35.94685],[-5.866432,36.029817],[-6.236694,36.367677],[-6.520191,36.942913],[-7.453726,37.097788],[-7.537105,37.428904],[-7.166508,37.803894],[-7.029281,38.075764],[-7.374092,38.373059],[-7.098037,39.030073],[-7.498632,39.629571],[-7.066592,39.711892],[-7.026413,40.184524],[-6.86402,40.330872],[-6.851127,41.111083],[-6.389088,41.381815],[-6.668606,41.883387],[-7.251309,41.918346],[-7.422513,41.792075],[-8.013175,41.790886],[-8.263857,42.280469],[-8.671946,42.134689],[-9.034818,41.880571]]]}");
            feature2.put("geometry", geom2);
            JSONObject properties2 = new JSONObject();
            properties2.put("periodid", "xyz");
            JSONObject names2 = new JSONObject();
            JSONArray en2 = new JSONArray();
            en2.add("freek");
            names1.put("en", en2);
            JSONArray de2 = new JSONArray();
            de2.add("griechisch");
            de2.add("Griechisch");
            names2.put("de", de1);
            properties2.put("names", names1);
            properties2.put("relation", "spatiallyLocatedIn");
            properties2.put("@id", "https://gazetteer.dainst.org/place/def");
            properties2.put("chronontology", new JSONObject());
            feature2.put("properties", properties2);
            spatialData.add(feature2);
        }
        return spatialData;
    }

}
