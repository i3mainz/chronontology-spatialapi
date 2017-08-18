package de.i3mainz.chronontology.rest;

import de.i3mainz.chronontlogy.json.CGeoJSONFeatureObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import de.i3mainz.chronontlogy.json.GGeoJSONObject;
import de.i3mainz.chronontlogy.json.NamesJSONObject;
import de.i3mainz.chronontology.restconfig.ResponseGZIP;
import java.util.HashSet;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.parser.ParseException;

@Path("/geojson")
public class GeojsonResource {

    @GET
    @Path("/g")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getGGeoJSONObject() throws ParseException {
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
        // crate CGeoJSONObject
        // set single feature
        CGeoJSONFeatureObject c = new CGeoJSONFeatureObject();
        c.setGeometry(geom);
        c.setProperties("http://mydummygeojson.org/130630f3", "130630f3", "dummy", names, "coreArea");
        return ResponseGZIP.setResponse("gzip", c.toJSONString(), Response.Status.OK);
    }

}
