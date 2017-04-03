package integrationTests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smarter_transfer.springrest.registration.WebApplication;
import com.smarter_transfer.springrest.registration.item.ItemService;
import com.smarter_transfer.springrest.registration.item.web.ItemDTO;
import com.smarter_transfer.springrest.registration.item.web.ItemResource;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;

import common.app.model.item.Item;
import common.app.model.merchant.Merchant;
import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;
import common.app.web.ListApiResponse;
import common.app.web.config.JsonConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class, JsonConfiguration.class})
@ContextConfiguration("file:src/test/resources/registration-test.xml")
@Transactional
public class ItemResourceIntegrationTest {
	
	@Autowired
	ItemResource itemResource;
	@Autowired
	MerchantService merchantService;
	/* for internal tests e.g. timestamp update */
	@Autowired
	ItemService itemService;
	

    Merchant merchant;
    Item item1;
	
    /* SETUP Tests */
    @Before
	public void setup(){
    	createMerchantPhysically();
    	item1 = new Item(merchant,"testItem", "testDescription", 10.0);
    	
    	ApiResponse response = itemResource.addItem(item1.getMerchant().getMerchantId(), new ItemDTO(item1));
    	item1.setItemId(((ItemDTO)response.getData()).getItemId());
    }
    
    private void createMerchantPhysically(){
		merchant = new Merchant();
		merchant.setKeshId(1);
		merchantService.addMerchant(merchant);
	}
   	
    @Test
    public void testAddItemSuccess(){
    	// setup
    	Item item2 = new Item(merchant, "testItem2", "testDescription2", 20.0);
    	
    	// test
    	ApiResponse response = itemResource.addItem(item1.getMerchant().getMerchantId(), new ItemDTO(item2));
    	item2.setItemId(((ItemDTO)response.getData()).getItemId());
    	assertEquals("testItem2", ((ItemDTO)response.getData()).getName());
    }
    
    @Test
    public void testGetItemSuccess(){
    	ApiResponse response = itemResource.getItem(item1.getMerchant().getMerchantId(), item1.getItemId());
    	assertEquals(item1.getItemId(), ((ItemDTO)response.getData()).getItemId());
    }
    
    @Test
    public void testUpdateItemSuccess(){
    	ItemDTO itemDTO = new ItemDTO();
		/* Passing modified ItemDTO , with itemId and merchantId excluded! */
		itemDTO.setName("updatedItem");
		itemDTO.setDescription(item1.getDescription());
		itemDTO.setPrice(item1.getPrice());
    	ApiResponse response = itemResource.updateItem(item1.getMerchant().getMerchantId(), item1.getItemId(), itemDTO);
    	assertEquals("updatedItem", ((ItemDTO)response.getData()).getName());
    }
    
    @Test
	public void testDeleteItemSuccess(){
		ApiResponse response = itemResource.deleteItem(item1.getMerchant().getMerchantId(), item1.getItemId());
		assertEquals(response.getStatus(), Status.OK);
		response = itemResource.getItem(item1.getMerchant().getMerchantId(), item1.getItemId());
		assertEquals(response.getStatus(), Status.ERROR);		
	}
    
    @Test
    public void testGetItemsSuccess(){
    	ListApiResponse response = itemResource.getItems(item1.getMerchant().getMerchantId(), 1, 5);
    	assertEquals(1, response.getData().size());
    	for (Object o : response.getData()){
        	assertEquals(item1.getMerchant().getMerchantId(), ((ItemDTO)o).getMerchantId());
    	}
    }
    
    
    @Test
    public void testCorrectItemIdAssigned(){
    	ApiResponse response = itemResource.getItem(merchant.getMerchantId(), item1.getItemId());
		if (response.getData() == null) fail();
    }
    
    @Test
    public void testCountSuccess(){
    	assertEquals(1, itemService.count(merchant.getMerchantId()));
    }
    
    @Test
	public void testTimestampItemUpdate(){
		item1 = itemService.getItem(merchant.getMerchantId(), item1.getItemId());
		LocalDateTime before = item1.getUpdated();

		/* Passing modified ItemDTO , with itemId and merchantId excluded! */
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setName("newName!");
		itemDTO.setDescription(item1.getDescription());
		itemDTO.setPrice(item1.getPrice());
		itemResource.updateItem(item1.getMerchant().getMerchantId(), item1.getItemId(), itemDTO);
		
		item1 = itemService.getItem(merchant.getMerchantId(), item1.getItemId());
		LocalDateTime after = item1.getUpdated();
		assertTrue(before.isBefore(after));
	}
    
}
