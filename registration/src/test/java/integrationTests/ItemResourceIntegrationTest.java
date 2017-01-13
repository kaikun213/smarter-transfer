package integrationTests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smarter_transfer.springrest.registration.WebApplication;
import com.smarter_transfer.springrest.registration.item.model.Item;
import com.smarter_transfer.springrest.registration.item.web.ItemDTO;
import com.smarter_transfer.springrest.registration.item.web.ItemResource;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;
import com.smarter_transfer.springrest.registration.merchant.model.Merchant;

import common.app.web.ApiResponse;
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

    Merchant merchant;
    Item item1;
	
    /* SETUP Tests */
    @Before
	public void setup(){
    	createMerchantPhysically();
    	item1 = new Item(merchant,"testItem", "testDescription", 10.0);
    	
    	ApiResponse response = itemResource.addItem(new ItemDTO(item1));
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
    	ApiResponse response = itemResource.addItem(new ItemDTO(item2));
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
    	item1.setName("updatedItem");
    	ApiResponse response = itemResource.updateItem(new ItemDTO(item1), item1.getMerchant().getMerchantId());
    	assertEquals("updatedItem", ((ItemDTO)response.getData()).getName());
    	
    	item1.setName("testItem");
    }
    
    
    @Test
    public void testCorrectItemIdAssigned(){
    	ApiResponse response = itemResource.getItem(merchant.getMerchantId(), item1.getItemId());
		if (response.getData() == null) fail();
    }
    
}
