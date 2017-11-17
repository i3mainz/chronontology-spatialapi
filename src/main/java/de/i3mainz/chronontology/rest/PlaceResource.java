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
import org.linkedgeodesy.org.gazetteerjson.json.JSONLD;

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
                               @QueryParam("type") String type,
                               @QueryParam("format") String format) {
        try {
            String out = "{}";
            GGeoJSONSingleFeature dummyjson = new GGeoJSONSingleFeature();
            if (format == null) {
                format = "json";
            }
            format = transformFormat(format);
            if (periodid != null) {
                if (!periodid.equals("")) {
                    if (format == "json") {
                        out = ChronOntology.getPlacesById(periodid).toJSONString();
                    } else if (format == "jsonld") {
                        out = JSONLD.getJSONLDChronOntologyJSON(ChronOntology.getPlacesById(periodid)).toJSONString();
                    } else {
                        out = JSONLD.getRDF(JSONLD.getJSONLDChronOntologyJSON(ChronOntology.getPlacesById(periodid)).toString(), format);
                    }
                } else {
                    out = dummyjson.toJSONString();
                }
            } else if (bbox != null) {
                String[] bboxSplit = bbox.split(";");
                if (!bbox.equals("")) {
                    if (bboxSplit.length == 8) {
                        if (type.equals("dai")) {
                            if (format == "json") {
                                out = IDAIGazetteer.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                            } else if (format == "jsonld") {
                                out = JSONLD.getJSONLDGazetteerSearch(IDAIGazetteer.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toJSONString();
                            } else {
                                out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(IDAIGazetteer.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toString(), format);
                            }
                        } else if (type.equals("geonames")) {
                            if (format == "json") {
                                out = GeoNames.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                            } else if (format == "jsonld") {
                                out = JSONLD.getJSONLDGazetteerSearch(GeoNames.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toJSONString();
                            } else {
                                out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(GeoNames.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toString(), format);
                            }
                        } else if (type.equals("getty")) {
                            if (format == "json") {
                                out = GettyTGN.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                            } else if (format == "jsonld") {
                                out = JSONLD.getJSONLDGazetteerSearch(GettyTGN.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toJSONString();
                            } else {
                                out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(GettyTGN.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toString(), format);
                            }
                        } else if (type.equals("pleiades")) {
                            if (format == "json") {
                                out = Pleiades.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3]).toJSONString();
                            } else if (format == "jsonld") {
                                out = JSONLD.getJSONLDGazetteerSearch(Pleiades.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toJSONString();
                            } else {
                                out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(Pleiades.getPlacesByBBox(bboxSplit[0], bboxSplit[1], bboxSplit[4], bboxSplit[5], bboxSplit[6], bboxSplit[7], bboxSplit[2], bboxSplit[3])).toString(), format);
                            }
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
                        if (format == "json") {
                            out = IDAIGazetteer.getPlacesByString(q).toJSONString();
                        } else if (format == "jsonld") {
                            out = JSONLD.getJSONLDGazetteerSearch(IDAIGazetteer.getPlacesByString(q)).toJSONString();
                        } else {
                            out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(IDAIGazetteer.getPlacesByString(q)).toString(), format);
                        }
                    } else if (type.equals("geonames")) {
                        if (format == "json") {
                            out = GeoNames.getPlacesByString(q).toJSONString();
                        } else if (format == "jsonld") {
                            out = JSONLD.getJSONLDGazetteerSearch(GeoNames.getPlacesByString(q)).toJSONString();
                        } else {
                            out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(GeoNames.getPlacesByString(q)).toString(), format);
                        }
                    } else if (type.equals("getty")) {
                        if (format == "json") {
                            out = GettyTGN.getPlacesByString(q).toJSONString();
                        } else if (format == "jsonld") {
                            out = JSONLD.getJSONLDGazetteerSearch(GettyTGN.getPlacesByString(q)).toJSONString();
                        } else {
                            out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(GettyTGN.getPlacesByString(q)).toString(), format);
                        }
                    } else if (type.equals("pleiades")) {
                        if (format == "json") {
                            out = Pleiades.getPlacesByString(q).toJSONString();
                        } else if (format == "jsonld") {
                            out = JSONLD.getJSONLDGazetteerSearch(Pleiades.getPlacesByString(q)).toJSONString();
                        } else {
                            out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(Pleiades.getPlacesByString(q)).toString(), format);
                        }
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
                                            @PathParam("id") String id,
                                            @QueryParam("format") String format) {
        try {
            String out = "{}";
            if (format == null) {
                format = "json";
            }
            format = transformFormat(format);
            if (type.equals("dai")) {
                if (format == "json") {
                    out = IDAIGazetteer.getPlaceById(id).toJSONString();
                } else if (format == "jsonld") {
                    out = JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById(id)).toJSONString();
                } else {
                    out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById(id)).toString(), format);
                }
            } else if (type.equals("geonames")) {
                if (format == "json") {
                    out = GeoNames.getPlaceById(id).toJSONString();
                } else if (format == "jsonld") {
                    out = JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById(id)).toJSONString();
                } else {
                    out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById(id)).toString(), format);
                }
            } else if (type.equals("getty")) {
                if (format == "json") {
                    out = GettyTGN.getPlaceById(id).toJSONString();
                } else if (format == "jsonld") {
                    out = JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById(id)).toJSONString();
                } else {
                    out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById(id)).toString(), format);
                }
            } else if (type.equals("pleiades")) {
                if (format == "json") {
                    out = Pleiades.getPlaceById(id).toJSONString();
                } else if (format == "jsonld") {
                    out = JSONLD.getJSONLDGazetteerResource(Pleiades.getPlaceById(id)).toJSONString();
                } else {
                    out = JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(Pleiades.getPlaceById(id)).toString(), format);
                }
            } else if (type.equals("chronontology")) {
                if (format == "json") {
                    out = ChronOntology.getPlacesById(id).toJSONString();
                } else if (format == "jsonld") {
                    out = JSONLD.getJSONLDChronOntologyJSON(ChronOntology.getPlacesById(id)).toJSONString();
                } else {
                    out = JSONLD.getRDF(JSONLD.getJSONLDChronOntologyJSON(ChronOntology.getPlacesById(id)).toString(), format);
                }
            }
            return ResponseGZIP.setResponse(acceptEncoding, out, Response.Status.OK);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.PlaceResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    private String transformFormat(String format) {
        switch (format) {
            case "json":
                return "json";
            case "ttl":
                return "TURTLE";
            case "jsonld":
                return "jsonld";
            case "rdf":
                return "RDF/XML";
            case "n3":
                return "N-TRIPLES";
            default:
                return "json";
        }
    }

}
