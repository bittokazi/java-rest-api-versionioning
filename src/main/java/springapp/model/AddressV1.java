package springapp.model;

import rest.api.versioning.ExposeProperty;

public class AddressV1 {
	@ExposeProperty(getMethod="getAddressLineV1", setMethod = "setAddressLineV1")
	String addressLine;
	
	@ExposeProperty
	String city;
	
	@ExposeProperty
	String country;
	
	public String getAddressLineV1(Address address) {
		return "House "+address.getHouse()+", Street "+address.getStreet();
	}
	
	public void setAddressLineV1(Address address, AddressV1 addressV1) {
		address.setHouse(addressV1.getAddressLine().split(",")[0].split(" ")[1]);
		address.setStreet(addressV1.getAddressLine().split(",")[1].split(" ")[2]);
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
