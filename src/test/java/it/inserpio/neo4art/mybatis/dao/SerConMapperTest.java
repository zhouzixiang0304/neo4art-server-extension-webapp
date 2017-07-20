package it.inserpio.neo4art.mybatis.dao;

import it.inserpio.neo4art.mybatis.entity.ServerConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lsy on 2017/7/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/application-context.xml")
public class SerConMapperTest {
    @Autowired
    private SerConMapper serConMapper;

    @Test
    public void findAllTest(){
        List<ServerConnection> list = serConMapper.findAll();
        for (ServerConnection serverConn :
                list) {
            System.out.println(serverConn);
        }
    }
    @Test
    public void findByColumnTest(){
        List<ServerConnection> list = serConMapper.findByColumn(new ServerConnection("class1"));
        for (ServerConnection serverConn :
                list) {
            System.out.println(serverConn);
        }
    }
}
