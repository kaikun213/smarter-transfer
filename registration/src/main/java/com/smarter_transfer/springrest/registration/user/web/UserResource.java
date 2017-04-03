package com.smarter_transfer.springrest.registration.user.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smarter_transfer.springrest.registration.user.UserService;

import common.app.model.merchant.Theme;
import common.app.model.user.User;
import common.app.web.ApiResponse;
import common.app.web.ListApiResponse;
import common.app.web.ApiResponse.ApiError;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
    private UserService userService;
	
	public UserResource(){}
	
	public UserResource(UserService userService){
		this.userService = userService;
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ApiResponse getUser(@PathVariable long userId){
		try {
			User user = userService.getUser(userId);
			return new ApiResponse(Status.OK,new UserDTO(user), null);
        } 
		catch (Exception e) {
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
        }
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ApiResponse addUser(@RequestBody UserDTO userDTO, @RequestParam(value="keshId", required=true) String keshId,
								@RequestParam(value="password", required=true) String password){
		try{
			userService.checkUniqueKeshId(userDTO.getUserId(), keshId);
			User newUser = createUser(userDTO, keshId, password);
			userService.addUser(newUser);
			return new ApiResponse(Status.OK,new UserDTO(newUser), null);
		}
		catch (Exception e) {
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
        }
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = "application/json")
	public ApiResponse updateUser(@RequestBody UserDTO userDTO, @PathVariable long userId){
		try{
			User user = userService.getUser(userId);
			updateUser(userDTO,user);
			return new ApiResponse(Status.OK,new UserDTO(user),null);
		}
		catch (Exception e) {
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
        }
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ApiResponse deleteUser(@PathVariable long userId){
		try{
			userService.deleteUser(userId);
			return new ApiResponse(Status.OK,null,null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	/* For testing purpose only */
	@RequestMapping(method=RequestMethod.GET, produces = "application/json")
	public ListApiResponse getUsers(){
			List<Object> users = userService.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
			int page = 1;
			return new ListApiResponse(Status.OK,users, null, page,"http://localhost:8080/users?page="+page, (long)users.size());
	}
	
	public long countUsers(){
		return userService.count();
	}
	
	private User createUser(UserDTO userDTO, String keshId, String password){
		User u = new User();
		if (userDTO.getUserId() > 0) throw new IllegalArgumentException("UserId will be generated, do not include.");
		u.setKeshId(keshId);
		u.setPassword(password);
		u.setName(userDTO.getName());
		u.setDeviceId(userDTO.getDeviceId());
		u.setTheme(new Theme(userDTO.getThemeId()));
		u.setLocation(userDTO.getLocation());
		return u;
	}
	
	private void updateUser(UserDTO userDTO, User user){
		user.setName(userDTO.getName());
		user.setDeviceId(userDTO.getDeviceId());
		user.setTheme(new Theme(userDTO.getThemeId()));
		user.setLocation(userDTO.getLocation());
		userService.updateUser(user);
	}
}
