package unitTests;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.smarter_transfer.springrest.registration.merchant.model.Theme;
import com.smarter_transfer.springrest.registration.user.UserService;
import com.smarter_transfer.springrest.registration.user.model.User;
import com.smarter_transfer.springrest.registration.user.web.UserDTO;
import com.smarter_transfer.springrest.registration.user.web.UserResource;

import common.app.error.DuplicateRecordException;
import common.app.error.RecordNotFoundException;
import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;
import common.app.web.ListApiResponse;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;


@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {
	
	@InjectMocks
	private UserResource userResource;
	@Mock
	private UserService userService;
	
	/* private help methods */
	public static User getUserDully(){
		User user = new User();
    	user.setKeshId(1);
		user.setName("tester");
		user.setDeviceId("testDeviceID");
		user.setLocation(1, 1);
		user.setTheme(new Theme(1));
		return user;
	}
	public static UserDTO getUserDTODully(){
		return new UserDTO(getUserDully());
	}
	
	/* Tests */
	@Test
	public void testGetUserSuccess(){
		// mock
		when(userService.getUser(1)).thenReturn(getUserDully());
        when(userService.getUser(2)).thenThrow(new RecordNotFoundException(""));
        // test
		ApiResponse response = userResource.getUser(1);
		// verify
        assertNotNull(response);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("tester", ((UserDTO) response.getData()).getName());
	}
	
	@Test
	public void testGetUserNoRecordFound(){
		// mock
		when(userService.getUser(1)).thenReturn(getUserDully());
        when(userService.getUser(2)).thenThrow(new RecordNotFoundException(""));
        // test
		ApiResponse response = userResource.getUser(2);
		// verify
		assertEquals(Status.ERROR, response.getStatus());
        assertEquals(400, response.getError().getErrorCode());
	}
	
	@Test
	public void testAddUserSuccess() throws Exception{
		// mock
		doNothing().when(userService).checkUniqueKeshId(anyLong(), anyLong());
		doNothing().when(userService).addUser(any(User.class));
		//test
		ApiResponse response = userResource.addUser(getUserDTODully());
		// verify
		verify(userService, times(1)).addUser(any(User.class));
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getName(),getUserDTODully().getName());
	}
	
	@Test
	public void testAddUserException(){
		// mock
		doThrow(new DuplicateRecordException("")).when(userService).checkUniqueKeshId(anyLong(),anyLong());
		doNothing().when(userService).addUser(any(User.class));
		// test
		ApiResponse response = userResource.addUser(getUserDTODully());
		// verify
		verify(userService, times(0)).addUser(any(User.class));
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testUpdateUserSuccess(){
		// mock
		when(userService.getUser(1)).thenReturn(getUserDully());
		doNothing().when(userService).updateUser(any(User.class));
        User updatedUser = new User();
        updatedUser.setName("updatedTester");
        // test
        ApiResponse response = userResource.updateUser(new UserDTO(updatedUser), 1);
        //verify
        assertEquals(Status.OK, response.getStatus());
        assertEquals("updatedTester",((UserDTO) response.getData()).getName());
		verify(userService, times(1)).getUser(anyLong());
		verify(userService, times(1)).updateUser(any(User.class));
	}
	
	@Test
	public void testUpdateUserException(){
		// mock
		when(userService.getUser(2)).thenThrow(new RecordNotFoundException(""));
		// test
		ApiResponse response = userResource.updateUser(new UserDTO(getUserDully()), 2);
		// verify
        assertEquals(Status.ERROR, response.getStatus());
        assertEquals(400, response.getError().getErrorCode());
		verify(userService, times(1)).getUser(anyLong());
		verify(userService, times(0)).updateUser(any(User.class));
	}
	
	@Test
	public void testDeleteUserSuccess(){
		// mock
		doNothing().when(userService).deleteUser(1);
		// test
		ApiResponse response = userResource.deleteUser(1);
		// verify
        assertEquals(Status.OK, response.getStatus());
        verify(userService, times(1)).deleteUser(1);
	}
	
	@Test
	public void testDeleteUserException(){
		// mock
		doThrow(new RecordNotFoundException("")).when(userService).deleteUser(2);
		// test
		ApiResponse response = userResource.deleteUser(2);
		// verify
        assertEquals(Status.ERROR, response.getStatus());
	}
	
	@Test
	public void testGetUsersSuccess(){
		// mock
		when(userService.getUsers()).thenReturn(new ArrayList<User>());
		// test
		ListApiResponse response = userResource.getUsers();
		// verify
        assertEquals(Status.OK, response.getStatus());
	}

}
