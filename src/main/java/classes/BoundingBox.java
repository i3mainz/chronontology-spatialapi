package classes;

import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BoundingBox {

	private double upperleft_lat = -1.0;
	private double upperleft_lon = -1.0;
	private double lowerleft_lat = -1.0;
	private double lowerleft_lon = -1.0;
	private double upperright_lat = -1.0;
	private double upperright_lon = -1.0;
	private double lowerright_lat = -1.0;
	private double lowerright_lon = -1.0;
	private JSONObject geoJSON;

	public BoundingBox() {
		this.upperleft_lat = -1.0;
		this.upperleft_lon = -1.0;
		this.lowerleft_lat = -1.0;
		this.lowerleft_lon = -1.0;
		this.upperright_lat = -1.0;
		this.upperright_lon = -1.0;
		this.lowerright_lat = -1.0;
		this.lowerright_lon = -1.0;
	}

	public BoundingBox(double upperleft_lat, double upperleft_lon, double lowerleft_lat, double lowerleft_lon, double upperright_lat, double upperright_lon, double lowerright_lat, double lowerright_lon) {
		this.upperleft_lat = upperleft_lat;
		this.upperleft_lon = upperleft_lon;
		this.lowerleft_lat = lowerleft_lat;
		this.lowerleft_lon = lowerleft_lon;
		this.upperright_lat = upperright_lat;
		this.upperright_lon = upperright_lon;
		this.lowerright_lat = lowerright_lat;
		this.lowerright_lon = lowerright_lon;
		JSONObject bboxfeature = new JSONObject();
		bboxfeature.put("type", "Feature");
		JSONObject bboxgeojson = new JSONObject();
		bboxgeojson.put("type", "Polygon");
		JSONArray bboxarray = new JSONArray();
		JSONArray bboxarray2 = new JSONArray();
		JSONArray upperleft = new JSONArray();
		upperleft.add(upperleft_lon);
		upperleft.add(upperleft_lat);
		JSONArray upperright = new JSONArray();
		upperright.add(upperright_lon);
		upperright.add(upperright_lat);
		JSONArray lowerright = new JSONArray();
		lowerright.add(lowerright_lon);
		lowerright.add(lowerright_lat);
		JSONArray lowerleft = new JSONArray();
		lowerleft.add(lowerleft_lon);
		lowerleft.add(lowerleft_lat);
		bboxarray.add(upperleft);
		bboxarray.add(upperright);
		bboxarray.add(lowerright);
		bboxarray.add(lowerleft);
		bboxarray.add(upperleft);
		bboxarray2.add(bboxarray);
		bboxgeojson.put("coordinates", bboxarray2);
		bboxfeature.put("geometry", bboxgeojson);
		JSONObject prop = new JSONObject();
		prop.put("type", "boundingbox");
		bboxfeature.put("properties", prop);
		this.geoJSON = bboxfeature;
	}

	public void getBoundingBoxFromLatLon(Double lat, Double lon, Double radius) {
		Point center = new Point(lat, lon);
		Envelope e = new Envelope(center, radius, radius);
		this.lowerleft_lon = e.getLowerLeft().getY();
		this.lowerleft_lat = e.getLowerLeft().getX();
		this.upperright_lon = e.getUpperRight().getY();
		this.upperright_lat = e.getUpperRight().getX();
		this.lowerright_lon = e.getLowerRight().getY();
		this.lowerright_lat = e.getLowerRight().getX();
		this.upperleft_lon = e.getUpperLeft().getY();
		this.upperleft_lat = e.getUpperLeft().getX();
		JSONObject bboxfeature = new JSONObject();
		bboxfeature.put("type", "Feature");
		JSONObject bboxgeojson = new JSONObject();
		bboxgeojson.put("type", "Polygon");
		JSONArray bboxarray = new JSONArray();
		JSONArray bboxarray2 = new JSONArray();
		JSONArray upperleft = new JSONArray();
		upperleft.add(e.getUpperLeft().getY());
		upperleft.add(e.getUpperLeft().getX());
		JSONArray upperright = new JSONArray();
		upperright.add(e.getUpperRight().getY());
		upperright.add(e.getUpperRight().getX());
		JSONArray lowerright = new JSONArray();
		lowerright.add(e.getLowerRight().getY());
		lowerright.add(e.getLowerRight().getX());
		JSONArray lowerleft = new JSONArray();
		lowerleft.add(e.getLowerLeft().getY());
		lowerleft.add(e.getLowerLeft().getX());
		bboxarray.add(upperleft);
		bboxarray.add(upperright);
		bboxarray.add(lowerright);
		bboxarray.add(lowerleft);
		bboxarray.add(upperleft);
		bboxarray2.add(bboxarray);
		bboxgeojson.put("coordinates", bboxarray2);
		bboxfeature.put("geometry", bboxgeojson);
		JSONObject prop = new JSONObject();
		prop.put("type", "boundingbox");
		bboxfeature.put("properties", prop);
		this.geoJSON = bboxfeature;
	}

	public double getUpperleft_lat() {
		return upperleft_lat;
	}

	public void setUpperleft_lat(double upperleft_lat) {
		this.upperleft_lat = upperleft_lat;
	}

	public double getUpperleft_lon() {
		return upperleft_lon;
	}

	public void setUpperleft_lon(double upperleft_lon) {
		this.upperleft_lon = upperleft_lon;
	}

	public double getLowerleft_lat() {
		return lowerleft_lat;
	}

	public void setLowerleft_lat(double lowerleft_lat) {
		this.lowerleft_lat = lowerleft_lat;
	}

	public double getLowerleft_lon() {
		return lowerleft_lon;
	}

	public void setLowerleft_lon(double lowerleft_lon) {
		this.lowerleft_lon = lowerleft_lon;
	}

	public double getUpperright_lat() {
		return upperright_lat;
	}

	public void setUpperright_lat(double upperright_lat) {
		this.upperright_lat = upperright_lat;
	}

	public double getUpperright_lon() {
		return upperright_lon;
	}

	public void setUpperright_lon(double upperright_lon) {
		this.upperright_lon = upperright_lon;
	}

	public double getLowerright_lat() {
		return lowerright_lat;
	}

	public void setLowerright_lat(double lowerright_lat) {
		this.lowerright_lat = lowerright_lat;
	}

	public double getLowerright_lon() {
		return lowerright_lon;
	}

	public void setLowerright_lon(double lowerright_lon) {
		this.lowerright_lon = lowerright_lon;
	}

	public JSONObject getGeoJSON() {
		return geoJSON;
	}

	public void setGeoJOSN(JSONObject geoJSON) {
		this.geoJSON = geoJSON;
	}

}
