package de.i3mainz.chronontlogy.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * JSONObject to store gazetter search results
 *
 * @author Florian Thiery
 */
public class GGeoJSONObject extends JSONObject {

    public GGeoJSONObject() {
        super();
        super.put("type", "Feature");
        super.put("geometry", new JSONObject());
        super.put("properties", new JSONObject());
    }

    /**
     * set GeoJSON geometry
     *
     * @param geometry
     */
    public void setGeometry(JSONObject geometry) {
        super.remove("geometry");
        super.put("geometry", geometry);
    }

    /**
     * get geometry
     *
     * @return geometry json object
     */
    public JSONObject getGeometry() {
        return (JSONObject) super.get("geometry");
    }

    /**
     * set GeoJSON properties
     *
     * @param url
     * @param gazetteerid
     * @param type
     * @param names
     */
    public void setProperties(String url, String gazetteerid, String type, NamesJSONObject names) {
        JSONObject properties = new JSONObject();
        super.remove("properties");
        properties.put("@id", url);
        properties.put("gazetteerid", gazetteerid);
        properties.put("type", type);
        properties.put("names", names);
        super.put("properties", properties);
    }

    /**
     * get properties
     *
     * @return properties json object
     */
    public JSONObject getProperties() {
        return (JSONObject) super.get("properties");
    }

    public JSONObject getJSONLD() throws IOException {
        try {
            JSONObject jsonld = this;
            JSONObject contexts = new JSONObject();
            // read GeoJSON-LD Context
            JSONObject data = new JSONObject();
            URL obj = new URL("https://raw.githubusercontent.com/geojson/geojson-ld/gh-pages/geojson-context.jsonld");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                data = (JSONObject) new JSONParser().parse(response.toString());
            }
            JSONObject context = (JSONObject) data.get("@context");
            contexts.putAll(context);
            jsonld.put("@context", context);
            // read GeoJSON-LD-LG Context
            obj = new URL("https://raw.githubusercontent.com/florianthiery/geojson-ld-lg/master/geojson-context-lg.jsonld");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                data = (JSONObject) new JSONParser().parse(response.toString());
            }
            context = (JSONObject) data.get("@context");
            contexts.putAll(context);
            jsonld.put("@context", contexts);
            // delete big properties for testing
            jsonld.remove("geometry");
            jsonld.remove("properties");
            // set test properties from geojson-lg
            jsonld.put("relation", "test");
            jsonld.put("metadata", new JSONObject());
            return jsonld;
        } catch (Exception e) {
            return null;
        }
    }

}
