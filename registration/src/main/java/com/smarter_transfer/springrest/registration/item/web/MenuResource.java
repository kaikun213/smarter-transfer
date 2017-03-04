package com.smarter_transfer.springrest.registration.item.web;

import java.util.ArrayList;
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
import com.smarter_transfer.springrest.registration.item.MenuService;
import com.smarter_transfer.springrest.registration.item.model.Item;
import com.smarter_transfer.springrest.registration.item.model.Menu;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;

import common.app.web.ApiResponse;
import common.app.web.ListApiResponse;
import common.app.web.ApiResponse.ApiError;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/menus")
public class MenuResource {
	
	@Autowired
	MenuService menuService;
	@Autowired
	MerchantService merchantService;
	@Autowired
	ItemService itemService;
	
	public MenuResource(){}
	
	public MenuResource(MenuService menuService){
		this.menuService = menuService;
	}
	
	@RequestMapping(value="/{merchantId}", method=RequestMethod.POST, consumes = "application/json")
	public ApiResponse addMenu(@PathVariable long merchantId, @RequestBody MenuDTO menuDTO){
		try{
			Menu newMenu = createMenu(merchantId, menuDTO);
			menuService.addMenu(newMenu);
			return new ApiResponse(Status.OK,new MenuDTO(newMenu), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value="/{merchantId}/{menuId}", method=RequestMethod.GET, produces = "application/json")
	public ApiResponse getMenu(@PathVariable long merchantId,@PathVariable long menuId){
		try {
			Menu menu = menuService.getMenu(merchantId, menuId);
			return new ApiResponse(Status.OK, new MenuDTO(menu), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}/{menuId}", method = RequestMethod.PUT, consumes = "application/json")
	public ApiResponse updateMenu(@PathVariable long merchantId,  @PathVariable long menuId, @RequestBody MenuDTO menuDTO){
		try{
			Menu menu = menuService.getMenu(merchantId, menuId);
			updateMenu(menuDTO,menu);
			return new ApiResponse(Status.OK,new MenuDTO(menu),null);
		}
		catch (Exception e){
			e.printStackTrace();
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ApiResponse deleteMenu(@PathVariable long merchantId,@RequestParam(value = "menuId", required = true) long menuId){
		try{
			menuService.deleteMenu(merchantId, menuId);
			return new ApiResponse(Status.OK,null,null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method=RequestMethod.GET, produces = "application/json")
	public ListApiResponse getMenus(@PathVariable long merchantId,
										@RequestParam(value = "page", required = false, defaultValue="1") int page,
										@RequestParam(value = "limit", required = false, defaultValue="10") int limit){
			List<Object> menus = menuService.getMenus(merchantId, (page-1)*limit, limit).stream().map(MenuDTO::new).collect(Collectors.toList());
			// calculate how many pages there are in total with object limit per page
			// amount devided by limit and possibly +1 if there is a rest
			long total = (long)((menuService.count(merchantId)/limit));
			if ((menuService.count(merchantId)%limit) > 0) total++;
			
			String nextPage = "none";
			if (page < total) nextPage = "http://localhost:8080/api/v1/merchants/"+merchantId+"?page="+(page+1)+"&limit="+limit;
			return new ListApiResponse(Status.OK,menus, null, page, nextPage, total);
	}
	
	private Menu createMenu(long merchantId, MenuDTO menuDTO){
		if (menuDTO.getMenuId() > 0) throw new IllegalArgumentException("MenuId will be generated, do not include.");
		if (menuDTO.getMerchantId() != merchantId) throw new IllegalArgumentException("MerchantIds in RequestBody("+menuDTO.getMerchantId()+") and URL("+merchantId+") not matching!");
		Menu menu = new Menu();
		menu.setMerchant(merchantService.getMerchant(menuDTO.getMerchantId()));
		menu.setName(menuDTO.getName());
		// EAGER FETCH => Otherwise unsaved transient objects
		for (Item i : menuDTO.getItems()){
			menu.getItems().add(itemService.getItem(merchantId, i.getItemId()));
		}
		return menu;
	}
	
	private void updateMenu(MenuDTO menuDTO, Menu menu){
		if (menuDTO.getMenuId() > 0 ) throw new IllegalArgumentException("MenuId can not get updated, do not include.");
		else if (menuDTO.getMerchantId() > 0)  throw new IllegalArgumentException("MerchantId can not get updated, do not include.");
		menu.setName(menuDTO.getName());
		// EAGER FETCH first
		List<Item> items = new ArrayList<Item>();
		for (Item i : menuDTO.getItems()){
			items.add(itemService.getItem(menu.getMerchant().getMerchantId(), i.getItemId()));
		}
		menu.setItems(items);
		menuService.updateMenu(menu);
	}

}
