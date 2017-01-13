package com.smarter_transfer.springrest.registration.item.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarter_transfer.springrest.registration.item.ItemService;
import com.smarter_transfer.springrest.registration.item.model.Item;
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
	

	@RequestMapping(value="/{merchantId}", method=RequestMethod.GET, produces = "application/json")
	public ApiResponse getItem(@PathVariable long merchantId,@RequestParam(value = "itemId", required = true) long itemId){
		try {
			Item item = itemService.getItem(merchantId, itemId);
			return new ApiResponse(Status.OK, new ItemDTO(item), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.PUT, consumes = "application/json")
	public ApiResponse updateItem(@RequestBody ItemDTO itemDTO, @PathVariable long merchantId){
		try{
			Item item = itemService.getItem(merchantId, itemDTO.getItemId());
			updateItem(itemDTO,item);
			return new ApiResponse(Status.OK,new ItemDTO(item),null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	private Item createItem(ItemDTO itemDTO){
		if (itemDTO.getItemId() > 0) throw new IllegalArgumentException("ItemId will be generated, do not include.");
		Item item = new Item();
		item.setMerchant(merchantService.getMerchant(itemDTO.getMerchantId()));
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		return item;
	}
	
	private void updateItem(ItemDTO itemDTO, Item item){
		item.setMerchant(merchantService.getMerchant(itemDTO.getMerchantId()));
		item.setItemId(itemDTO.getItemId());
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		itemService.updateItem(item);
	}

}
