package integrationTests;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import common.app.error.RecordNotFoundException;
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
	
	/* for teardown only */
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemResourceIntegrationTest.class);
    @Autowired
    private SessionFactory sessionFactory;
    
    Merchant merchant;
    Item item1;
    
    /* Physically deletion of merchant for test purpose - no soft delete => cascade */
	private void deleteMerchantPhysically(long merchantId) {
		  if (merchantId <= 0) {
			  throw new IllegalArgumentException("The merchantId must be greater than zero");
		  }
	      Merchant merchant = (Merchant) sessionFactory.getCurrentSession().get(Merchant.class, merchantId);
	      if (merchant == null){ // || merchant.isDeleted()) { does not matter if it is set deleted for test-data
	    	  throw new RecordNotFoundException("No merchant with Merchant-ID " + merchantId);
	      }
	      sessionFactory.getCurrentSession().delete(merchant);
	      if (LOGGER.isInfoEnabled()) {
	         LOGGER.info("Teardown deleted merchant: {}", merchant.toString());
	      }
	}
	private void createMerchantPhysically(){
		merchant = new Merchant();
		merchant.setCompanyName("testMerchant");
		merchant.setKeshId(1);
		merchantService.addMerchant(merchant);
	}
	
    /* SETUP Tests */
    @Before
	public void setup(){
    	createMerchantPhysically();
    	ItemPK itemPK1 = new ItemPK();
    	itemPK1.setMerchant(merchant);
    	item1 = new Item();
    	item1.setName("testItem");
    	item1.setDescription("testDescription");
    	item1.setPrice(10.0);
    	item1.setItemPK(itemPK1);
    	
    	ApiResponse response = itemResource.addItem(new ItemDTO(item1));
    	item1.getItemPK().setItemId(((ItemDTO)response.getData()).getItemId());
    }

    @After
	public void teardown(){
    	System.out.println(item1.toString());
    	deleteItemPhysically(item1.getItemPK());
    	//deleteMerchantPhysically(1);
    }
    
    /* Physically deletion of items for test purpose - no soft delete => cascade */
   	private void deleteItemPhysically(ItemPK itemPK) {
   		  if (itemPK.getItemId() <= 0) {
   			  throw new IllegalArgumentException("The itemId must be greater than zero");
   		  }
   		  Item item = (Item) sessionFactory.getCurrentSession().createCriteria(Item.class).add(Restrictions.idEq(itemPK)).uniqueResult();
   	      if (item == null){ // || merchant.isDeleted()) { does not matter if it is set deleted for test-data
   	    	  System.out.println("Items:");
   	   		  List<Item> items = (List<Item>) sessionFactory.getCurrentSession().createCriteria(Item.class).add(Restrictions.eq("itemPK.merchant.merchantId", itemPK.getMerchant().getMerchantId())).list();
   	    	  for (Item i : items){
   	    		  System.out.println("ListItem: " + i.toString());
   	    	  }
   	   		  throw new RecordNotFoundException("No item with Item-ID " + itemPK.toString());
   	      }
   	      sessionFactory.getCurrentSession().delete(item);
   	      if (LOGGER.isInfoEnabled()) {
   	         LOGGER.info("Teardown deleted item: {}", itemPK.toString());
   	      }
   	}
   	
    
    @Test
    public void testAddItem(){
    	// setup
    	ItemPK itemPK3 = new ItemPK();
    	itemPK3.setMerchant(merchant);
    	Item item3 = new Item();
    	item3.setName("testItem3");
    	item3.setDescription("testDescription");
    	item3.setPrice(10.0);
    	item3.setItemPK(itemPK3);
    	// test
    	ApiResponse response = itemResource.addItem(new ItemDTO(item3));
    	item3.getItemPK().setItemId(((ItemDTO)response.getData()).getItemId());
    	assertEquals("testItem3", ((ItemDTO)response.getData()).getName());
    	// teardown
    	System.out.println(item3.toString());
    	deleteItemPhysically(item3.getItemPK());
    }
}
