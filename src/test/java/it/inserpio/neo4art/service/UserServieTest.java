package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.Book;
import it.inserpio.neo4art.model.IdentifiableEntity;
import it.inserpio.neo4art.model.User;
import it.inserpio.neo4art.util.ToD3Format;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lsy on 2017/6/23.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
@Transactional
@EnableTransactionManagement
public class UserServieTest {
    @Autowired
    private UserService userService;
    ToD3Format<User> toD3Format = ToD3Format.getInstance();

    @Test
    public void testGetAllUsers() throws IllegalAccessException{
        List list = userService.getAllUsers();
        list.add(new Book("ef","Effective Java"));
//        assertEquals(6,list.size());
        toD3Format.toD3FormatAgain(list);
    }

    @Test
    public void testAddUser(){
        userService.saveUser(new User("1","234"));
    }

    @Test
    public void testDeleteUser(){
        userService.deleteUser(16809L);
    }

    @Test
    public void testSearchFriends(){
        Map<String, Object> map = userService.searchFriends("Sara");
        assertEquals(3,((List)map.get("nodes")).size());
    }

    @Test
    public void testSearchByName(){
        User joe = userService.searchByName("Joe");
        assertEquals(16802,joe.getGraphId().longValue());
    }

    @Test
    public void testFriendOfFriend(){
        Set<User> friendOfFriend = userService.getFriendOfFriend("Joe");
        assertEquals(3,friendOfFriend.size());
    }
}
