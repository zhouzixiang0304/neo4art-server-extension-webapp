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
    public void testFindAll(){
        serverService.findAllConnection();
    }

    @Test
    public void prepareNeo(){
        serverService.prepareNeo();
    }

    @Test
    public void saveNodesAndRelations(){
        serverService.saveNodesAndRelations();
    }

    @Test
    public void findAllServerFunc(){
        List<Server> allServerFunc = serverService.getAllServer();
    }

    @Test
    public void deleteAll(){
        serverService.deleteAllServer();
    }

    @Test
    public void testServerFuncUnique(){
        ServerFunc s1 = new ServerFunc("test1","test1");
        ServerFunc s2 = new ServerFunc("test1","test1");
        Set<ServerFunc> set = new HashSet<>();
        set.add(new ServerFunc("test2","test3"));
        s2.setTargets(set);
        Set<ServerFunc> nodeSet = new HashSet<>();
        nodeSet.add(s1);
        nodeSet.add(s2);
        System.out.println(nodeSet.size());

    }
}
