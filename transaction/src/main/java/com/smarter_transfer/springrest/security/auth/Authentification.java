package com.smarter_transfer.springrest.security.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/auth")
public class Authentification {
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ApiResponse getKeshId(){
		return new ApiResponse(Status.OK, "Test", null);
	}

}
