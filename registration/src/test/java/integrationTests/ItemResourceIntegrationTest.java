package integrationTests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.smarter_transfer.springrest.registration.WebApplication;
import com.smarter_transfer.springrest.registration.item.model.Item;
import com.smarter_transfer.springrest.registration.item.model.ItemPK;
import com.smarter_transfer.springrest.registration.item.web.ItemDTO;
import com.smarter_transfer.springrest.registration.item.web.ItemResource;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;
import com.smarter_transfer.springrest.registration.merchant.model.Merchant;

import common.app.web.ApiResponse;
import common.app.web.config.JsonConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebApplication.class, JsonConfiguration.class}, locations = "registration-test.xml")
@WebAppConfiguration
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
    	item1 = new Item(new ItemPK(merchant),"testItem", "testDescription", 10.0);
    	
    	ApiResponse response = itemResource.addItem(new ItemDTO(item1));
    	item1.getItemPK().setItemId(((ItemDTO)response.getData()).getItemId());
    }
    
    private void createMerchantPhysically(){
		merchant = new Merchant();
		merchant.setKeshId(1);
		merchantService.addMerchant(merchant);
	}
   	
    
    @Test
    public void testAddItem(){
    	// setup
    	Item item2 = new Item(new ItemPK(merchant), "testItem2", "testDescription2", 20.0);
    	
    	// test
    	ApiResponse response = itemResource.addItem(new ItemDTO(item2));
    	item2.getItemPK().setItemId(((ItemDTO)response.getData()).getItemId());
    	assertEquals("testItem2", ((ItemDTO)response.getData()).getName());
    }
    
    /*
    @Test
    public void testCorrectItemIdAssigned(){
    	ApiResponse response = itemResource.getItem(merchant.getMerchantId(), item1.getItemPK().getItemId());
		if (response.getData() == null) fail();
    }
    */
}
