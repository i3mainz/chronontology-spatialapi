package de.i3mainz.chronontology.chronontology;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSONObject to represent spatial data of a chronontology period
 * @author Florian Thiery
 */
public class CGeoJSONObject extends JSONObject {
    
    private JSONObject geojson = new JSONObject();
    private String type = "";
    private JSONArray features = new JSONArray();
    private JSONObject metadata = new JSONObject();
    
    public CGeoJSONObject() {
        super();
        geojson = new JSONObject();
        geojson.put("type", type);
        geojson.put("features", features);
        geojson.put("metadata", metadata);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONArray getFeatures() {
        return features;
    }

    public void setFeatures(JSONArray features) {
        this.features = features;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }
    
}
