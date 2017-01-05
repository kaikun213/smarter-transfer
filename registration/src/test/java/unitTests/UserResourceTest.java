package units;


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

import common.app.error.RecordNotFoundException;
import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {
	
	@InjectMocks
	private UserResource userResource;
	@Mock
	private UserService userService;
	
	@Test
	public void testGetUser() throws Exception{
		userService = new UserService(){

			public void addUser(User user) {}

			public void updateUser(User user) {}

			public void deleteUser(long keshId) {}

			public User getUser(long keshId) {
				if (keshId == 1){
					User user = new User();
		        	user.setKeshId(1);
		    		user.setName("tester");
		    		user.setDeviceId("testDeviceID");
		    		user.setLocation(1, 1);
		    		user.setTheme(new Theme(1));
		    		return user;
				}
				else throw new RecordNotFoundException("No User with id: "+ keshId);
			}

			public List<User> getUsers() {return null;}

			@Override
			public long count() {return 0;}

			@Override
			public void checkUniqueKeshId(long userId, long keshId) throws Exception {
				// TODO Auto-generated method stub
				
			}	
		};
		userResource = new UserResource(userService);
		ApiResponse response = userResource.getUser(1);
        assertEquals(Status.OK, response.getStatus());
        assertNotNull(response);
        UserDTO user = (UserDTO) response.getData();
        assertNotNull(user);
        assertEquals("tester", user.getName());
        // non-existent user
        response = userResource.getUser(2);
        assertEquals(Status.ERROR, response.getStatus());
        assertEquals(400, response.getError().getErrorCode());
	}
	
	@Test
	public void testUpdateUser(){
		User user = new User();
		user.setName("tester");
		user.setDeviceId("testDeviceID");
		user.setLocation(1, 1);
		user.setTheme(new Theme(1));
		when(userService.getUser(1)).thenReturn(user);
        when(userService.getUser(2)).thenThrow(new RecordNotFoundException(""));
        User updatedUser = new User();
        updatedUser.setName("updatedTester");
        updatedUser.setDeviceId("updatedDeviceID");
        updatedUser.setLocation(10.001, 10.001);
        updatedUser.setTheme(new Theme(1));
        ApiResponse response = userResource.updateUser(new UserDTO(updatedUser), 1);
        assertEquals(Status.OK, response.getStatus());
        assertEquals("updatedTester",((UserDTO) response.getData()).getName());
        assertEquals("updatedDeviceID",((UserDTO) response.getData()).getDeviceId());
        assertEquals(10,((UserDTO) response.getData()).getLat(), 0.001);
        // non-existent user
        response = userResource.updateUser(new UserDTO(user), 2);
        assertEquals(Status.ERROR, response.getStatus());
        assertEquals(400, response.getError().getErrorCode());
	}
	
	@Test
	public void testAddUser(){		
	}
	
	@Test
	public void testDeleteUser(){
		
	}

}
