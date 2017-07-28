package functions;

import classes.GazetteerData;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GazetteerGettyTGN {
	
	public static StringBuffer getResultsFromGettyTGN(String lowerleftLat, String lowerleftLon, String upperrightLat, String upperrightLon) throws Exception {
		String url = "http://vocab.getty.edu/sparql.json";
		String queryString = "prefix ontogeo: <http://www.ontotext.com/owlim/geo#> "
				+ "prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> "
				+ "prefix skos: <http://www.w3.org/2004/02/skos/core#>"
				+ "prefix tgn: <http://vocab.getty.edu/tgn/> "
				+ "prefix foaf: <http://xmlns.com/foaf/0.1/> "
				+ "prefix gvp: <http://vocab.getty.edu/ontology#> "
				+ "prefix skosxl: <http://www.w3.org/2008/05/skos-xl#> "
				+ "select ?place ?name ?lat ?long where { "
				+ "?place skos:inScheme tgn: . "
				+ "?place foaf:focus [ontogeo:within(" + lowerleftLat + " " + lowerleftLon + " " + upperrightLat + " " + upperrightLon + ")]. "
				+ "?place gvp:prefLabelGVP [skosxl:literalForm ?name] . "
				+ "?place foaf:focus ?p. "
				+ "?p geo:lat ?lat . "
				+ "?p geo:long ?long . "
				+ "}";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String urlParameters = "query=" + queryString;
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("Getty TGN Response Code : " + responseCode);
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response;
		} else {
			return null;
		}
	}
	
	public static List<GazetteerData> ParseTGNJSON(StringBuffer GEONAMESJSON) throws IOException, ParseException {
		List<GazetteerData> resultList = new ArrayList<GazetteerData>();
		if (GEONAMESJSON != null && !GEONAMESJSON.toString().equals("")) {
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(GEONAMESJSON.toString());
			JSONObject results = (JSONObject) jsonObject.get("results");
			JSONArray bindings = (JSONArray) results.get("bindings");
			for (Object element : bindings) {
				JSONObject binding = (JSONObject) element;
				JSONObject place = (JSONObject) binding.get("place");
				String URI = (String) place.get("value");
				JSONObject name = (JSONObject) binding.get("name");
				String NAME = (String) name.get("value");
				JSONObject lat = (JSONObject) binding.get("lat");
				String LAT = (String) lat.get("value");
				JSONObject lon = (JSONObject) binding.get("long");
				String LON = (String) lon.get("value");
				resultList.add(new GazetteerData(URI, NAME, LAT, LON, "gettytgn"));
			}
		} else {
			resultList.add(new GazetteerData("", "Getty TGN Server Error", "0", "0",""));
		}
		return resultList;
	}
	
}
