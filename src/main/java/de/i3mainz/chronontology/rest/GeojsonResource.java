package de.i3mainz.chronontology.rest;

import de.i3mainz.chronontlogy.json.CGeoJSONFeatureObject;
import de.i3mainz.chronontlogy.json.CGeoJSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import de.i3mainz.chronontlogy.json.GGeoJSONObject;
import de.i3mainz.chronontlogy.json.NamesJSONObject;
import de.i3mainz.chronontology.restconfig.ResponseGZIP;
import java.io.IOException;
import java.util.HashSet;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.jena.riot.Lang;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.linkedgeodesy.jenaext.log.JenaModelException;
import org.linkedgeodesy.jenaext.model.JenaModel;

@Path("/geojson")
public class GeojsonResource {

    @GET
    @Path("/g")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getGGeoJSONObject() throws ParseException, IOException, JenaModelException {
        // set geom
        JSONParser parser = new JSONParser();
        JSONObject geom = (JSONObject) parser.parse("{\"type\":\"Polygon\",\"coordinates\":[[[9.921906,54.983104],[9.93958,54.596642],[10.950112,54.363607],[10.939467,54.008693],[11.956252,54.196486],[12.51844,54.470371],[13.647467,54.075511],[14.119686,53.757029],[14.353315,53.248171],[14.074521,52.981263],[14.4376,52.62485],[14.685026,52.089947],[14.607098,51.745188],[15.016996,51.106674],[14.570718,51.002339],[14.307013,51.117268],[14.056228,50.926918],[13.338132,50.733234],[12.966837,50.484076],[12.240111,50.266338],[12.415191,49.969121],[12.521024,49.547415],[13.031329,49.307068],[13.595946,48.877172],[13.243357,48.416115],[12.884103,48.289146],[13.025851,47.637584],[12.932627,47.467646],[12.62076,47.672388],[12.141357,47.703083],[11.426414,47.523766],[10.544504,47.566399],[10.402084,47.302488],[9.896068,47.580197],[9.594226,47.525058],[8.522612,47.830828],[8.317301,47.61358],[7.466759,47.620582],[7.593676,48.333019],[8.099279,49.017784],[6.65823,49.201958],[6.18632,49.463803],[6.242751,49.902226],[6.043073,50.128052],[6.156658,50.803721],[5.988658,51.851616],[6.589397,51.852029],[6.84287,52.22844],[7.092053,53.144043],[6.90514,53.482162],[7.100425,53.693932],[7.936239,53.748296],[8.121706,53.527792],[8.800734,54.020786],[8.572118,54.395646],[8.526229,54.962744],[9.282049,54.830865],[9.921906,54.983104]]]}");
        // set names
        HashSet de = new HashSet();
        de.add("römisch");
        de.add("Römisch");
        HashSet en = new HashSet();
        en.add("greek");
        en.add("Greek");
        NamesJSONObject names = new NamesJSONObject();
        names.setName("de", de);
        names.setName("en", en);
        // crate GGeoJSONObject
        GGeoJSONObject g = new GGeoJSONObject();
        g.setGeometry(geom);
        g.setProperties("http://mydummygeojson.org/130630f3", "130630f3", "dummy", names);
        return ResponseGZIP.setResponse("gzip", g.toJSONString(), Response.Status.OK);
    }

