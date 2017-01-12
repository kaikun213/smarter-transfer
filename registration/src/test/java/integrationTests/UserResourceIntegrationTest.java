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
import com.smarter_transfer.springrest.registration.merchant.model.Theme;
import com.smarter_transfer.springrest.registration.user.model.User;
import com.smarter_transfer.springrest.registration.user.web.UserDTO;
import com.smarter_transfer.springrest.registration.user.web.UserResource;

import common.app.web.ApiResponse;
import common.app.web.ApiResponse.Status;
import common.app.web.config.JsonConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebApplication.class, JsonConfiguration.class}, locations = "registration-test.xml")
@WebAppConfiguration
@Transactional
public class UserResourceIntegrationTest {
		
	@Autowired
	UserResource userResource;
	
	User tester1;
	User tester2;
	
	@Before
	public void setup(){
		long id = userResource.countUsers();
		tester1 = new User(id+1);
		tester1.setTheme(new Theme(1));
		tester2 = new User(id+2);
		tester2.setTheme(new Theme(1));

		ApiResponse response = userResource.addUser(new UserDTO(tester1));
		tester1.setUserId(((UserDTO)response.getData()).getUserId());
		response = userResource.addUser(new UserDTO(tester2));
		tester2.setUserId(((UserDTO)response.getData()).getUserId());
	}
	
	
	@Test
	public void testGetUserSuccess(){
		/* successful case */
		ApiResponse response = userResource.getUser(tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getKeshId(), tester1.getKeshId());
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
		long keshId = userResource.countUsers();
		User tester3 = new User(keshId +1);
		tester3.setTheme(new Theme(1));
		ApiResponse response = userResource.addUser(new UserDTO(tester3));
		tester3.setUserId(((UserDTO)response.getData()).getUserId());
		
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getKeshId(), tester3.getKeshId());
	}
	
	@Test
	public void testAddUserDuplicateKeshId(){
		ApiResponse response = userResource.addUser(new UserDTO(tester1));
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testUpdateUserSuccess(){
		tester1.setName("Tester1");
		ApiResponse response = userResource.updateUser(new UserDTO(tester1), tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getName(), tester1.getName());		
	}
	
	@Test
	public void testUpdateUserDuplicateKeshId(){
		long keshId1 = tester1.getKeshId();
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
	
	/*
	@Test
	public void testTimestampUserUpdate(){
		// before update
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery("COMMIT;");
		sessionFactory.getCurrentSession().refresh(tester1);
		Date before = tester1.getUpdated();
		// after
		userResource.updateUser(new UserDTO(tester1), tester1.getUserId());
		sqlQuery = sessionFactory.getCurrentSession().createSQLQuery("COMMIT;");
		sessionFactory.getCurrentSession().refresh(tester1);
		Date after = ((User) sessionFactory.getCurrentSession().get(User.class, tester1.getUserId())).getUpdated();
		assertTrue(before.before(after));
	}
	*/

}
