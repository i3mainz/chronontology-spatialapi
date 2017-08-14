package de.i3mainz.chronontology.gazetteer;

import org.json.simple.JSONObject;

/**
 * JSONObject to store gazetter search results
 * @author Florian Thiery
 */
public class GJSONObject {
    
    private static JSONObject gjson;
    
    public GJSONObject() {
        gjson = new JSONObject();
    }
    
}