    @GET
    @Path("/c")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getCGeoJSONObject() throws ParseException {
        JSONParser parser = new JSONParser();
        // set metadata
        // example from http://chronontology.dainst.org/data/period/0iRdmlr38uau
        JSONObject co_object = (JSONObject) parser.parse("{\"resource\":{\"names\":{\"de\":[\"Parthisch\",\"Partherreich\"],\"en\":[\"Parthian\",\"Parthian Empire\",\"Arsacid Empire\"]},\"types\":[\"politisch\"],\"provenance\":\"chronontology\",\"note2\":\"Das Partherreich war die dominierende Macht des ersten vorchristlichen sowie des ersten und zweiten nachchristlichen Jahrhunderts in Persien und Mesopotamien. Die Parther  waren ein iranisches Volk, das vom 3. Jahrhundert v. Chr. im heutigen Iran ein Reich aufbaute, das zur Zeit der grÃ¶ÃŸten Ausdehnung auch groÃŸe Teile Mesopotamiens, des sÃ¼dwestlichen Mittelasiens und einiger angrenzender Randgebiete umfasste. Mitunter werden sie nach dem Namen der regierenden Dynastie auch als Arsakiden bezeichnet. Das Partherreich endete mit der MachtÃ¼bernahme der Sassaniden im Iran im frÃ¼hen 3. Jahrhundert n. Chr. (source: wikipedia deutsch)\\n\\nParthia is a historical region located in north-eastern Iran. It was the political and cultural base of the Arsacid dynasty, rulers of the Parthian Empire (247 BC â€“ 224 AD).  (source: wikipedia english)\",\"hasTimespan\":[{\"timeOriginal\":\"247 BC â€“ 224 AD\",\"begin\":{\"at\":\"-247\",\"atPrecision\":\"ca\"},\"end\":{\"at\":\"224\",\"atPrecision\":\"ca\"}}],\"spatiallyPartOfRegion\":[\"http://gazetteer.dainst.org/place/2359910\"],\"relations\":{\"isFollowedBy\":[\"prX4hmZMs0yp\"],\"isPartOf\":[\"LgCdcG02kRtJ\"],\"isSenseOf\":[\"EBJIZW5V24qE\"]},\"id\":\"0iRdmlr38uau\",\"type\":\"period\"},\"dataset\":\"none\",\"derived\":{\"relations\":{\"occursDuring\":[\"LgCdcG02kRtJ\"],\"meetsInTimeWith\":[\"prX4hmZMs0yp\"],\"fallsWithin\":[\"LgCdcG02kRtJ\"],\"endsAtTheStartOf\":[\"prX4hmZMs0yp\"]}},\"version\":6,\"created\":{\"user\":\"admin\",\"date\":\"2017-05-29T10:27:39.38+02:00\"},\"modified\":[{\"user\":\"admin\",\"date\":\"2017-05-29T10:32:25.79+02:00\"},{\"user\":\"admin\",\"date\":\"2017-05-30T13:52:20.804+02:00\"},{\"user\":\"admin\",\"date\":\"2017-05-30T13:55:42.633+02:00\"},{\"user\":\"admin\",\"date\":\"2017-05-30T14:54:54.986+02:00\"},{\"user\":\"admin\",\"date\":\"2017-05-30T14:55:27.748+02:00\"}],\"related\":{\"http://gazetteer.dainst.org/place/2359910\":{\"prefName\":{\"title\":\"Partherreich\",\"language\":\"deu\"}}}}");
        JSONArray co_when = (JSONArray) parser.parse("[{\"timeOriginal\":\"247 BC - 224 AD\",\"begin\":{\"at\":\"-247\",\"atPrecision\":\"ca\"},\"end\":{\"at\":\"224\",\"atPrecision\":\"ca\"}}]");
        JSONObject co_names = (JSONObject) parser.parse("{\"de\":[\"Parthisch\",\"Partherreich\"],\"en\":[\"Parthian\",\"Parthian Empire\",\"Arsacid Empire\"]}");
        // set feature 1 --> example geoms from https://github.com/johan/world.geo.json/tree/master/countries
        JSONObject geom = (JSONObject) parser.parse("{\"type\":\"Polygon\",\"coordinates\":[[[9.921906,54.983104],[9.93958,54.596642],[10.950112,54.363607],[10.939467,54.008693],[11.956252,54.196486],[12.51844,54.470371],[13.647467,54.075511],[14.119686,53.757029],[14.353315,53.248171],[14.074521,52.981263],[14.4376,52.62485],[14.685026,52.089947],[14.607098,51.745188],[15.016996,51.106674],[14.570718,51.002339],[14.307013,51.117268],[14.056228,50.926918],[13.338132,50.733234],[12.966837,50.484076],[12.240111,50.266338],[12.415191,49.969121],[12.521024,49.547415],[13.031329,49.307068],[13.595946,48.877172],[13.243357,48.416115],[12.884103,48.289146],[13.025851,47.637584],[12.932627,47.467646],[12.62076,47.672388],[12.141357,47.703083],[11.426414,47.523766],[10.544504,47.566399],[10.402084,47.302488],[9.896068,47.580197],[9.594226,47.525058],[8.522612,47.830828],[8.317301,47.61358],[7.466759,47.620582],[7.593676,48.333019],[8.099279,49.017784],[6.65823,49.201958],[6.18632,49.463803],[6.242751,49.902226],[6.043073,50.128052],[6.156658,50.803721],[5.988658,51.851616],[6.589397,51.852029],[6.84287,52.22844],[7.092053,53.144043],[6.90514,53.482162],[7.100425,53.693932],[7.936239,53.748296],[8.121706,53.527792],[8.800734,54.020786],[8.572118,54.395646],[8.526229,54.962744],[9.282049,54.830865],[9.921906,54.983104]]]}");
        CGeoJSONFeatureObject f1 = new CGeoJSONFeatureObject();
        f1.setGeometry(geom);
        f1.setProperties("http://mydummygeojson.org/130630f3", "130630f3", "dummy", "hasCoreArea");
        // set feature 2
        JSONObject geom2 = (JSONObject) parser.parse("{\"type\":\"Polygon\",\"coordinates\":[[[-9.034818,41.880571],[-8.984433,42.592775],[-9.392884,43.026625],[-7.97819,43.748338],[-6.754492,43.567909],[-5.411886,43.57424],[-4.347843,43.403449],[-3.517532,43.455901],[-1.901351,43.422802],[-1.502771,43.034014],[0.338047,42.579546],[0.701591,42.795734],[1.826793,42.343385],[2.985999,42.473015],[3.039484,41.89212],[2.091842,41.226089],[0.810525,41.014732],[0.721331,40.678318],[0.106692,40.123934],[-0.278711,39.309978],[0.111291,38.738514],[-0.467124,38.292366],[-0.683389,37.642354],[-1.438382,37.443064],[-2.146453,36.674144],[-3.415781,36.6589],[-4.368901,36.677839],[-4.995219,36.324708],[-5.37716,35.94685],[-5.866432,36.029817],[-6.236694,36.367677],[-6.520191,36.942913],[-7.453726,37.097788],[-7.537105,37.428904],[-7.166508,37.803894],[-7.029281,38.075764],[-7.374092,38.373059],[-7.098037,39.030073],[-7.498632,39.629571],[-7.066592,39.711892],[-7.026413,40.184524],[-6.86402,40.330872],[-6.851127,41.111083],[-6.389088,41.381815],[-6.668606,41.883387],[-7.251309,41.918346],[-7.422513,41.792075],[-8.013175,41.790886],[-8.263857,42.280469],[-8.671946,42.134689],[-9.034818,41.880571]]]}");
        CGeoJSONFeatureObject f2 = new CGeoJSONFeatureObject();
        f2.setGeometry(geom2);
        f2.setProperties("http://mydummygeojson.org/e96ce95d", "e96ce95d", "dummy", "hasCoreArea");
        // set feature 3
        JSONObject geom3 = (JSONObject) parser.parse("{\"type\":\"Polygon\",\"coordinates\":[[[-6.197885,53.867565],[-6.032985,53.153164],[-6.788857,52.260118],[-8.561617,51.669301],[-9.977086,51.820455],[-9.166283,52.864629],[-9.688525,53.881363],[-8.327987,54.664519],[-7.572168,55.131622],[-7.366031,54.595841],[-7.572168,54.059956],[-6.95373,54.073702],[-6.197885,53.867565]]]}");
        CGeoJSONFeatureObject f3 = new CGeoJSONFeatureObject();
        f3.setGeometry(geom3);
        f3.setProperties("http://mydummygeojson.org/f632876f", "f632876f", "dummy", "hasCoreArea");
        // set geojson
        CGeoJSONObject c = new CGeoJSONObject();
        c.setMetadata("http://chronontology.dainst.org/period/0iRdmlr38uau", "0iRdmlr38uau", co_object, co_when, co_names);
        //c.setMetadata("FD6JS3cmi2Wc", new JSONObject(), new JSONArray(), new JSONObject());
        c.setFeature(f1);
        c.setFeature(f2);
        c.setFeature(f3);
        return ResponseGZIP.setResponse("gzip", c.toJSONString(), Response.Status.OK);
    }

