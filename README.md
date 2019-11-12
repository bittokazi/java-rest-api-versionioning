# Start by Copying files:
  Copy all the files from rest.api.versioning package or copy the whole rest.api.versioning package to your project.

# How to use the library. Lets create domain level object which will not change with version

Example domain level Class User.java

```sh
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
```

Example domain level Class Address.java

```sh
    public class Address {
        String street;
        String house;
        String city;
        String country;
        public String getStreet() {
            return street;
        }
        public void setStreet(String street) {
            this.street = street;
        }
        public String getHouse() {
            return house;
        }
        public void setHouse(String house) {
            this.house = house;
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
```

# Create Version 1 rest response/request class for project

Example Version 1 Userv1.java

```sh
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
```

Example Version 1 Addressv1.java

```sh
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

```

# Lets create a controller to serve Version 1 rest api.

```sh

    @RestController
    @RequestMapping(value = "/rest")
    public class ControllerTest {
        @GetMapping("/user")
        public UserV1 userGet() {
            User user = User.generateUser();
            return new RestResponseEntity<UserV1>(UserV1.class).toResponseObject(user);
        }
        @PostMapping("/user")
        public UserV1 userPost(@RequestBody UserV1 userV1) {
            User user = new RestRequestEntity<User>(User.class).toRequestObject(userV1);
            // Domain level model user do database operation or other operation
            //........
            return new RestResponseEntity<UserV1>(UserV1.class).toResponseObject(user);
        }
        @GetMapping("/user-list")
        public List<UserV1> userPost() {
            List<User> users = new ArrayList<User>();
            User user = User.generateUser();
            users.add(user);
            User user1 = User.generateUser();
            user1.setId(31L);
            users.add(user1);
            return new RestResponseEntity<List<UserV1>>(UserV1.class).toResponseObject(users);
        }
        @PostMapping("/user-list")
        public List<UserV1> userPost(@RequestBody List<UserV1> userV1s) {
            List<User> users = new RestRequestEntity<List<User>>(User.class).toRequestObject(userV1s);
            // Domain level model users do database operation or other operation
            //........
            return new RestResponseEntity<List<UserV1>>(UserV1.class).toResponseObject(users);
        }
    }

```