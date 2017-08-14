package de.i3mainz.chronontology.gazetteer;

import de.i3mainz.chronontology.classes.GazetteerData;
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

public class Geonames {
	
	public static StringBuffer getResultsFromGeonames(String south, String north, String west, String east) throws Exception {
		String url = "http://api.geonames.org/searchJSON";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String urlParameters = "username=chron.ontology";
		urlParameters += "&featureClass=A&featureClass=P";
		urlParameters += "&style=short";
		urlParameters += "&south=" + south + "&north=" + north + "&west=" + west + "&east=" + east;
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("Geonames Response Code : " + responseCode);
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
	
	public static List<GazetteerData> ParseGEONAMESJSON(StringBuffer SPARQLJSON) throws IOException, ParseException {
		List<GazetteerData> resultList = new ArrayList<GazetteerData>();
		if (SPARQLJSON != null && !SPARQLJSON.toString().equals("")) {
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(SPARQLJSON.toString());
			JSONArray geonames = (JSONArray) jsonObject.get("geonames");
			for (Object element : geonames) {
				JSONObject geonamesEntry = (JSONObject) element;
				Long URI = (Long) geonamesEntry.get("geonameId");
				String NAME = (String) geonamesEntry.get("name");
				String LAT = (String) geonamesEntry.get("lat");
				String LON = (String) geonamesEntry.get("lng");
				resultList.add(new GazetteerData("http://geonames.org/" + Long.toString(URI), NAME, LAT, LON, "geonames"));
			}
		} else {
			resultList.add(new GazetteerData("", "Geonames Server Error", "0", "0",""));
		}
		return resultList;
	}
	
}
