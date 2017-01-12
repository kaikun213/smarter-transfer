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
import com.smarter_transfer.springrest.registration.merchant.model.Merchant;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantDTO;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantResource;

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
	
	Merchant tester1;
	Merchant tester2;
	
	@Before
	public void setup(){
		// initialize tester
		long keshId = merchantResource.countMerchants();
		tester1 = new Merchant(keshId+1);
		tester2 = new Merchant(keshId+2);

		ApiResponse response = merchantResource.addMerchant(new MerchantDTO(tester1));
		tester1.setMerchantId(((MerchantDTO)response.getData()).getMerchantId());
		response = merchantResource.addMerchant(new MerchantDTO(tester2));
		tester2.setMerchantId(((MerchantDTO)response.getData()).getMerchantId());
	}
		
	
	@Test
	public void testGetMerchantSuccess(){
		ApiResponse response = merchantResource.getMerchant(tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((MerchantDTO)response.getData()).getKeshId(), tester1.getKeshId());		
	}
	
	@Test
	public void testGetMerchantInvalidIdentifier(){
		ApiResponse response = merchantResource.getMerchant(-1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testGetMerchantNonExisting(){
		ApiResponse response = merchantResource.getMerchant(merchantResource.countMerchants()+1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testAddMerchantSuccess(){
		long keshId = merchantResource.countMerchants();
		Merchant tester3 = new Merchant(keshId+1);
		
		ApiResponse response = merchantResource.addMerchant(new MerchantDTO(tester3));
		tester3.setMerchantId(((MerchantDTO)response.getData()).getMerchantId());
		
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((MerchantDTO)response.getData()).getKeshId(), tester3.getKeshId());		
	}
	
	@Test
	public void testAddMerchantDuplicateKeshId(){
		ApiResponse response = merchantResource.addMerchant(new MerchantDTO(tester1));
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testUpdateMerchantSuccess(){
		tester1.setCompanyName("Tester1");
		ApiResponse response = merchantResource.updateMerchant(new MerchantDTO(tester1), tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((MerchantDTO)response.getData()).getCompanyName(), tester1.getCompanyName());	
	}
	
	@Test
	public void testUpdateMerchantDuplicateKeshId(){
		long keshId1 = tester1.getKeshId();
		tester1.setKeshId(tester2.getKeshId());
		
		ApiResponse response = merchantResource.updateMerchant(new MerchantDTO(tester1), tester1.getMerchantId());
		
		assertEquals(response.getStatus(), Status.ERROR);
		tester1.setKeshId(keshId1);
	}
	
	@Test
	public void testUpdateMerchantNonExisting(){
		ApiResponse response = merchantResource.updateMerchant(new MerchantDTO(tester1), merchantResource.countMerchants()+1);
		assertEquals(Status.ERROR,response.getStatus());
	}
	
	@Test
	public void testDeleteMerchant(){
		ApiResponse response = merchantResource.deleteMerchant(tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.OK);
		response = merchantResource.getMerchant(tester1.getMerchantId());
		assertEquals(response.getStatus(), Status.ERROR);		
	}
	
	@Test
	public void testDeleteMerchantInvalidMerchantId(){
		ApiResponse response = merchantResource.getMerchant(-1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testDeleteMerchantNonExisting(){
		ApiResponse response = merchantResource.getMerchant(merchantResource.countMerchants()+1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
    public void testCorrectMerchantIdAssigned(){
		ApiResponse response = merchantResource.getMerchant(tester1.getMerchantId());
		if (response.getData() == null) fail();
	}

	
	/*
	@Test
	public void testTimestampMerchantUpdate(){
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery("SELECT updated_at FROM smarterTestDB.Merchant WHERE merchantId="+tester1.getMerchantId());
		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List list = sqlQuery.list();
		String test = list.get(0).toString();
		System.err.println("**************************************************");

		System.out.println("Date: " + test);
		// before update
		sessionFactory.getCurrentSession().refresh(tester1);
		Date before = tester1.getUpdated();
		// after
		merchantResource.updateMerchant(new MerchantDTO(tester1), tester1.getMerchantId());
		sessionFactory.getCurrentSession().refresh(tester1);
		Date after = tester1.getUpdated();
		if (before == null || after == null) System.out.println("Error");
		assertTrue(before.before(after));
	}
	*/
	
	

}

