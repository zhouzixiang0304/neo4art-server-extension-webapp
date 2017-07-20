package it.inserpio.neo4art.util;

import it.inserpio.neo4art.model.Book;
import it.inserpio.neo4art.model.IdentifiableEntity;
import it.inserpio.neo4art.model.User;
import org.easymock.EasyMock;
import org.easymock.internal.MocksControl;
import org.junit.Test;
import org.springframework.data.neo4j.conversion.EndResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lsy on 2017/7/20.
 */
public class ToD3FormatTest {

    private ToD3Format<IdentifiableEntity> toD3Format = ToD3Format.getInstance();

    @Test
    public void toD3FormatTest(){
        List<User> list = new ArrayList<>();
        User u1 = new User("1", "lsy1");
        User u2 = new User("2", "lsy2");
        User u3 = new User("3", "lsy3");
        User u4 = new User("4", "lsy4");
        u1.knowsSomeone(u2);
        u1.knowsSomeone(u3);
        u2.knowsSomeone(u3);
        u3.knowsSomeone(u4);
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);
        Map<String, Object> map = toD3Format.toD3Format(list);
        //验证map大小
        assertEquals(map.size(),2);
        assertTrue(map.get("nodes") instanceof List && map.get("links") instanceof List);
        List<Map<String,Object>> nodes = (List<Map<String,Object>>)map.get("nodes");
        List<Map<String,Object>> links = (List<Map<String,Object>>)map.get("links");
        assertEquals(nodes.size(),4);
        assertEquals(links.size(),4);
    }

    @Test
    public void toD3FormatAgainTest(){
        Collection<IdentifiableEntity> collection = new ArrayList<>();
        User u1 = new User("1", "lsy1");
        User u2 = new User("2", "lsy2");
        User u3 = new User("3", "lsy3");
        User u4 = new User("4", "lsy4");
        u1.knowsSomeone(u2);
        u1.knowsSomeone(u3);
        u2.knowsSomeone(u3);
        u3.knowsSomeone(u4);
        Book b1 = new Book("1","book1");
        Book b2 = new Book("2","book2");
        collection.add(u1);
        collection.add(u2);
        collection.add(u3);
        collection.add(u4);
        collection.add(b1);
        collection.add(b2);
        Map<String, Object> map = toD3Format.toD3FormatAgain(collection);
        //验证map大小
        assertEquals(map.size(),2);
        assertTrue(map.get("nodes") instanceof List && map.get("links") instanceof List);
        List<Map<String,Object>> nodes = (List<Map<String,Object>>)map.get("nodes");
        List<Map<String,Object>> links = (List<Map<String,Object>>)map.get("links");
        assertEquals(nodes.size(),6);
        assertEquals(links.size(),4);

    }

    @Test
    public void getIndexTest() throws IllegalAccessException{
        Collection<IdentifiableEntity> collection = new ArrayList<>();
        User u1 = new User("1", "lsy1");
        User u2 = new User("2", "lsy2");
        User u3 = new User("3", "lsy3");
        User u4 = new User("4", "lsy4");
        u1.knowsSomeone(u2);
        u1.knowsSomeone(u3);
        u2.knowsSomeone(u3);
        u3.knowsSomeone(u4);
        Book b1 = new Book("1","book1");
        Book b2 = new Book("2","book2");
        collection.add(u1);
        collection.add(u2);
        collection.add(u3);
        collection.add(u4);
        collection.add(b1);
        collection.add(b2);
        Map<String, Object> map = toD3Format.toD3FormatAgain(collection);
        List<Map<String, Object>> nodes = (List<Map<String, Object>>) (map.get("nodes"));
        int i1 = toD3Format.getIndex(b1, nodes);
        int i2 = toD3Format.getIndex(u2, nodes);
        assertEquals(i1,0);
        assertEquals(i2,1);
    }
}
