package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lsy on 2017/6/23.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
@Transactional
@EnableTransactionManagement
public class MyMuseumServieTest {
    @Autowired
    private MuseumService museumService;

    @Test
    public void testGetAllUsers(){
        museumService.getAllUsers();
    }

    @Test
    public void testAddUser(){
        museumService.saveUser(new User("1","234"));
    }

    @Test
    public void testDeleteUser(){
        museumService.deleteUser(16802L);
    }
}
