package com.smarter_transfer.springrest.registration.item.web;

import java.util.List;
import java.util.stream.Collectors;

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
import common.app.web.ListApiResponse;
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
	
	@RequestMapping(value="/{merchantId}", method=RequestMethod.POST, consumes = "application/json")
	public ApiResponse addItem(@PathVariable long merchantId,@RequestBody ItemDTO itemDTO){
		try{
			Item newItem = createItem(merchantId, itemDTO);
			itemService.addItem(newItem);
			return new ApiResponse(Status.OK,new ItemDTO(newItem), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	

	@RequestMapping(value="/{merchantId}/{itemId}", method=RequestMethod.GET, produces = "application/json")
	public ApiResponse getItem(@PathVariable long merchantId,@PathVariable long itemId){
		try {
			Item item = itemService.getItem(merchantId, itemId);
			return new ApiResponse(Status.OK, new ItemDTO(item), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}/{itemId}", method = RequestMethod.PUT, consumes = "application/json")
	public ApiResponse updateItem(@PathVariable long merchantId,  @PathVariable long itemId, @RequestBody ItemDTO itemDTO){
		try{
			Item item = itemService.getItem(merchantId, itemId);
			updateItem(itemDTO,item);
			return new ApiResponse(Status.OK,new ItemDTO(item),null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ApiResponse deleteItem(@PathVariable long merchantId,@RequestParam(value = "itemId", required = true) long itemId){
		try{
			itemService.deleteItem(merchantId, itemId);
			return new ApiResponse(Status.OK,null,null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method=RequestMethod.GET, produces = "application/json")
	public ListApiResponse getItems(@PathVariable long merchantId,
										@RequestParam(value = "page", required = false, defaultValue="1") int page,
										@RequestParam(value = "limit", required = false, defaultValue="10") int limit){
			List<Object> items = itemService.getItems(merchantId, (page-1)*limit, limit).stream().map(ItemDTO::new).collect(Collectors.toList());
			// calculate how many pages there are in total with object limit per page
			// amount devided by limit and possibly +1 if there is a rest
			long total = (long)((itemService.count(merchantId)/limit));
			if ((itemService.count(merchantId)%limit) > 0) total++;
			
			String nextPage = "none";
			if (page < total) nextPage = "http://localhost:8080/api/v1/merchants"+merchantId+"?page="+(page+1)+"&limit="+limit;
			return new ListApiResponse(Status.OK,items, null, page, nextPage, total);
	}
	
	private Item createItem(long merchantId, ItemDTO itemDTO){
		if (itemDTO.getItemId() > 0) throw new IllegalArgumentException("ItemId will be generated, do not include.");
		if (itemDTO.getMerchantId() != merchantId) throw new IllegalArgumentException("MerchantIds in RequestBody and URL not matching!");
		Item item = new Item();
		item.setMerchant(merchantService.getMerchant(merchantId));
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		return item;
	}
	
	private void updateItem(ItemDTO itemDTO, Item item){
		if (itemDTO.getItemId() > 0 ) throw new IllegalArgumentException("ItemId can not get updated, do not include.");
		else if (itemDTO.getMerchantId() > 0)  throw new IllegalArgumentException("MerchantId can not get updated, do not include.");
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		itemService.updateItem(item);
	}

}
