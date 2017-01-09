package com.smarter_transfer.springrest.registration.item.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smarter_transfer.springrest.registration.item.ItemService;
import com.smarter_transfer.springrest.registration.item.model.Item;
import com.smarter_transfer.springrest.registration.item.model.ItemPK;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;

import common.app.web.ApiResponse;
import common.app.web.ApiResponse.ApiError;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/items")
public class ItemResource {
	
	@Autowired
	ItemService itemService;
	@Autowired
	MerchantService merchantService;
	
	public ItemResource(){}
	
	public ItemResource(ItemService itemService){
		this.itemService = itemService;
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes = "application/json")
	public ApiResponse addItem(@RequestBody ItemDTO itemDTO){
		try{
			Item newItem = createItem(itemDTO);
			itemService.addItem(newItem);
			return new ApiResponse(Status.OK,new ItemDTO(newItem), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	private Item createItem(ItemDTO itemDTO){
		if (itemDTO.getItemId() > 0) throw new IllegalArgumentException("ItemId will be generated, do not include.");
		Item item = new Item();
		ItemPK itemPK = new ItemPK();
		itemPK.setMerchant(merchantService.getMerchant(itemDTO.getMerchantId()));
		item.setItemPK(itemPK);
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		return item;
	}

}
