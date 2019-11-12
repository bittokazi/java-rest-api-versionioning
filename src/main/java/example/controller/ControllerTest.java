package example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.api.versioning.RestRequestEntity;
import rest.api.versioning.RestResponseEntity;
import springapp.model.User;
import springapp.model.UserV1;

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
