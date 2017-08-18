package de.i3mainz.chronontology.rest;

import de.i3mainz.chronontology.chronontology.ChronOntology;
import de.i3mainz.chronontology.errorlog.Logging;
import de.i3mainz.chronontology.restconfig.ResponseGZIP;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/place")
public class PlaceResource {

    /**
     * creates a GeoJSON out of a query
     *
     * @param acceptEncoding
     * @param acceptHeader
     * @param periodid
     * @param bbox
     * @param q
     * @param dummyFeature
     * @param dummyType
     * @return GeoJSON
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getGeoJSON(@HeaderParam("Accept-Encoding") String acceptEncoding,
                               @HeaderParam("Accept") String acceptHeader,
                               @QueryParam("periodid") String periodid,
                               @QueryParam("bbox") String bbox,
                               @QueryParam("q") String q,
                               @QueryParam("dummy") String dummyFeature,
                               @QueryParam("type") String dummyType) {
        try {
            JSONObject geojson = new JSONObject();
            if (periodid != null) {
                if (!periodid.equals("")) {
                    geojson.put("type", "FeatureCollection");
                    geojson.put("features", ChronOntology.getGeoJSON(periodid));
                    return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
                } else {
                    geojson.put("type", "FeatureCollection");
                    geojson.put("features", new JSONArray());
                    return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
                }
            } else if (bbox != null) {
                if (!bbox.equals("")) {
                    geojson.put("type", "FeatureCollection");
                    // TODO query gazetteers for bbox
                    geojson.put("features", new JSONArray());
                    return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
                } else {
                    geojson.put("type", "FeatureCollection");
                    geojson.put("features", new JSONArray());
                    return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
                }
            } else if (q != null) {
                if (!q.equals("")) {
                    geojson.put("type", "FeatureCollection");
                    // TODO query gazetteers for string
                    geojson.put("features", new JSONArray());
                    return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
                } else {
                    geojson.put("type", "FeatureCollection");
                    geojson.put("features", new JSONArray());
                    return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
                }
            } else {
                geojson.put("type", "FeatureCollection");
                geojson.put("features", new JSONArray());
                return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.PlaceResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getGeoJSONfromGazetteer(@HeaderParam("Accept-Encoding") String acceptEncoding,
                                            @HeaderParam("Accept") String acceptHeader,
                                            @PathParam("type") String type,
                                            @PathParam("id") String id) {
        try {
            JSONObject geojson = new JSONObject();
            // TODO query gazetteers
            geojson.put("type", type);
            geojson.put("id", id);
            return ResponseGZIP.setResponse(acceptEncoding, geojson.toJSONString(), Response.Status.OK);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.PlaceResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

}
