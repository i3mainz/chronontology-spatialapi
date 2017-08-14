package de.i3mainz.chronontology.pom;

import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * Class for POM details
 * @author thiery
 */
public class POM {
    
    /**
     * get POM info as JSON
     * @return pom JSON
     * @throws IOException 
     */
    public static JSONObject getInfo() throws IOException {
        JSONObject outObj = new JSONObject();
        JSONObject maven = new JSONObject();
        maven.put("modelVersion", ConfigProperties.getPropertyParam("modelVersion"));
        outObj.put("maven", maven);
        JSONObject project = new JSONObject();
        project.put("buildNumber", ConfigProperties.getPropertyParam("buildNumber"));
        project.put("buildNumberShort", ConfigProperties.getPropertyParam("buildNumber").substring(0, 7));
        project.put("buildRepository", ConfigProperties.getPropertyParam("url").replace(".git", "/tree/" + ConfigProperties.getPropertyParam("buildNumber")));
        project.put("artifactId", ConfigProperties.getPropertyParam("artifactId"));
        project.put("groupId", ConfigProperties.getPropertyParam("groupId"));
        project.put("version", ConfigProperties.getPropertyParam("version"));
        project.put("packaging", ConfigProperties.getPropertyParam("packaging"));
        project.put("name", ConfigProperties.getPropertyParam("name"));
        project.put("description", ConfigProperties.getPropertyParam("description"));
        project.put("url", ConfigProperties.getPropertyParam("url"));
        project.put("encoding", ConfigProperties.getPropertyParam("sourceEncoding"));
        outObj.put("project", project);
        return outObj;
    }

}
