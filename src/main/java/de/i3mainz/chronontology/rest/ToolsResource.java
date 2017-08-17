package de.i3mainz.chronontology.rest;

import de.i3mainz.chronontology.classes.BoundingBox;
import de.i3mainz.chronontology.classes.GazetteerData;
import de.i3mainz.chronontology.errorlog.Logging;
import de.i3mainz.chronontology.functions.Functions;
import de.i3mainz.chronontology.functions.StringSimilarity;
import de.i3mainz.chronontology.gazetteer.Geonames;
import de.i3mainz.chronontology.gazetteer.GettyTGN;
import de.i3mainz.chronontology.gazetteer.IdaiGazetteer;
import de.i3mainz.chronontology.restconfig.ResponseGZIP;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * testing class for tools, will be included in place resource
 *
 * @author florian.thiery
 */
@Path("/tools")
public class ToolsResource {

    String req_lat = "49.9987";
    String req_lon = "8.27399";
    String req_name = "Mainzer Dom";

    String upperleft = "50.082665;8.161050";
    String lowerleft = "50.082665;8.371850";
    String upperright = "49.903887;8.161050";
    String lowerright = "49.903887;8.371850";

    @GET
    @Path("/gazetteercompare")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response gazetteercompare(@QueryParam("lat") String req_lat,
                                     @QueryParam("lon") String req_lon,
                                     @QueryParam("name") String req_name) throws Exception {
        try {
            // GET BOUNDINGBOX
            BoundingBox bb = new BoundingBox();
            bb.getBoundingBoxFromLatLon(Double.parseDouble(req_lat), Double.parseDouble(req_lon), 0.1);
            // HARVEST DATA
            //StringBuffer resultTGN = getResultsFromGettyTGN(lowerleftSplit[0], lowerleftSplit[1], upperrightSplit[0], upperrightSplit[1]);
            StringBuffer resultTGN = GettyTGN.getResultsFromGettyTGN(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()));
            List<GazetteerData> tgn = GettyTGN.ParseTGNJSON(resultTGN);
            StringBuffer resultGEONAMES = Geonames.getResultsFromGeonames(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lon()));
            List<GazetteerData> geonames = Geonames.ParseGEONAMESJSON(resultGEONAMES);
            //StringBuffer resultDAI = getResultsFromDaiGazetteer(upperleftSplit[0], upperleftSplit[1], upperrightSplit[0], upperrightSplit[1], lowerrightSplit[0], lowerrightSplit[1], lowerleftSplit[0], lowerleftSplit[1]);
            StringBuffer resultDAI = IdaiGazetteer.getResultsFromDaiGazetteer(String.valueOf(bb.getUpperleft_lat()), String.valueOf(bb.getUpperleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()), String.valueOf(bb.getLowerright_lat()), String.valueOf(bb.getLowerright_lon()), String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()));
            List<GazetteerData> daigazetteer = IdaiGazetteer.ParseDAIJSON(resultDAI);
            // CALCULATE AND SET DISTANCES
            // SET GETTY TGN
            for (GazetteerData element : tgn) {
                // Point1: Point, Point2: Data
                double distance = Functions.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
                element.setDISTANCE(Functions.round(distance, 3));
                double levenshtein = StringSimilarity.Levenshtein(req_name, element.getNAME());
                double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(req_name, element.getNAME());
                double dameraulevenshtein = StringSimilarity.Damerau(req_name, element.getNAME());
                double jarowinkler = StringSimilarity.JaroWinkler(req_name, element.getNAME());
                element.setLevenshtein(Functions.round(levenshtein, 2));
                element.setNormalizedLevenshtein(Functions.round(normalizedlevenshtein, 2));
                element.setDamerauLevenshtein(Functions.round(dameraulevenshtein, 2));
                element.setJaroWinkler(Functions.round(jarowinkler, 2));
            }
            // SET GEONAMES
            for (GazetteerData element : geonames) {
                // Point1: Point, Point2: Data
                double distance = Functions.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
                element.setDISTANCE(Functions.round(distance, 3));
                double levenshtein = StringSimilarity.Levenshtein(req_name, element.getNAME());
                double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(req_name, element.getNAME());
                double dameraulevenshtein = StringSimilarity.Damerau(req_name, element.getNAME());
                double jarowinkler = StringSimilarity.JaroWinkler(req_name, element.getNAME());
                element.setLevenshtein(Functions.round(levenshtein, 2));
                element.setNormalizedLevenshtein(Functions.round(normalizedlevenshtein, 2));
                element.setDamerauLevenshtein(Functions.round(dameraulevenshtein, 2));
                element.setJaroWinkler(Functions.round(jarowinkler, 2));
            }
            // DAI GAZETTEER
            for (GazetteerData element : daigazetteer) {
                // Point1: Point, Point2: Data
                double distance = Functions.getKilometers(Double.parseDouble(req_lat), Double.parseDouble(req_lon), Double.parseDouble(element.getLAT()), Double.parseDouble(element.getLON()));
                element.setDISTANCE(Functions.round(distance, 3));
                double levenshtein = StringSimilarity.Levenshtein(req_name, element.getNAME());
                double normalizedlevenshtein = StringSimilarity.NormalizedLevenshtein(req_name, element.getNAME());
                double dameraulevenshtein = StringSimilarity.Damerau(req_name, element.getNAME());
                double jarowinkler = StringSimilarity.JaroWinkler(req_name, element.getNAME());
                element.setLevenshtein(Functions.round(levenshtein, 2));
                element.setNormalizedLevenshtein(Functions.round(normalizedlevenshtein, 2));
                element.setDamerauLevenshtein(Functions.round(dameraulevenshtein, 2));
                element.setJaroWinkler(Functions.round(jarowinkler, 2));
            }
            // CREATE GEOJSON
            JSONObject outObject = new JSONObject();
            outObject.put("type", "FeatureCollection");
            // SET PLACE
            JSONObject place = new JSONObject();
            place.put("lat", Double.parseDouble(req_lat));
            place.put("lon", Double.parseDouble(req_lon));
            place.put("name", req_name);
            outObject.put("place", place);
            // SET ELEMENTS
            JSONObject elements = new JSONObject();
            elements.put("gettyTGN", tgn.size());
            elements.put("geonames", geonames.size());
            elements.put("daiGazetteer", daigazetteer.size());
            outObject.put("elements", elements);
            // SET FEATURES
            JSONArray features = new JSONArray();
            // SET START POINT
            JSONObject start = new JSONObject();
            start.put("type", "Feature");
            JSONObject geomS = new JSONObject();
            geomS.put("type", "Point");
            JSONArray coordinatesS = new JSONArray();
            coordinatesS.add(Double.parseDouble(req_lon));
            coordinatesS.add(Double.parseDouble(req_lat));
            geomS.put("coordinates", coordinatesS);
            JSONObject propertiesS = new JSONObject();
            propertiesS.put("type", "startpoint");
            propertiesS.put("name", req_name);
            start.put("geometry", geomS);
            start.put("properties", propertiesS);
            features.add(start);
            // SET BOUNDING BOX
            features.add(bb.getGeoJSON());
            // SET GETTY TGN
            for (GazetteerData element : tgn) {
                String lat = element.getLAT().replace("-.", "-0.");
                String lon = element.getLON().replace("-.", "-0.");
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geom = new JSONObject();
                geom.put("type", "Point");
                JSONArray coordinates = new JSONArray();
                coordinates.add(Double.parseDouble(lon));
                coordinates.add(Double.parseDouble(lat));
                geom.put("coordinates", coordinates);
                JSONObject properties = new JSONObject();
                properties.put("uri", element.getURI());
                properties.put("name", element.getNAME());
                properties.put("provenance", element.getPROVENANCE());
                properties.put("distance", element.getDISTANCE());
                JSONObject sim = new JSONObject();
                sim.put("levenshtein", element.getLevenshtein());
                sim.put("normalizedlevenshtein", element.getNormalizedLevenshtein());
                sim.put("dameraulevenshtein", element.getDamerauLevenshtein());
                sim.put("jarowinkler", element.getJaroWinkler());
                properties.put("similarity", sim);
                feature.put("geometry", geom);
                feature.put("properties", properties);
                features.add(feature);
            }
            // SET GEONAMES
            for (GazetteerData element : geonames) {
                String lat = element.getLAT().replace("-.", "-0.");
                String lon = element.getLON().replace("-.", "-0.");
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geom = new JSONObject();
                geom.put("type", "Point");
                JSONArray coordinates = new JSONArray();
                coordinates.add(Double.parseDouble(lon));
                coordinates.add(Double.parseDouble(lat));
                geom.put("coordinates", coordinates);
                JSONObject properties = new JSONObject();
                properties.put("uri", element.getURI());
                properties.put("name", element.getNAME());
                properties.put("provenance", element.getPROVENANCE());
                properties.put("distance", element.getDISTANCE());
                JSONObject sim = new JSONObject();
                sim.put("levenshtein", element.getLevenshtein());
                sim.put("normalizedlevenshtein", element.getNormalizedLevenshtein());
                sim.put("dameraulevenshtein", element.getDamerauLevenshtein());
                sim.put("jarowinkler", element.getJaroWinkler());
                properties.put("similarity", sim);
                feature.put("geometry", geom);
                feature.put("properties", properties);
                features.add(feature);
            }
            // SET DAI GAZETTEER
            for (GazetteerData element : daigazetteer) {
                String lat = element.getLAT().replace("-.", "-0.");
                String lon = element.getLON().replace("-.", "-0.");
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geom = new JSONObject();
                geom.put("type", "Point");
                JSONArray coordinates = new JSONArray();
                coordinates.add(Double.parseDouble(lon));
                coordinates.add(Double.parseDouble(lat));
                geom.put("coordinates", coordinates);
                JSONObject properties = new JSONObject();
                properties.put("uri", element.getURI());
                properties.put("name", element.getNAME());
                properties.put("provenance", element.getPROVENANCE());
                properties.put("distance", element.getDISTANCE());
                JSONObject sim = new JSONObject();
                sim.put("levenshtein", element.getLevenshtein());
                sim.put("normalizedlevenshtein", element.getNormalizedLevenshtein());
                sim.put("dameraulevenshtein", element.getDamerauLevenshtein());
                sim.put("jarowinkler", element.getJaroWinkler());
                properties.put("similarity", sim);
                feature.put("geometry", geom);
                feature.put("properties", properties);
                features.add(feature);
            }
            outObject.put("features", features);
            return ResponseGZIP.setResponse("gzip", outObject.toJSONString(), Response.Status.OK);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.InfoResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/gazetteerlookup")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response gazetteerlookup(@QueryParam("upperleft") String upperleft,
                                    @QueryParam("lowerleft") String lowerleft,
                                    @QueryParam("upperright") String upperright,
                                    @QueryParam("lowerright") String lowerright) throws Exception {
        try {
            String[] upperleftSplit = upperleft.split(";"); //[0]=LAT [1]=LON
            String[] lowerleftSplit = lowerleft.split(";"); //[0]=LAT [1]=LON
            String[] upperrightSplit = upperright.split(";"); //[0]=LAT [1]=LON
            String[] lowerrightSplit = lowerright.split(";"); //[0]=LAT [1]=LON
            // GET BOUNDINGBOX
            BoundingBox bb = new BoundingBox(Double.parseDouble(upperleftSplit[0]), Double.parseDouble(upperleftSplit[1]), Double.parseDouble(lowerleftSplit[0]), Double.parseDouble(lowerleftSplit[1]), Double.parseDouble(upperrightSplit[0]), Double.parseDouble(upperrightSplit[1]), Double.parseDouble(lowerrightSplit[0]), Double.parseDouble(lowerrightSplit[1]));
            // HARVEST DATA
            StringBuffer resultTGN = GettyTGN.getResultsFromGettyTGN(String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()));
            List<GazetteerData> tgn = GettyTGN.ParseTGNJSON(resultTGN);
            StringBuffer resultGEONAMES = Geonames.getResultsFromGeonames(String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getUpperright_lon()), String.valueOf(bb.getLowerleft_lon()));
            List<GazetteerData> geonames = Geonames.ParseGEONAMESJSON(resultGEONAMES);
            StringBuffer resultDAI = IdaiGazetteer.getResultsFromDaiGazetteer(String.valueOf(bb.getUpperleft_lat()), String.valueOf(bb.getUpperleft_lon()), String.valueOf(bb.getUpperright_lat()), String.valueOf(bb.getUpperright_lon()), String.valueOf(bb.getLowerright_lat()), String.valueOf(bb.getLowerright_lon()), String.valueOf(bb.getLowerleft_lat()), String.valueOf(bb.getLowerleft_lon()));
            List<GazetteerData> daigazetteer = IdaiGazetteer.ParseDAIJSON(resultDAI);
            // CREATE GEOJSON
            JSONObject outObject = new JSONObject();
            outObject.put("type", "FeatureCollection");
            // SET ELEMENTS
            JSONObject elements = new JSONObject();
            elements.put("gettyTGN", tgn.size());
            elements.put("geonames", geonames.size());
            elements.put("daiGazetteer", daigazetteer.size());
            outObject.put("elements", elements);
            // SET FEATURES
            JSONArray features = new JSONArray();
            // SET BOUNDING BOX
            features.add(bb.getGeoJSON());
            // SET GETTY TGN
            for (GazetteerData element : tgn) {
                String lat = element.getLAT().replace("-.", "-0.");
                String lon = element.getLON().replace("-.", "-0.");
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geom = new JSONObject();
                geom.put("type", "Point");
                JSONArray coordinates = new JSONArray();
                coordinates.add(Double.parseDouble(lon));
                coordinates.add(Double.parseDouble(lat));
                geom.put("coordinates", coordinates);
                JSONObject properties = new JSONObject();
                properties.put("uri", element.getURI());
                properties.put("name", element.getNAME());
                properties.put("provenance", element.getPROVENANCE());
                feature.put("geometry", geom);
                feature.put("properties", properties);
                features.add(feature);
            }
            // SET GEONAMES
            for (GazetteerData element : geonames) {
                String lat = element.getLAT().replace("-.", "-0.");
                String lon = element.getLON().replace("-.", "-0.");
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geom = new JSONObject();
                geom.put("type", "Point");
                JSONArray coordinates = new JSONArray();
                coordinates.add(Double.parseDouble(lon));
                coordinates.add(Double.parseDouble(lat));
                geom.put("coordinates", coordinates);
                JSONObject properties = new JSONObject();
                properties.put("uri", element.getURI());
                properties.put("name", element.getNAME());
                properties.put("provenance", element.getPROVENANCE());
                feature.put("geometry", geom);
                feature.put("properties", properties);
                features.add(feature);
            }
            // SET DAI GAZETTEER
            for (GazetteerData element : daigazetteer) {
                String lat = element.getLAT().replace("-.", "-0.");
                String lon = element.getLON().replace("-.", "-0.");
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                JSONObject geom = new JSONObject();
                geom.put("type", "Point");
                JSONArray coordinates = new JSONArray();
                coordinates.add(Double.parseDouble(lon));
                coordinates.add(Double.parseDouble(lat));
                geom.put("coordinates", coordinates);
                JSONObject properties = new JSONObject();
                properties.put("uri", element.getURI());
                properties.put("name", element.getNAME());
                properties.put("provenance", element.getPROVENANCE());
                feature.put("geometry", geom);
                feature.put("properties", properties);
                features.add(feature);
            }
            outObject.put("features", features);
            return ResponseGZIP.setResponse("gzip", outObject.toJSONString(), Response.Status.OK);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Logging.getMessageJSON(e, "de.i3mainz.chronontology.rest.InfoResource"))
                    .header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

}
