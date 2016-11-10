package com.smarter_transfer.springrest.registration.merchant.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smarter_transfer.springrest.registration.merchant.MerchantService;
import com.smarter_transfer.springrest.registration.merchant.model.ContactPerson;
import com.smarter_transfer.springrest.registration.merchant.model.Merchant;
import com.smarter_transfer.springrest.registration.merchant.model.Theme;

import common.app.model.Address;
import common.app.web.ApiResponse;
import common.app.web.ApiResponse.ApiError;
import common.app.web.ListApiResponse;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/merchants")
public class MerchantResource {
	
	@Autowired
	MerchantService merchantService;
	
	public MerchantResource(){}
	
	public MerchantResource(MerchantService merchantService){
		this.merchantService = merchantService;
	}
	
	@RequestMapping(value="/{merchantId}", method=RequestMethod.GET, produces = "application/json")
	public ApiResponse getMerchant(@PathVariable long merchantId){
		try {
			Merchant merchant = merchantService.getMerchant(merchantId);
			return new ApiResponse(Status.OK, new MerchantDTO(merchant), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, produces = "application/json")
	public ListApiResponse getMerchants(@RequestParam(value = "page", required = false, defaultValue=1) int page,
										@RequestParam(value = "limit", required = false, defaultValue=10) int limit){
			List<Object> merchants = merchantService.getMerchants((page-1)*limit, limit).stream().map(MerchantDTO::new).collect(Collectors.toList());
			// calculate how many pages there are in total with object limit per page
			long total = (long)((merchants.size()/limit) + ((merchants.size()%limit)*1));
			return new ListApiResponse(Status.OK,merchants, null, page,"http://localhost:8080/merchants?page="+(page+1), total);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes = "application/json")
	public ApiResponse addMerchant(@RequestBody MerchantDTO merchantDTO){
		try{
			merchantService.checkUniqueKeshId(merchantDTO.getMerchantId(), merchantDTO.getKeshId());
			Merchant newMerchant = createMerchant(merchantDTO);
			merchantService.addMerchant(newMerchant);
			return new ApiResponse(Status.OK,new MerchantDTO(newMerchant), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.PUT, consumes = "application/json")
	public ApiResponse updateMerchant(@RequestBody MerchantDTO merchantDTO, @PathVariable long merchantId){
		try{
			merchantService.checkUniqueKeshId(merchantId, merchantDTO.getKeshId());
			Merchant merchant = merchantService.getMerchant(merchantId);
			updateMerchant(merchantDTO,merchant);
			return new ApiResponse(Status.OK,new MerchantDTO(merchant),null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ApiResponse deleteMerchant(@PathVariable long merchantId){
		try{
			merchantService.deleteMerchant(merchantId);
			return new ApiResponse(Status.OK,null,null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	public long countMerchants(){
		return merchantService.count();
	}
	
	private Merchant createMerchant(MerchantDTO merchantDTO){
		if (merchantDTO.getMerchantId() > 0) throw new IllegalArgumentException("MerchantId will be generated, do not include.");
		Merchant merchant = new Merchant();
		merchant.setKeshId(merchantDTO.getKeshId());
		merchant.setCompanyName(merchantDTO.getCompanyName());
		merchant.setPhoneNumber(merchantDTO.getPhoneNumber());
		merchant.setEmail(merchantDTO.getEmail());
		merchant.setContact(new ContactPerson(merchantDTO.getContactForename(), merchantDTO.getContactSurname(), merchantDTO.getContactEmail()));
		merchant.setAddress(new Address(merchantDTO.getCountry(), merchantDTO.getState(), merchantDTO.getCity(), merchantDTO.getZip(), merchantDTO.getAddressLine1(), merchantDTO.getAddressLine2()));
		merchant.setUstId(merchantDTO.getUstId());
		merchant.setLogoId(merchantDTO.getLogoId());
		if (merchantDTO.getThemeIds() != null){
			for (long themeId : merchantDTO.getThemeIds()) merchant.addTheme(new Theme(themeId));
		}
		merchant.setWebsiteURL(merchantDTO.getWebsiteURL());
		merchant.setShopURL(merchantDTO.getShopURL());
		merchant.setTicketURL(merchantDTO.getTicketURL());
	    merchant.getContact().setMerchant(merchant);
		return merchant;
	}
	private void updateMerchant(MerchantDTO merchantDTO, Merchant merchant){
		merchant.setKeshId(merchantDTO.getKeshId());
		merchant.setCompanyName(merchantDTO.getCompanyName());
		merchant.setPhoneNumber(merchantDTO.getPhoneNumber());
		merchant.setEmail(merchantDTO.getEmail());
		merchant.getContact().setEmail(merchantDTO.getEmail());
		merchant.getContact().setForename(merchantDTO.getContactForename());
		merchant.getContact().setSurname(merchantDTO.getContactSurname());
		merchant.setAddress(new Address(merchantDTO.getCountry(), merchantDTO.getState(), merchantDTO.getCity(), merchantDTO.getZip(), merchantDTO.getAddressLine1(), merchantDTO.getAddressLine2()));
		merchant.setUstId(merchantDTO.getUstId());
		merchant.setLogoId(merchantDTO.getLogoId());
		if (merchantDTO.getThemeIds() != null){
			for (long themeId : merchantDTO.getThemeIds()) merchant.addTheme(new Theme(themeId));
		}
		merchant.setWebsiteURL(merchantDTO.getWebsiteURL());
		merchant.setShopURL(merchantDTO.getShopURL());
		merchant.setTicketURL(merchantDTO.getTicketURL());
		merchantService.updateMerchant(merchant);
	}
}
