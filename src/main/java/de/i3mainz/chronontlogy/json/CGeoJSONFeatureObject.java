package de.i3mainz.chronontlogy.json;

import org.json.simple.JSONObject;

/**
 * JSONObject to store single feature information for CGeoJON objects
 *
 * @author Florian Thiery
 */
public class CGeoJSONFeatureObject extends GGeoJSONObject {

    public CGeoJSONFeatureObject() {
        super();
    }

    /**
     * set GeoJSON single feature properties
     *
     * @param url
     * @param gazetteerid
     * @param type
     * @param names
     * @param relation
     */
    public void setProperties(String url, String gazetteerid, String type, String relation) {
        JSONObject properties = new JSONObject();
        super.remove("properties");
        properties.put("@id", url);
        properties.put("gazetteerid", gazetteerid);
        properties.put("type", type);
        properties.put("relation", relation);
        super.put("properties", properties);
    }

}
