package one.luckyminer.eosminer.model;

public class Location {
	// {"country":"Singapore","latitude":1.53,"longitude":103.74,"name":"ZBnode1"}
	private String country;
	private String latitude;
	private String longitude;
	private String name;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "{country:" + country + ",latitude:" + latitude + ",longitude:" + longitude + ",name:" + name + "}";

	}
}
