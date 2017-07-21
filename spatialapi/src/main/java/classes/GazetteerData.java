package classes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GazetteerData {

	private String URI = "";
	private String ID = "";
	private String NAME = "";
	private JSONObject PREFNAME = new JSONObject();
	private JSONArray NAMES = new JSONArray();
	private String LAT = "";
	private String LON = "";
	private String GEOMETRY = "";
	private JSONObject GEOM = new JSONObject();
	private String PROVENANCE = "";
	private double DISTANCE = -1.0;
	private double Levenshtein = -1.0;
	private double NormalizedLevenshtein = -1.0;
	private double DamerauLevenshtein = -1.0;
	private double JaroWinkler = -1.0;
	private String OPTIONAL = "";

	public GazetteerData(String uri, String name, String lat, String lon, String provenance) {
		this.URI = uri;
		this.NAME = name;
		this.LAT = lat;
		this.LON = lon;
		this.PROVENANCE = provenance;
	}
	
	public GazetteerData(String uri, String name, String geometry, String provenance, JSONObject prefName) {
		this.URI = uri;
		this.NAME = name;
		this.GEOMETRY = geometry;
		this.PROVENANCE = provenance;
	}
	
	public GazetteerData(String uri, String id, String provenance, JSONObject prefName, JSONArray names, JSONObject geom) {
		this.URI = uri;
		this.ID = id;
		this.PROVENANCE = provenance;
		this.PREFNAME = prefName;
		this.NAMES = names;
		this.GEOM = geom;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String URI) {
		this.URI = URI;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getLAT() {
		return LAT;
	}

	public void setLAT(String LAT) {
		this.LAT = LAT;
	}

	public String getLON() {
		return LON;
	}

	public void setLON(String LON) {
		this.LON = LON;
	}

	public double getDISTANCE() {
		return DISTANCE;
	}

	public void setDISTANCE(double DISTANCE) {
		this.DISTANCE = DISTANCE;
	}

	public double getLevenshtein() {
		return Levenshtein;
	}

	public void setLevenshtein(double Levenshtein) {
		this.Levenshtein = Levenshtein;
	}

	public double getNormalizedLevenshtein() {
		return NormalizedLevenshtein;
	}

	public void setNormalizedLevenshtein(double NormalizedLevenshtein) {
		this.NormalizedLevenshtein = NormalizedLevenshtein;
	}

	public double getDamerauLevenshtein() {
		return DamerauLevenshtein;
	}

	public void setDamerauLevenshtein(double DamerauLevenshtein) {
		this.DamerauLevenshtein = DamerauLevenshtein;
	}

	public double getJaroWinkler() {
		return JaroWinkler;
	}

	public void setJaroWinkler(double JaroWinkler) {
		this.JaroWinkler = JaroWinkler;
	}

	public String getPROVENANCE() {
		return PROVENANCE;
	}

	public void setPROVENANCE(String PROVENANCE) {
		this.PROVENANCE = PROVENANCE;
	}

	public String getGEOMETRY() {
		return GEOMETRY;
	}

	public void setGEOMETRY(String GEOMETRY) {
		this.GEOMETRY = GEOMETRY;
	}

	public String getOPTIONAL() {
		return OPTIONAL;
	}

	public void setOPTIONAL(String OPTIONAL) {
		this.OPTIONAL = OPTIONAL;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public JSONObject getPREFNAME() {
		return PREFNAME;
	}

	public void setPREFNAME(JSONObject PREFNAME) {
		this.PREFNAME = PREFNAME;
	}

	public JSONArray getNAMES() {
		return NAMES;
	}

	public void setNAMES(JSONArray NAMES) {
		this.NAMES = NAMES;
	}

	public JSONObject getGEOM() {
		return GEOM;
	}

	public void setGEOM(JSONObject GEOM) {
		this.GEOM = GEOM;
	}

}
