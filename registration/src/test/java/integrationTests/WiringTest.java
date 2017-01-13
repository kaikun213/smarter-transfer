package integrationTests;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.smarter_transfer.springrest.registration.WebApplication;

import common.app.web.config.JsonConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplication.class, JsonConfiguration.class})
@ContextConfiguration("file:src/test/resources/registration-test.xml")
public class WiringTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void test() {
        assertNotNull(sessionFactory);
    }
}
