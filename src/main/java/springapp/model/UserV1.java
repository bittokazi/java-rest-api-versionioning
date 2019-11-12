package springapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import rest.api.versioning.ExposeProperty;

public class UserV1 {
	
	@ExposeProperty
	Long id;
	
	@ExposeProperty(getMethod="getTheFullName", setMethod = "breakFullName")
	String name;
	
	@ExposeProperty(name="dateOfBirth")
	Date dob;
	
	@ExposeProperty()
	AddressV1 address;
	
	public String getTheFullName(User user) {
		return user.getFirstName()+" "+user.getLastName();
	}
	
	public void breakFullName(User user, UserV1 userV1) {
		user.setFirstName(userV1.getName().split(" ")[0]);
		user.setLastName(userV1.getName().split(" ")[1]);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public AddressV1 getAddress() {
		return address;
	}

	public void setAddress(AddressV1 address) {
		this.address = address;
	}
}
