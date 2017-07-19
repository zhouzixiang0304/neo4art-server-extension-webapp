package it.inserpio.neo4art.service.impl;

import it.inserpio.neo4art.model.Func;
import it.inserpio.neo4art.model.Server;
import it.inserpio.neo4art.model.ServerFunc;
import it.inserpio.neo4art.mybatis.dao.SerConMapper;
import it.inserpio.neo4art.mybatis.entity.ServerConnection;
import it.inserpio.neo4art.repository.ServerRepository;
import it.inserpio.neo4art.service.ServerService;
import it.inserpio.neo4art.util.ToD3Format;
import org.neo4j.cypher.internal.compiler.v2_0.functions.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lsy on 2017/7/18.
 */
@Service("serverService")
public class ServerServiceImpl implements ServerService {
    @Autowired
    SerConMapper serConMapper;
    @Autowired
    ServerRepository serverRepository;
    private ToD3Format<Server> toD3Format = ToD3Format.getInstance();

    @Override
    public List<ServerConnection> findAllConnection() {
        return serConMapper.findAll();
    }

    @Override
    public Collection<Server> prepareNeo() {
        //第一步：将查找到的服务连接构建为Node
        List<ServerConnection> connectionList = findAllConnection();
        Map<Server,Set<Server>> serverMap = new HashMap<>();
        for (ServerConnection serverConnection : connectionList) {
            //保存每行信息
            Server serverSrc = new Server(serverConnection.getSourceClass());
            Server serverTgt = new Server(serverConnection.getTargetClass());
            Set<Server> keySet = serverMap.keySet();
            for (Server server : keySet) {
                serverSrc = server.equals(serverSrc) ? server : serverSrc;
                serverTgt = server.equals(serverTgt) ? server : serverTgt;
            }
            String funcSrcName = serverConnection.getSourceClass()+ ":" + serverConnection.getSourceFunc();
            String funcTgtName = serverConnection.getTargetClass()+ ":" + serverConnection.getTargetFunc();
            serverSrc.getFuncs().add(new Func(funcSrcName));
            serverTgt.getFuncs().add(new Func(funcTgtName));
            serverSrc.getConnections().add(serverConnection.getDescription() + " : "
                    + funcSrcName
                    + "->"
                    + funcTgtName
            );
            //将target节点存到source的Set中
            if(serverMap.containsKey(serverSrc))//表示Map中已存在source节点
                serverMap.get(serverSrc).add(serverTgt);
            else {//map中不存在source节点
                Set<Server> targets = new HashSet<>();
                targets.add(serverTgt);
                serverMap.put(serverSrc,targets);
            }
            if(!serverMap.containsKey(serverTgt))
                serverMap.put(serverTgt,new HashSet<Server>());
        }
        Set<Server> serverSet = serverMap.keySet();
        for (Server server : serverSet) {
            server.setTargets(serverMap.get(server));
        }
        return serverSet;
    }

    @Override
    public void saveNodesAndRelations() {
        Collection<Server> serverSet = prepareNeo();
        for (Server server : serverSet) {
            serverRepository.save(server);
        }
    }

    @Override
    public List<Server> getAllServer() {
        EndResult<Server> all = serverRepository.findAll();
        Iterator<Server> iterator = all.iterator();
        List<Server> resultList = new ArrayList<>();
        while(iterator.hasNext()){
            resultList.add(iterator.next());
        }
        return resultList;
    }

    @Override
    public void deleteAllServer() {
        serverRepository.deleteAll();
    }

    @Override
    public Map<String, Object> graph(int limit) {
        return toD3Format.toD3FormatAgain(getAllServer());
    }
}
