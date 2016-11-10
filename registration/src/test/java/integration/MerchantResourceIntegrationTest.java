package integration;

import static org.junit.Assert.assertEquals;

import org.hibernate.SessionFactory;
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
import com.smarter_transfer.springrest.registration.merchant.model.Merchant;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantDTO;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantResource;

import common.app.error.RecordNotFoundException;
import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;
import common.app.web.config.JsonConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebApplication.class, JsonConfiguration.class}, locations = "registration-test.xml")
@WebAppConfiguration
@Transactional
public class MerchantResourceIntegrationTest {
		
	@Autowired
	MerchantResource merchantResource;
	
	/* for teardown only */
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantResourceIntegrationTest.class);
    @Autowired
    private SessionFactory sessionFactory;

	
	Merchant tester1;
	Merchant tester2;
	
	@Before
	public void setup(){
		// initialize tester
		long id = merchantResource.countMerchants();
		tester1 = new Merchant();
		tester1.setKeshId(id + 100);
		tester1.setCompanyName("tester1");
		tester2 = new Merchant();
		tester2.setKeshId(id + 201);
		tester2.setCompanyName("tester2");

		ApiResponse response = merchantResource.addMerchant(new MerchantDTO(tester1));
		tester1.setMerchantId(((MerchantDTO)response.getData()).getMerchantId());
		response = merchantResource.addMerchant(new MerchantDTO(tester2));
		tester2.setMerchantId(((MerchantDTO)response.getData()).getMerchantId());
	}
	
	@After
	public void teardown(){
		deleteMerchant(tester1.getMerchantId());
		deleteMerchant(tester2.getMerchantId());
	}
	
	/* private delete method for test purpose - no soft delete => cascade */
	private void deleteMerchant(long merchantId) {
		  if (merchantId <= 0) {
			  throw new IllegalArgumentException("The merchantId must be greater than zero");
		  }
	      Merchant merchant = (Merchant) sessionFactory.getCurrentSession().get(Merchant.class, merchantId);
	      if (merchant == null){ // || merchant.isDeleted()) { does not matter if it is set deleted for test-data
	    	  throw new RecordNotFoundException("No merchant with Merchant-ID " + merchantId);
	      }
	      sessionFactory.getCurrentSession().delete(merchant);
	      if (LOGGER.isInfoEnabled()) {
	         LOGGER.info("Teardown deleted merchant: {}", merchant.getCompanyName());
	      }
	}
	
	
	@Test
	public void testGetMerchant(){
		/* successful case */
		ApiResponse response = merchantResource.getMerchant(tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((MerchantDTO)response.getData()).getKeshId(), tester1.getKeshId());
		/* invalid integer */
		response = merchantResource.getMerchant(-1);
		assertEquals(response.getStatus(), Status.ERROR);
		/* not existing merchant */
		response = merchantResource.getMerchant(merchantResource.countMerchants()+3);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testAddMerchant(){
		/* successful case */
		/* create merchant */
		Merchant tester3 = new Merchant();
		long id = merchantResource.countMerchants();
		tester3.setKeshId(id + 301);
		tester3.setCompanyName("tester3");
		ApiResponse response = merchantResource.addMerchant(new MerchantDTO(tester3));
		tester3.setMerchantId(((MerchantDTO)response.getData()).getMerchantId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((MerchantDTO)response.getData()).getKeshId(), tester3.getKeshId());
		/* teardown - delete merchant again */
		deleteMerchant(tester3.getMerchantId());

		/* merchant already exists error (keshId) */
		response = merchantResource.addMerchant(new MerchantDTO(tester1));
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testUpdateMerchant(){
		/* successful case */
		tester1.setCompanyName("Tester1");
		tester1.setKeshId(tester1.getKeshId()+1);
		ApiResponse response = merchantResource.updateMerchant(new MerchantDTO(tester1), tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((MerchantDTO)response.getData()).getKeshId(), tester1.getKeshId());
		assertEquals(((MerchantDTO)response.getData()).getCompanyName(), tester1.getCompanyName());

		
		/* keshId already exists error */
		long keshId1 = tester1.getKeshId();
		tester1.setKeshId(tester2.getKeshId());
		response = merchantResource.updateMerchant(new MerchantDTO(tester1), tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.ERROR);
		tester1.setKeshId(keshId1);
				
		/* Merchant does not exist */
		response = merchantResource.updateMerchant(new MerchantDTO(tester1), merchantResource.countMerchants()+3);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testDeleteMerchant(){
		ApiResponse response = merchantResource.deleteMerchant(tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.OK);
		response = merchantResource.getMerchant(tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.ERROR);

		/* invalid integer */
		response = merchantResource.getMerchant(-1);
		assertEquals(response.getStatus(), Status.ERROR);
		/* not existing merchant */
		response = merchantResource.getMerchant(merchantResource.getMerchants().getTotal()+1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	
	

}

