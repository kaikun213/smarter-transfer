package com.smarter_transfer.springrest.security.auth;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/auth")
@Transactional
public class KeyResource {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ApiResponse getKeshId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT keshId from USER where userId = :userId");
		query.setParameter("userId", auth.getName());
		return new ApiResponse(Status.OK, query.uniqueResult().toString());
	}

}
