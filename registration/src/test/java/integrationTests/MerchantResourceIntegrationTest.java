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
import com.smarter_transfer.springrest.registration.merchant.MerchantService;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantDTO;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantResource;

import common.app.model.merchant.Merchant;
import common.app.web.ApiResponse;
import common.app.web.ListApiResponse;
import common.app.web.ApiResponse.Status;
import common.app.web.config.JsonConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class, JsonConfiguration.class})
@ContextConfiguration("file:src/test/resources/registration-test.xml")
@Transactional
public class MerchantResourceIntegrationTest {
		
	@Autowired
	MerchantResource merchantResource;
	/* for internal tests e.g. timestamp update */
	@Autowired
	MerchantService merchantService;
	
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
    public void testGetMerchantsSuccess(){
    	ListApiResponse response = merchantResource.getMerchants(1, 5);
    	assertEquals(2, response.getData().size());
    	for (Object o : response.getData()){
        	if (!(o instanceof MerchantDTO)) fail("No Merchants returned");
    	}
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
	public void testDeleteMerchantSuccess(){
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

	
	@Test
	public void testTimestampMerchantUpdate(){
		tester1 = merchantService.getMerchant(tester1.getMerchantId());
		LocalDateTime before = tester1.getUpdated();
		
		tester1.setCompanyName("updatedName");
		merchantResource.updateMerchant(new MerchantDTO(tester1), tester1.getMerchantId());
		
		tester1 = merchantService.getMerchant(tester1.getMerchantId());
		LocalDateTime after = tester1.getUpdated();
		assertTrue(before.isBefore(after));
	}
	
	

}

