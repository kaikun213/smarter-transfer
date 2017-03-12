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
import com.smarter_transfer.springrest.registration.merchant.model.Theme;
import com.smarter_transfer.springrest.registration.user.UserService;
import com.smarter_transfer.springrest.registration.user.model.User;
import com.smarter_transfer.springrest.registration.user.web.UserDTO;
import com.smarter_transfer.springrest.registration.user.web.UserResource;

import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;
import common.app.web.config.JsonConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class, JsonConfiguration.class})
@ContextConfiguration("file:src/test/resources/registration-test.xml")
@Transactional
public class UserResourceIntegrationTest {
		
	@Autowired
	UserResource userResource;
	/* for internal tests e.g. timestamp update */
	@Autowired
	UserService userService;
	
	User tester1;
	User tester2;
	
	@Before
	public void setup(){
		String id = Long.toString(userResource.countUsers());
		tester1.setTheme(new Theme(1));
		tester1.setKeshId(id+"@Tester1");
		tester2.setTheme(new Theme(1));
		tester2.setKeshId(id+"@Tester2");

		ApiResponse response = userResource.addUser(new UserDTO(tester1), id+"@Tester1", "EncPass@Tester1");
		tester1.setUserId(((UserDTO)response.getData()).getUserId());
		response = userResource.addUser(new UserDTO(tester2), id+"@Tester2", "EncPass@Tester2");
		tester2.setUserId(((UserDTO)response.getData()).getUserId());
	}
	
	
	@Test
	public void testGetUserSuccess(){
		ApiResponse response = userResource.getUser(tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getThemeId(), tester1.getTheme().getThemeId());
	}
	
	@Test
	public void testGetUserInvalidUserId(){
		ApiResponse response = userResource.getUser(-1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testGetUserNonExisting(){
		ApiResponse response = userResource.getUser(userResource.countUsers()+3);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testAddUserSuccess(){
		String id = Long.toString(userResource.countUsers());
		User tester3 = new User();
		tester3.setTheme(new Theme(1));
		ApiResponse response = userResource.addUser(new UserDTO(tester3), id+"@Tester3", "EncPass@Tester3");
		tester3.setUserId(((UserDTO)response.getData()).getUserId());
		
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getThemeId(), tester3.getTheme().getThemeId());
	}
	
	@Test
	public void testAddUserDuplicateKeshId(){
		ApiResponse response = userResource.addUser(new UserDTO(tester1), tester1.getKeshId(), "EncPass@Tester1");
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testUpdateUserSuccess(){
		tester1.setName("Tester1");
		ApiResponse response = userResource.updateUser(new UserDTO(tester1), tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals("Tester1",((UserDTO)response.getData()).getName());		
	}
	
	@Test
	public void testUpdateUserDuplicateKeshId(){
		String keshId1 = tester1.getKeshId();
		tester1.setKeshId(tester2.getKeshId());
		ApiResponse response = userResource.updateUser(new UserDTO(tester1), tester1.getUserId());
		assertEquals(response.getStatus(), Status.ERROR);
		tester1.setKeshId(keshId1);
	}
	
	@Test
	public void testUpdateUserNonExisting(){
		ApiResponse response = userResource.updateUser(new UserDTO(tester1), userResource.countUsers()+3);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testDeleteUserSuccess(){
		ApiResponse response = userResource.deleteUser(tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		response = userResource.getUser(tester1.getUserId());
		assertEquals(response.getStatus(), Status.ERROR);		
	}
	
	@Test
	public void testDeleteUserInvalidUserId(){
		ApiResponse response = userResource.getUser(-1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testDeleteUserNonExisting(){
		ApiResponse response = userResource.getUser(userResource.countUsers()+1);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
    public void testCorrectUserIdAssigned(){
		ApiResponse response = userResource.getUser(tester1.getUserId());
		if (response.getData() == null) fail();
	}
	
	
	@Test
	public void testTimestampUserUpdate(){
		/* needs to retrieve tester newly because only userDTO got passed => tester1 object never updated (pass by value) */
		tester1 = userService.getUser(tester1.getUserId());
		LocalDateTime before = tester1.getUpdated();
		
		tester1.setName("updatedName");
		userResource.updateUser(new UserDTO(tester1), tester1.getUserId());
		
		tester1 = userService.getUser(tester1.getUserId());
		LocalDateTime after = tester1.getUpdated();
		assertTrue(before.isBefore(after));
	}
	

}
