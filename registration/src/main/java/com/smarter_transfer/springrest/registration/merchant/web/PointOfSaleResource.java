package com.smarter_transfer.springrest.registration.merchant.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarter_transfer.springrest.registration.item.MenuService;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;
import com.smarter_transfer.springrest.registration.merchant.PointOfSaleService;

import common.app.model.merchant.PointOfSale;
import common.app.web.ApiResponse;
import common.app.web.ListApiResponse;
import common.app.web.ApiResponse.ApiError;
import common.app.web.ApiResponse.Status;

@RestController
@RequestMapping("/pos")
public class PointOfSaleResource {
	
	@Autowired
	PointOfSaleService posService;
	@Autowired
	MerchantService merchantService;
	@Autowired
	MenuService menuService;

	public PointOfSaleResource(){}
	
	public PointOfSaleResource(PointOfSaleService posService){
		this.posService = posService;
	}
	
	@RequestMapping(value="/{merchantId}", method=RequestMethod.POST, consumes = "application/json")
	public ApiResponse addPOS(@PathVariable long merchantId,@RequestBody PointOfSaleDTO posDTO){
		try{
			PointOfSale newPointOfSale = createPOS(merchantId, posDTO);
			posService.addPOS(newPointOfSale);
			return new ApiResponse(Status.OK,new PointOfSaleDTO(newPointOfSale), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	

	@RequestMapping(value="/{merchantId}/{posId}", method=RequestMethod.GET, produces = "application/json")
	public ApiResponse getPOS(@PathVariable long merchantId,@PathVariable long posId){
		try {
			PointOfSale pos = posService.getPOS(merchantId, posId);
			return new ApiResponse(Status.OK, new PointOfSaleDTO(pos), null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}/{posId}", method = RequestMethod.PUT, consumes = "application/json")
	public ApiResponse updatePOS(@PathVariable long merchantId,  @PathVariable long posId, @RequestBody PointOfSaleDTO posDTO){
		try{
			PointOfSale pos = posService.getPOS(merchantId, posId);
			updatePOS(posDTO,pos);
			return new ApiResponse(Status.OK,new PointOfSaleDTO(pos),null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ApiResponse deletePOS(@PathVariable long merchantId,@RequestParam(value = "posId", required = true) long posId){
		try{
			posService.deletePOS(merchantId, posId);
			return new ApiResponse(Status.OK,null,null);
		}
		catch (Exception e){
            return new ApiResponse(Status.ERROR, null, new ApiError(400, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{merchantId}", method=RequestMethod.GET, produces = "application/json")
	public ListApiResponse getPOSs(@PathVariable long merchantId,
										@RequestParam(value = "page", required = false, defaultValue="1") int page,
										@RequestParam(value = "limit", required = false, defaultValue="10") int limit){
			List<Object> poss = posService.getPOSs(merchantId, (page-1)*limit, limit).stream().map(PointOfSaleDTO::new).collect(Collectors.toList());
			// calculate how many pages there are in total with object limit per page
			// amount devided by limit and possibly +1 if there is a rest
			long total = (long)((posService.count(merchantId)/limit));
			if ((posService.count(merchantId)%limit) > 0) total++;
			
			String nextPage = "none";
			if (page < total) nextPage = "http://localhost:8080/api/v1/merchants"+merchantId+"?page="+(page+1)+"&limit="+limit;
			return new ListApiResponse(Status.OK,poss, null, page, nextPage, total);
	}
	
	private PointOfSale createPOS(long merchantId, PointOfSaleDTO posDTO){
		if (posDTO.getPosId() > 0) throw new IllegalArgumentException("PointOfSaleId will be generated, do not include.");
		if (posDTO.getMerchantId() != merchantId) throw new IllegalArgumentException("MerchantIds in RequestBody and URL not matching!");
		PointOfSale pos = new PointOfSale();
		pos.setMerchant(merchantService.getMerchant(merchantId));
		pos.setMenu(menuService.getMenu(merchantId, posDTO.getMenuId()));
		pos.setLocation(posDTO.getLocation());
		return pos;
	}
	
	private void updatePOS(PointOfSaleDTO posDTO, PointOfSale pos){
		if (posDTO.getPosId() > 0 ) throw new IllegalArgumentException("PointOfSaleId can not get updated, do not include.");
		else if (posDTO.getMerchantId() > 0)  throw new IllegalArgumentException("MerchantId can not get updated, do not include.");
		pos.setMenu(menuService.getMenu(pos.getMerchant().getMerchantId(), posDTO.getMenuId()));
		pos.setLocation(posDTO.getLocation());
		posService.updatePOS(pos);
	}
	
	
}
