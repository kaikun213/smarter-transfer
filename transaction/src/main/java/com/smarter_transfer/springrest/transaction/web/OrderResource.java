package com.smarter_transfer.springrest.transaction.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.app.model.order.Order;
//import common.app.repository.order.OrderService;
import common.app.web.ApiResponse;
import common.app.web.ApiResponse.ApiError;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/orders")
public class OrderResource {
	
//	@Autowired
//	OrderService orderService;
//	
//	@RequestMapping(value="/{userId}", method=RequestMethod.POST, consumes = "application/json")
//	public ApiResponse addItem(@PathVariable long userId,@RequestBody OrderDTO orderDTO){
//		try{
//			Order newOrder = createOrder(userId, orderDTO);
//			orderService.save(newOrder);
//			return new ApiResponse(Status.OK,new OrderDTO(newOrder), null);
//		}
//		catch (Exception e){
//            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
//		}
//	}
	
	private Order createOrder(long userId, OrderDTO orderDTO){
		// TODO write order object (look item create)
		return null;
	}

}
