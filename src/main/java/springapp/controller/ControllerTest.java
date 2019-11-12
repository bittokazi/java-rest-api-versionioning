package springapp.controller;

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
        return new RestResponseEntity<UserV1>(UserV1.class).toResponseObject(user);
    }
}
