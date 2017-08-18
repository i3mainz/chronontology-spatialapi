package de.i3mainz.chronontlogy.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSONObject to represent spatial data of a chronontology period
 *
 * @author Florian Thiery
 */
public class CGeoJSONObject extends JSONObject {

    public CGeoJSONObject() {
        super();
        super.put("type", "FeatureCollection");
        super.put("features", new JSONArray());
        super.put("metadata", new JSONObject());
    }

    /**
     * set GeoJSON features
     *
     * @param geometry
     */
    public void setFeature(JSONObject feature) {
        JSONArray features = (JSONArray) super.get("features");
        features.add(feature);
        super.remove("features");
        super.put("features", features);
    }

    /**
     * get features
     *
     * @return features json object
     */
    public JSONObject getFeatures() {
        return (JSONObject) super.get("features");
    }

    /**
     * set metadata
     *
     * @param periodid
     * @param chronontology
     * @param when
     * @param names
     */
    public void setMetadata(String url, String periodid, JSONObject chronontology, JSONArray when, JSONObject names) {
        JSONObject metadata = new JSONObject();
        super.remove("metadata");
        metadata.put("@id", url);
        metadata.put("periodid", periodid);
        metadata.put("chronontology", chronontology);
        metadata.put("when", when);
        metadata.put("names", names);
        super.put("metadata", metadata);
    }

    /**
     * get metadata
     *
     * @return metadata json object
     */
    public JSONObject getMetadata() {
        return (JSONObject) super.get("metadata");
    }

}
