package unitTests;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.smarter_transfer.springrest.registration.merchant.model.Theme;
import com.smarter_transfer.springrest.registration.merchant.MerchantService;
import com.smarter_transfer.springrest.registration.merchant.model.ContactPerson;
import com.smarter_transfer.springrest.registration.merchant.model.Merchant;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantDTO;
import com.smarter_transfer.springrest.registration.merchant.web.MerchantResource;

import common.app.error.DuplicateRecordException;
import common.app.error.RecordNotFoundException;
import common.app.model.Address;
import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;
import common.app.web.ListApiResponse;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;


@RunWith(MockitoJUnitRunner.class)
public class MerchantResourceTest {
	
	@InjectMocks
	private MerchantResource merchantResource;
	@Mock
	private MerchantService merchantService;
	
	/* private help methods */
	private Merchant getMerchantDully(){
		Merchant merchant = new Merchant();
    	merchant.setKeshId(1);
		merchant.setCompanyName("tester");
		merchant.setPhoneNumber("");
		merchant.setEmail("");
		merchant.setContact(new ContactPerson("forename", "surname", "email"));
		merchant.setAddress(new Address("country", "state", "city", "zip","address1", "address2"));
		merchant.setUstId("ustID");
		merchant.setLogoId(1);
		merchant.addTheme(new Theme(1));
		merchant.setWebsiteURL("websiteURL");
		merchant.setShopURL("shopURL");
		merchant.setTicketURL("ticketURL");
	    merchant.getContact().setMerchant(merchant);
		return merchant;
	}
	private MerchantDTO getMerchantDTODully(){
		return new MerchantDTO(getMerchantDully());
	}
	
	/* Tests */
	@Test
	public void testGetMerchantSuccess(){
		// mock
		when(merchantService.getMerchant(1)).thenReturn(getMerchantDully());
        when(merchantService.getMerchant(2)).thenThrow(new RecordNotFoundException(""));
        // test
		ApiResponse response = merchantResource.getMerchant(1);
		// verify
        assertNotNull(response);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("tester", ((MerchantDTO) response.getData()).getCompanyName());
	}
	
	@Test
	public void testGetMerchantNoRecordFound(){
		// mock
		when(merchantService.getMerchant(1)).thenReturn(getMerchantDully());
        when(merchantService.getMerchant(2)).thenThrow(new RecordNotFoundException(""));
        // test
		ApiResponse response = merchantResource.getMerchant(2);
		// verify
		assertEquals(Status.ERROR, response.getStatus());
        assertEquals(400, response.getError().getErrorCode());
	}
	
	@Test
	public void testAddMerchantSuccess() throws Exception{
		// mock
		doNothing().when(merchantService).checkUniqueKeshId(anyLong(), anyLong());
		doNothing().when(merchantService).addMerchant(any(Merchant.class));
		//test
		ApiResponse response = merchantResource.addMerchant(getMerchantDTODully());
		// verify
		verify(merchantService, times(1)).addMerchant(any(Merchant.class));
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((MerchantDTO)response.getData()).getCompanyName(),getMerchantDTODully().getCompanyName());
	}
	
	@Test
	public void testAddMerchantException(){
		// mock
		doThrow(new DuplicateRecordException("")).when(merchantService).checkUniqueKeshId(anyLong(),anyLong());
		doNothing().when(merchantService).addMerchant(any(Merchant.class));
		// test
		ApiResponse response = merchantResource.addMerchant(getMerchantDTODully());
		// verify
		verify(merchantService, times(0)).addMerchant(any(Merchant.class));
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testUpdateMerchantSuccess(){
		// mock
		when(merchantService.getMerchant(1)).thenReturn(getMerchantDully());
		doNothing().when(merchantService).updateMerchant(any(Merchant.class));
        Merchant updatedMerchant = new Merchant();
        updatedMerchant.setCompanyName("updatedTester");
        // test
        ApiResponse response = merchantResource.updateMerchant(new MerchantDTO(updatedMerchant), 1);
        //verify
        assertEquals(Status.OK, response.getStatus());
        assertEquals("updatedTester",((MerchantDTO) response.getData()).getCompanyName());
		verify(merchantService, times(1)).getMerchant(anyLong());
		verify(merchantService, times(1)).updateMerchant(any(Merchant.class));
	}
	
	@Test
	public void testUpdateMerchantException(){
		// mock
		when(merchantService.getMerchant(2)).thenThrow(new RecordNotFoundException(""));
		// test
		ApiResponse response = merchantResource.updateMerchant(new MerchantDTO(getMerchantDully()), 2);
		// verify
        assertEquals(Status.ERROR, response.getStatus());
        assertEquals(400, response.getError().getErrorCode());
		verify(merchantService, times(1)).getMerchant(anyLong());
		verify(merchantService, times(0)).updateMerchant(any(Merchant.class));
	}
	
	@Test
	public void testDeleteMerchantSuccess(){
		// mock
		doNothing().when(merchantService).deleteMerchant(1);
		// test
		ApiResponse response = merchantResource.deleteMerchant(1);
		// verify
        assertEquals(Status.OK, response.getStatus());
        verify(merchantService, times(1)).deleteMerchant(1);
	}
	
	@Test
	public void testDeleteMerchantException(){
		// mock
		doThrow(new RecordNotFoundException("")).when(merchantService).deleteMerchant(2);
		// test
		ApiResponse response = merchantResource.deleteMerchant(2);
		// verify
        assertEquals(Status.ERROR, response.getStatus());
	}
	
	@Test
	public void testGetMerchantsSuccess(){
		// mock
		when(merchantService.getMerchants(anyInt(),anyInt())).thenReturn(new ArrayList<Merchant>());
		// test
		ListApiResponse response = merchantResource.getMerchants(1,1);
		// verify
        assertEquals(Status.OK, response.getStatus());
        verify(merchantService, times(1)).getMerchants(anyInt(), anyInt());
	}

}
