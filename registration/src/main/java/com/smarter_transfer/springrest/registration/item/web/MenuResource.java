package com.smarter_transfer.springrest.registration.item.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smarter_transfer.springrest.registration.item.ItemService;
import com.smarter_transfer.springrest.registration.item.MenuService;
import com.smarter_transfer.springrest.registration.item.model.Item;
import com.smarter_transfer.springrest.registration.item.model.Menu;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;

import common.app.web.ApiResponse;
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
	
	@RequestMapping(method=RequestMethod.POST, consumes = "application/json")
	public ApiResponse addMenu(@RequestBody MenuDTO menuDTO){
		try{
			Menu newMenu = createMenu(menuDTO);
			menuService.addMenu(newMenu);
			return new ApiResponse(Status.OK,new MenuDTO(newMenu), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	private Menu createMenu(MenuDTO menuDTO){
		if (menuDTO.getMenuId() > 0) throw new IllegalArgumentException("MenuId will be generated, do not include.");
		Menu menu = new Menu();
		menu.setMerchant(merchantService.getMerchant(menuDTO.getMerchantId()));
		menu.setName(menuDTO.getName());
		menu.setItems(menuDTO.getItems());
		return menu;
	}
	
	private void updateMenu(MenuDTO menuDTO, Menu menu){
		if (menuDTO.getMenuId() > 0 ) throw new IllegalArgumentException("MenuId can not get updated, do not include.");
		else if (menuDTO.getMerchantId() > 0)  throw new IllegalArgumentException("MerchantId can not get updated, do not include.");
		menu.setName(menuDTO.getName());
		menu.setItems(menuDTO.getItems());
		menuService.updateMenu(menu);
	}

}
