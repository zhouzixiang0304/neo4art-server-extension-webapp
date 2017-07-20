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

import java.util.*;

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
    ToD3Format<? extends IdentifiableEntity> toD3Format = ToD3Format.getInstance();

    @Test
    public void getAllUsersTest() throws IllegalAccessException{
        User lsy = new User("lsy", "lsy233");
//        List<? super IdentifiableEntity> list = userService.getAllUsers();
        List<? super IdentifiableEntity> list = new ArrayList<>();
        Book ef = new Book("ef", "Effective Java");
        list.add(ef);
//        assertEquals(6,list.size());
//        toD3Format.toD3FormatAgain(list);
        list.add(lsy);
        Iterator<? super IdentifiableEntity> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    public void saveUserTest(){
        userService.saveUser(new User("1","234"));
    }

    @Test
    public void deleteUserTest(){
        userService.deleteUser(16809L);
    }

    @Test
    public void setUpUsersTest(){

    }

    @Test
    public void purgeDatabaseTest(){

    }

    @Test
    public void graphTest(){

    }

    @Test
    public void searchFriendsTest(){
        Map<String, Object> map = userService.searchFriends("Sara");
        assertEquals(3,((List)map.get("nodes")).size());
    }

    @Test
    public void searchByNameTest(){
        User joe = userService.searchByName("Joe");
        assertEquals(16802,joe.getGraphId().longValue());
    }

    @Test
    public void getFriendOfFriendTest(){
        Set<User> friendOfFriend = userService.getFriendOfFriend("Joe");
        assertEquals(3,friendOfFriend.size());
    }
}
