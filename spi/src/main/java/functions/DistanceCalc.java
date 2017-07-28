package functions;

public class DistanceCalc {
	
	private static final double rho = Math.PI / 180;
	private static final double r_earth = 6378.388; // Hayford-Ellipsoid, 1910/24, Äquatorradius
	
	public static double getKilometers(double lat1, double lon1, double lat2, double lon2) {
		lat1 = lat1 * rho;
		lon1 = lon1 * rho;
		lat2 = lat2 * rho;
		lon2 = lon2 * rho;
		// http://www.kompf.de/gps/distcalc.html
		return r_earth * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
	}
	
}
