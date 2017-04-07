package classes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoundingBox {

	private double upperleft_lat = -1.0;
	private double upperleft_lon = -1.0;
	private double lowerleft_lat = -1.0;
	private double lowerleft_lon = -1.0;
	private double upperright_lat = -1.0;
	private double upperright_lon = -1.0;
	private double lowerright_lat = -1.0;
	private double lowerright_lon = -1.0;
	private String geoJSON = "";

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
		this.geoJSON = "{\"type\":\"Polygon\",\"coordinates\":[[[" + lowerleft_lon + "," + lowerleft_lat + "],[" + upperleft_lon + "," + upperleft_lat + "],[" + upperright_lon + "," + upperright_lat + "],[" + lowerright_lon + "," + lowerright_lat + "],[" + lowerleft_lon + "," + lowerleft_lat + "]]]}";
	}

	public void getBoundingBoxFromPostgisBOX(String PostgisBOX) {
		String extent[] = PostgisBOX.replace("BOX(", "").replace(")", "").split(",");
		this.lowerleft_lon = Double.parseDouble(extent[0].split(" ")[0]);
		this.lowerleft_lat = Double.parseDouble(extent[0].split(" ")[1]);
		this.upperright_lon = Double.parseDouble(extent[1].split(" ")[0]);
		this.upperright_lat = Double.parseDouble(extent[1].split(" ")[1]);
		this.lowerright_lon = Double.parseDouble(extent[1].split(" ")[0]);
		this.lowerright_lat = Double.parseDouble(extent[0].split(" ")[1]);
		this.upperleft_lon = Double.parseDouble(extent[0].split(" ")[0]);
		this.upperleft_lat = Double.parseDouble(extent[1].split(" ")[1]);
	}

	public void getBoundingBoxFromLatLon(String lat, String lon, String radius) throws IOException, ClassNotFoundException, SQLException {
		/*PostGIS db = new PostGIS();
		 ResultSet rs;
		 rs = db.getBoundingBoxFromPoint4326(lat, lon, radius);
		 while (rs.next()) {
		 String extent[] = rs.getString("extent").replace("BOX(", "").replace(")", "").split(",");
		 this.lowerleft_lon = Double.parseDouble(extent[0].split(" ")[0]);
		 this.lowerleft_lat = Double.parseDouble(extent[0].split(" ")[1]);
		 this.upperright_lon = Double.parseDouble(extent[1].split(" ")[0]);
		 this.upperright_lat = Double.parseDouble(extent[1].split(" ")[1]);
		 this.lowerright_lon = Double.parseDouble(extent[1].split(" ")[0]);
		 this.lowerright_lat = Double.parseDouble(extent[0].split(" ")[1]);
		 this.upperleft_lon = Double.parseDouble(extent[0].split(" ")[0]);
		 this.upperleft_lat = Double.parseDouble(extent[1].split(" ")[1]);
		 }
		 rs = db.getBoundingBoxGeoJSONFromPoint4326(lat, lon, radius);
		 while (rs.next()) {
		 this.geoJSON = rs.getString("extent");
		 }
		 db.close();*/
		this.lowerleft_lon = Double.parseDouble(lon)-10.0;
		this.lowerleft_lat = Double.parseDouble(lat)-10.0;
		this.upperright_lon = Double.parseDouble(lon)+10.0;
		this.upperright_lat = Double.parseDouble(lat)+10.0;
		this.lowerright_lon = Double.parseDouble(lon)+10.0;
		this.lowerright_lat = Double.parseDouble(lat)+10.0;
		this.upperleft_lon = Double.parseDouble(lon)-10.0;
		this.upperleft_lat = Double.parseDouble(lat)-10.0;
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

	public String getGeoJSON() {
		return geoJSON;
	}

	public void setGeoJOSN(String geoJOSN) {
		this.geoJSON = geoJOSN;
	}

}
