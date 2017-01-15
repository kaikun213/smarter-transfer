package com.smarter_transfer.springrest.registration.merchant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarter_transfer.springrest.registration.merchant.PointOfSaleService;

@RestController
@RequestMapping("/pos")
public class PointOfSaleResource {
	
	@Autowired
	PointOfSaleService posService;

	public PointOfSaleResource(){}
	
	public PointOfSaleResource(PointOfSaleService posService){
		this.posService = posService;
	}
	
	
}
