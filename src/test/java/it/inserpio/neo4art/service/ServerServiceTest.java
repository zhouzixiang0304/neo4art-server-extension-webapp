package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.Server;
import it.inserpio.neo4art.model.ServerFunc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lsy on 2017/7/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
public class ServerServiceTest {
    @Autowired
    private ServerService serverService;

    @Test
    public void findAllConnectionTest(){
        serverService.findAllConnection();
    }

    @Test
    public void prepareNeoTest(){
        serverService.prepareNeo();
    }

    @Test
    public void saveNodesAndRelationsTest(){
        serverService.saveNodesAndRelations();
    }

    @Test
    public void getAllServerTest(){
        List<Server> allServerFunc = serverService.getAllServer();
    }

    @Test
    public void deleteAllServerTest(){
        serverService.deleteAllServer();
    }

    @Test
    public void graph(){

    }
}
