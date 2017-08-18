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
     * @param gazetteertype
     * @param names
     * @param gazetteerrelation
     */
    public void setProperties(String url, String gazetteerid, String gazetteertype, String gazetteerrelation) {
        JSONObject properties = new JSONObject();
        super.remove("properties");
        properties.put("@id", url);
        properties.put("gazetteerid", gazetteerid);
        properties.put("gazetteertype", gazetteertype);
        properties.put("gazetteerrelation", gazetteerrelation);
        super.put("properties", properties);
    }

}
