package integrationTests;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:registration-test.xml")
public class WiringTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void test() {
        assertNotNull(sessionFactory);
    }
}
