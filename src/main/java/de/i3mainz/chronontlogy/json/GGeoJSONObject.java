package de.i3mainz.chronontlogy.json;

import org.json.simple.JSONObject;

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
     * @param geometry 
     */
    public void setGeometry(JSONObject geometry) {
        super.remove("geometry");
        super.put("geometry", geometry);
    }
    
    /**
     * get geometry
     * @return geometry json object
     */
    public JSONObject getGeometry() {
        return (JSONObject) super.get("geometry");
    }

    /**
     * set GeoJSON properties
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
     * @return properties json object
     */
    public JSONObject getProperties() {
        return (JSONObject) super.get("properties");
    }

}
