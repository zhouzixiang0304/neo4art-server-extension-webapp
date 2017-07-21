package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.ServerFunc;
import it.inserpio.neo4art.mybatis.dao.SerConMapper;
import it.inserpio.neo4art.mybatis.entity.ServerConnection;
import it.inserpio.neo4art.repository.ServerFuncRepository;
import it.inserpio.neo4art.service.impl.ServerFuncServiceImpl;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by lsy on 2017/7/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
public class ServerFuncServiceTest {
    @Autowired
    private ServerFuncService serverFuncService;
    private ServerFuncRepository serverFuncRepositoryMock;
    private SerConMapper serConMapperMock;

    @Before
    public void setUp() throws Exception{
        serverFuncRepositoryMock = EasyMock.createMock(ServerFuncRepository.class);
        serConMapperMock = EasyMock.createMock(SerConMapper.class);
        Class<ServerFuncServiceImpl> clazz = ServerFuncServiceImpl.class;
        Field serverFuncRepository = clazz.getDeclaredField("serverFuncRepository");
        Field serConMapper = clazz.getDeclaredField("serConMapper");
        serverFuncRepository.setAccessible(true);
        serConMapper.setAccessible(true);
        serverFuncRepository.set(serverFuncService,serverFuncRepositoryMock);
        serConMapper.set(serverFuncService,serConMapperMock);
    }

    @Test
    public void findAllConnectionTest(){
        List<ServerConnection> expectedList = new ArrayList<>();
        EasyMock.expect(serConMapperMock.findAll()).andReturn(expectedList);
        EasyMock.replay(serConMapperMock);
        List<ServerConnection> list = serverFuncService.findAllConnection();
        assertEquals(expectedList,list);
        EasyMock.verify(serConMapperMock);
    }

    /**
     * 此方法为私有方法，但比较重要，所以需要单独测试
     */
    @Test
    public void prepareNeoTest() throws Exception{
        List<ServerConnection> expectedList = createExpectedList();
        EasyMock.expect(serConMapperMock.findAll()).andReturn(expectedList);
        EasyMock.replay(serConMapperMock);
        Class<ServerFuncServiceImpl> clazz = ServerFuncServiceImpl.class;
        Method prepareNeo = clazz.getDeclaredMethod("prepareNeo");
        prepareNeo.setAccessible(true);
        Set<ServerFunc> set = (Set<ServerFunc>)prepareNeo.invoke(serverFuncService);
        assertEquals(set.size(),6);
    }

    @Test
    @Rollback
    public void saveNodesAndRelationsTest() throws Exception{
//        List<ServerConnection> expectedList = createExpectedList();
//        EasyMock.expect(serConMapperMock.findAll()).andReturn(expectedList);
//        EasyMock.replay(serConMapperMock);
//        Class<ServerFuncServiceImpl> clazz = ServerFuncServiceImpl.class;
//        Method prepareNeo = clazz.getDeclaredMethod("prepareNeo");
//        prepareNeo.setAccessible(true);
//        Set<ServerFunc> set = (Set<ServerFunc>)prepareNeo.invoke(serverFuncService);
//        for (ServerFunc s :
//                set) {
//            EasyMock.expect(serverFuncRepositoryMock.save(s)).andReturn(s);
//        }
//        EasyMock.replay(serverFuncRepositoryMock);
//        serverFuncService.saveNodesAndRelations();
//        EasyMock.verify(serverFuncRepositoryMock);
    }

    @Test
    public void getAllServerFuncTest(){
        List<ServerFunc> allServerFunc = serverFuncService.getAllServerFunc();
    }

    @Test
    public void deleteAllServerTest(){
        serverFuncService.deleteAllServer();
    }

    @Test
    public void graphTest(){

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

    private List<ServerConnection> createExpectedList(){
        List<ServerConnection> expectedList = new ArrayList<>();
        ServerConnection sc1 = new ServerConnection("class1","func1","class2","func1","test1");
        ServerConnection sc2 = new ServerConnection("class1","func2","class2","func2","test2");
        ServerConnection sc3 = new ServerConnection("class2","func1","class3","func1","test3");
        ServerConnection sc4 = new ServerConnection("class3","func1","class1","func2","test4");
        ServerConnection sc5 = new ServerConnection("class2","func2","class3","func2","test5");
        expectedList.add(sc1);
        expectedList.add(sc2);
        expectedList.add(sc3);
        expectedList.add(sc4);
        expectedList.add(sc5);
        return expectedList;
    }
}
