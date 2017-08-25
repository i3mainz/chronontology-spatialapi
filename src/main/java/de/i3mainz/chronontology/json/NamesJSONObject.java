package de.i3mainz.chronontology.json;

import java.util.HashSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSONObject to store names information
 *
 * @author Florian Thiery
 */
public class NamesJSONObject extends JSONObject {

    public NamesJSONObject() {
        super();
    }

    /**
     * set multiple names for one language
     * @param language
     * @param names 
     */
    public void setName(String language, HashSet names) {
        JSONArray namesArray = new JSONArray();
        for (Object item : names) {
            String tmp = (String) item;
            namesArray.add(tmp);
        }
        super.put(language, namesArray);
    }
    
    /**
     * get array of names by language
     * @param language
     * @return 
     */
    public JSONArray getNamesByLanguage(String language) {
        return (JSONArray) super.get(language);
    }

}
