package integrationTests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.SQLQuery;
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
import com.smarter_transfer.springrest.registration.merchant.model.Theme;
import com.smarter_transfer.springrest.registration.user.model.User;
import com.smarter_transfer.springrest.registration.user.web.UserDTO;
import com.smarter_transfer.springrest.registration.user.web.UserResource;

import common.app.error.RecordNotFoundException;
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
	
	/* for teardown only */
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantResourceIntegrationTest.class);
    @Autowired
    private SessionFactory sessionFactory;
	
	User tester1;
	User tester2;
	
	@Before
	public void setup(){
		// initialize tester
		long id = userResource.countUsers();
		tester1 = new User();
		tester1.setKeshId(id + 101);
		tester1.setTheme(new Theme(1));
		tester1.setName("tester1");
		tester2 = new User();
		tester2.setKeshId(id + 201);
		tester2.setTheme(new Theme(1));
		tester2.setName("tester2");

		ApiResponse response = userResource.addUser(new UserDTO(tester1));
		tester1.setUserId(((UserDTO)response.getData()).getUserId());
		sessionFactory.getCurrentSession().refresh(tester1);
		response = userResource.addUser(new UserDTO(tester2));
		tester2.setUserId(((UserDTO)response.getData()).getUserId());
	}
	
	@After
	public void teardown(){
		deleteUser(tester1.getUserId());
		deleteUser(tester2.getUserId());
	}
	
	/* private delete method for test purpose - no soft delete => cascade */
	private void deleteUser(long userId) {
		  if (userId <= 0) {
			  throw new IllegalArgumentException("The userId must be greater than zero");
		  }
	      User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
	      if (user == null){ // || user.isDeleted()) { does not matter if it is set deleted for test-data
	    	  throw new RecordNotFoundException("No user with User-ID " + userId);
	      }
	      sessionFactory.getCurrentSession().delete(user);
	      if (LOGGER.isInfoEnabled()) {
	         LOGGER.info("Teardown deleted user: {}", user.toString());
	      }
	}
	
	@Test
	public void testGetUser(){
		/* successful case */
		ApiResponse response = userResource.getUser(tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getKeshId(), tester1.getKeshId());
		/* invalid integer */
		response = userResource.getUser(-1);
		assertEquals(response.getStatus(), Status.ERROR);
		/* not existing user */
		response = userResource.getUser(userResource.countUsers()+3);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testAddUser(){
		/* successful case */
		/* create user */
		User tester3 = new User();
		long id = userResource.countUsers();
		tester3.setKeshId(id + 301);
		tester3.setTheme(new Theme(1));
		tester3.setName("tester3");
		ApiResponse response = userResource.addUser(new UserDTO(tester3));
		tester3.setUserId(((UserDTO)response.getData()).getUserId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getKeshId(), tester3.getKeshId());
		/* teardown - delete user again */
		deleteUser(tester3.getUserId());

		/* user already exists error (keshId) */
		response = userResource.addUser(new UserDTO(tester1));
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testUpdateUser(){
		/* successful case */
		tester1.setName("Tester1");
		tester1.setKeshId(tester1.getKeshId()+1);
		ApiResponse response = userResource.updateUser(new UserDTO(tester1), tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		assertEquals(((UserDTO)response.getData()).getKeshId(), tester1.getKeshId());
		assertEquals(((UserDTO)response.getData()).getName(), tester1.getName());

		
		/* keshId already exists error */		
		long keshId1 = tester1.getKeshId();
		tester1.setKeshId(tester2.getKeshId());
		response = userResource.updateUser(new UserDTO(tester1), tester1.getUserId());
		assertEquals(response.getStatus(), Status.ERROR);
		tester1.setKeshId(keshId1);
				
		/* User does not exist */
		response = userResource.updateUser(new UserDTO(tester1), userResource.countUsers()+3);
		assertEquals(response.getStatus(), Status.ERROR);
	}
	
	@Test
	public void testDeleteUser(){
		ApiResponse response = userResource.deleteUser(tester1.getUserId());
		assertEquals(response.getStatus(), Status.OK);
		response = userResource.getUser(tester1.getUserId());
		assertEquals(response.getStatus(), Status.ERROR);

		/* invalid integer */
		response = userResource.getUser(-1);
		assertEquals(response.getStatus(), Status.ERROR);
		/* not existing user */
		response = userResource.getUser(userResource.countUsers()+1);
		assertEquals(response.getStatus(), Status.ERROR);
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
