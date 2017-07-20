package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.User;
import it.inserpio.neo4art.repository.PeopleRepository;
import it.inserpio.neo4art.service.impl.PeopleServiceImpl;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lsy on 2017/6/30.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
@Transactional
@EnableTransactionManagement
public class PeopleServiceTest {
    @Autowired
    private PeopleService peopleService;
    private PeopleRepository mock;

    @Before
    public void setUp() throws Exception{
        mock = EasyMock.createMock(PeopleRepository.class);
        Class<PeopleServiceImpl> clazz = PeopleServiceImpl.class;
        Field peopleRepository = clazz.getDeclaredField("peopleRepository");
        peopleRepository.setAccessible(true);
        peopleRepository.set(peopleService,mock);
    }

    @Test
    public void findByUserNameTest() throws Exception{
        User expectedUser = new User();
        EasyMock.expect(mock.findByUserName("lsy1")).andReturn(expectedUser);
        EasyMock.replay(mock);
        User joe = peopleService.findByUserName("lsy1");
        assertEquals(expectedUser,joe);
        EasyMock.verify(mock);
    }
}
