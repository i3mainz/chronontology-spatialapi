package de.i3mainz.chronontology.rest;

import de.i3mainz.chronontology.errorlog.Logging;
import de.i3mainz.chronontology.pom.POM;
import de.i3mainz.chronontology.restconfig.ResponseGZIP;
import java.io.IOException;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class InfoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getInfo() {
        try {
            return ResponseGZIP.setResponse("gzip", POM.getInfo().toJSONString(), Response.Status.OK);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.InfoResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

}