    @GET
    @Path("/ld")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getJSONLD() throws ParseException, IOException, JenaModelException {
        // read in turtle, write json-ld, read in json-ld, write N-Triples
        String turtle = "<http://example.org/#spiderman> <http://www.perceive.net/schemas/relationship/enemyOf> <http://example.org/#green-goblin> ; <http://xmlns.com/foaf/0.1/name> \"Spiderman\" .";
        JenaModel jm = new JenaModel();
        JenaModel jm2 = new JenaModel();
        jm.readRDF(turtle, Lang.TURTLE);
        System.out.println(jm.getModel("JSON-LD"));
        String turtleld = jm.getModel("JSON-LD");
        jm2.readRDF(turtleld, Lang.JSONLD);
        System.out.println(jm2.getModel());
        // test with ls json-ld
        JenaModel jm3 = new JenaModel();
        String ld = "{\n"
                + "  \"@id\" : \"http://143.93.114.135/item/label/9ExDWbZEPMOP\",\n"
                + "  \"ls:thumbnail\" : {\n"
                + "    \"@language\" : \"de\",\n"
                + "    \"@value\" : \"Wrack\"\n"
                + "  },\n"
                + "  \"dc:created\" : \"2017-05-15T13:44:38.177+0200\",\n"
                + "  \"dc:creator\" : \"thiery\",\n"
                + "  \"dc:identifier\" : \"9ExDWbZEPMOP\",\n"
                + "  \"dc:language\" : \"de\",\n"
                + "  \"dc:modified\" : [ \"2017-05-15T13:44:38.177+0200\", \"2017-05-15T13:44:46.606+0200\" ],\n"
                + "  \"creator\" : \"http://143.93.114.135/item/agent/thiery\",\n"
                + "  \"rdf:type\" : [ {\n"
                + "    \"@id\" : \"skos:Concept\"\n"
                + "  }, {\n"
                + "    \"@id\" : \"ls:Label\"\n"
                + "  } ],\n"
                + "  \"changeNote\" : \"http://143.93.114.135/item/revision/j76Bx3b5WqLR\",\n"
                + "  \"inScheme\" : \"http://143.93.114.135/item/vocabulary/R85M7YgBEeg2\",\n"
                + "  \"skos:prefLabel\" : [ {\n"
                + "    \"@language\" : \"de\",\n"
                + "    \"@value\" : \"Wrack\"\n"
                + "  }, {\n"
                + "    \"@language\" : \"en\",\n"
                + "    \"@value\" : \"wreck\"\n"
                + "  } ],\n"
                + "  \"@context\" : {\n"
                + "    \"prefLabel\" : {\n"
                + "      \"@id\" : \"http://www.w3.org/2004/02/skos/core#prefLabel\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"language\" : {\n"
                + "      \"@id\" : \"http://purl.org/dc/elements/1.1/language\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"thumbnail\" : {\n"
                + "      \"@id\" : \"http://labeling.link/docs/ls/core#thumbnail\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"creator\" : {\n"
                + "      \"@id\" : \"http://purl.org/dc/terms/creator\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"identifier\" : {\n"
                + "      \"@id\" : \"http://purl.org/dc/elements/1.1/identifier\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"changeNote\" : {\n"
                + "      \"@id\" : \"http://www.w3.org/2004/02/skos/core#changeNote\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"modified\" : {\n"
                + "      \"@id\" : \"http://purl.org/dc/elements/1.1/modified\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"created\" : {\n"
                + "      \"@id\" : \"http://purl.org/dc/elements/1.1/created\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"inScheme\" : {\n"
                + "      \"@id\" : \"http://www.w3.org/2004/02/skos/core#inScheme\",\n"
                + "      \"@type\" : \"@id\"\n"
                + "    },\n"
                + "    \"geo\" : \"http://www.w3.org/2003/01/geo/wgs84_pos#\",\n"
                + "    \"dct\" : \"http://purl.org/dc/terms/\",\n"
                + "    \"owl\" : \"http://www.w3.org/2002/07/owl#\",\n"
                + "    \"rdf\" : \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\",\n"
                + "    \"ls\" : \"http://labeling.link/docs/ls/core#\",\n"
                + "    \"xsd\" : \"http://www.w3.org/2001/XMLSchema#\",\n"
                + "    \"skos\" : \"http://www.w3.org/2004/02/skos/core#\",\n"
                + "    \"rdfs\" : \"http://www.w3.org/2000/01/rdf-schema#\",\n"
                + "    \"dcat\" : \"http://www.w3.org/ns/dcat#\",\n"
                + "    \"prov\" : \"http://www.w3.org/ns/prov#\",\n"
                + "    \"foaf\" : \"http://xmlns.com/foaf/0.1/\",\n"
                + "    \"dc\" : \"http://purl.org/dc/elements/1.1/\"\n"
                + "  }\n"
                + "}";
        jm3.readJSONLD(ld);
        System.out.println(jm3.getModel("N-Triples"));
        return ResponseGZIP.setResponse("gzip", jm3.getModel("JSON-LD"), Response.Status.OK);
    }

}
