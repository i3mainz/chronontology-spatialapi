package de.i3mainz.chronontology.rest;

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
import org.linkedgeodesy.gazetteerjson.gazetteer.GeoNames;
import org.linkedgeodesy.gazetteerjson.gazetteer.GettyTGN;
import org.linkedgeodesy.gazetteerjson.gazetteer.IDAIGazetteer;
import org.linkedgeodesy.gazetteerjson.gazetteer.Pleiades;
import org.linkedgeodesy.gazetteerjson.gazetteer.ChronOntology;
import org.linkedgeodesy.org.gazetteerjson.json.GGeoJSONSingleFeature;

@Path("/place")
public class PlaceResource {

    /**
     * creates as GeoJSON+
     *
     * @param acceptEncoding
     * @param acceptHeader
     * @param periodid
     * @param bbox
     * @param q
     * @param type
     * @return
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getGeoJSON(@HeaderParam("Accept-Encoding") String acceptEncoding,
                               @HeaderParam("Accept") String acceptHeader,
                               @QueryParam("periodid") String periodid,
                               @QueryParam("bbox") String bbox,
                               @QueryParam("q") String q,
                               @QueryParam("type") String type) {
        try {
            String out = "{}";
            GGeoJSONSingleFeature dummyjson = new GGeoJSONSingleFeature();
            if (periodid != null) {
                if (!periodid.equals("")) {
                    out = ChronOntology.getPlacesById(periodid).toJSONString();
                } else {
                    out = dummyjson.toJSONString();
                }
            } else if (bbox != null) {
                String[] bboxSplit = bbox.split(";");
                if (!bbox.equals("")) {
                    if (bboxSplit.length == 8) {
                        if (type.equals("dai")) {
                            out = IDAIGazetteer.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                        } else if (type.equals("geonames")) {
                            out = GeoNames.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                        } else if (type.equals("getty")) {
                            out = GettyTGN.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                        } else if (type.equals("pleiades")) {
                            out = Pleiades.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                        } else {
                            out = dummyjson.toJSONString();
                        }
                    } else {
                        out = dummyjson.toJSONString();
                    }
                } else {
                    out = dummyjson.toJSONString();
                }
            } else if (q != null) {
                if (!q.equals("")) {
                    if (type.equals("dai")) {
                        out = IDAIGazetteer.getPlacesByString(q).toJSONString();
                    } else if (type.equals("geonames")) {
                        out = GeoNames.getPlacesByString(q).toJSONString();
                    } else if (type.equals("getty")) {
                        out = GettyTGN.getPlacesByString(q).toJSONString();
                    } else if (type.equals("pleiades")) {
                        out = Pleiades.getPlacesByString(q).toJSONString();
                    } else {
                        out = dummyjson.toJSONString();
                    }
                } else {
                    out = dummyjson.toJSONString();
                }
            } else {
                out = dummyjson.toJSONString();
            }
            return ResponseGZIP.setResponse(acceptEncoding, out, Response.Status.OK);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.PlaceResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    /**
     * creates as GeoJSON+
     *
     * @param acceptEncoding
     * @param acceptHeader
     * @param type
     * @param id
     * @return
     */
    @GET
    @Path("/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getGeoJSONfromGazetteer(@HeaderParam("Accept-Encoding") String acceptEncoding,
                                            @HeaderParam("Accept") String acceptHeader,
                                            @PathParam("type") String type,
                                            @PathParam("id") String id) {
        try {
            String out = "{}";
            if (type.equals("dai")) {
                out = IDAIGazetteer.getPlaceById(id).toJSONString();
            } else if (type.equals("geonames")) {
                out = GeoNames.getPlaceById(id).toJSONString();
            } else if (type.equals("getty")) {
                out = GettyTGN.getPlaceById(id).toJSONString();
            } else if (type.equals("pleiades")) {
                out = Pleiades.getPlaceById(id).toJSONString();
            } else if (type.equals("chronontology")) {
                out = ChronOntology.getPlacesById(id).toJSONString();
            }
            return ResponseGZIP.setResponse(acceptEncoding, out, Response.Status.OK);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.PlaceResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

}
