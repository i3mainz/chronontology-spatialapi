package functions;

import java.io.BufferedReader;
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

public class ChronOntology {

	//private String host = "localhost:8084";
	private static String host = "chronontology.i3mainz.hs-mainz.de";
	
	public static StringBuffer getResultsFromChronOntology(String uri) throws Exception {
		String url = uri.replace("period", "data/period");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		int responseCode = con.getResponseCode();
		System.out.println("ChronOntology Response Code : " + responseCode + " - " + url);
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

	public static List<String> ParseChronOntologyJSON(StringBuffer JSON) throws IOException, ParseException {
		List<String> resultList = new ArrayList<String>();
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(JSON.toString());
		JSONObject resource = (JSONObject) jsonObject.get("resource");
		try {
			JSONArray spatial = (JSONArray) resource.get("spatiallyPartOfRegion");
			for (Object element : spatial) {
				resultList.add(element.toString().replace("http://gazetteer.dainst.org/place/", "") + "%" + "spatiallyPartOfRegion");
			}
		} catch (Exception e) {
		}
		try {
			JSONArray spatial2 = (JSONArray) resource.get("namedAfter");
			for (Object element : spatial2) {
				resultList.add(element.toString().replace("http://gazetteer.dainst.org/place/", "") + "%" + "namedAfter");
			}
		} catch (Exception e) {
		}
		try {
			JSONArray spatial3 = (JSONArray) resource.get("hasCoreRegion");
			for (Object element : spatial3) {
				resultList.add(element.toString().replace("http://gazetteer.dainst.org/place/", "") + "%" + "hasCoreRegion");
			}
		} catch (Exception e) {
		}
		if (resultList.isEmpty()) {
			resultList.add("http://"+host+"/stc/getWorldJSON%undefined");
		}
		return resultList;
	}

	public static List<String> ResultsFromChronOntology(String uri) throws Exception {
		return ParseChronOntologyJSON(getResultsFromChronOntology(uri));
	}

}
