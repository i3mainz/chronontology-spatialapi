package de.i3mainz.chronontology.rest;

import de.i3mainz.chronontology.restconfig.ResponseGZIP;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

@Path("/geojson")
public class GeojsonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getInfo() {
        return ResponseGZIP.setResponse("gzip", new JSONObject().toJSONString(), Response.Status.OK);
    }

}
