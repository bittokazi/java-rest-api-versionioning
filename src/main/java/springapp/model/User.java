package springapp.model;

import java.util.Date;

public class User {
	Long id;
	String firstName;
	String lastName;
	Date dateOfBirth;
	Address address;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public static User generateUser() {
		User user = new User();
		user.setId(21L);
		user.setFirstName("Joye");
		user.setLastName("Joe");

		user.setDateOfBirth(new Date(System.currentTimeMillis()));
		
		Address address = new Address();
		address.setHouse("H0403");
		address.setStreet("JoeStreet33");
		address.setCity("JoeCity");
		address.setCountry("IsleOfMan");
		user.setAddress(address);
		
		return user;
	}
}
