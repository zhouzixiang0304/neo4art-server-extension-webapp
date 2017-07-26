package it.inserpio.neo4art.service.impl;

import it.inserpio.neo4art.model.ServerFunc;
import it.inserpio.neo4art.mybatis.dao.SerConMapper;
import it.inserpio.neo4art.mybatis.entity.ServerConnection;
import it.inserpio.neo4art.repository.ServerFuncRepository;
import it.inserpio.neo4art.service.ServerFuncService;
import it.inserpio.neo4art.util.ToD3Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lsy on 2017/7/11.
 */
@Service("serverFuncService")
public class ServerFuncServiceImpl implements ServerFuncService {
    @Autowired
    SerConMapper serConMapper;
    @Autowired
    ServerFuncRepository serverFuncRepository;
    private ToD3Format<ServerFunc> toD3Format = ToD3Format.getInstance();

    @Override
    public List<ServerConnection> findAllConnection() {
        return serConMapper.findAll();
    }

    /**
     * 将查找出来的所有连接，格式转换为
     * @return
     */
    private Set<ServerFunc> prepareNeo() {
        //第一步：将查找到的服务连接构建为Node
        List<ServerConnection> connectionList = findAllConnection();
        Map<ServerFunc,Set<ServerFunc>> serverFuncMap = new HashMap<>();
        for (ServerConnection serverConnection : connectionList) {
            ServerFunc serverFuncSrc = new ServerFunc(serverConnection.getSourceClass(), serverConnection.getSourceFunc());
            ServerFunc serverFuncTgt = new ServerFunc(serverConnection.getTargetClass(), serverConnection.getTargetFunc());
            Set<ServerFunc> keySet = serverFuncMap.keySet();
            for (ServerFunc serverFunc : keySet) {
                serverFuncSrc = serverFunc.equals(serverFuncSrc) ? serverFunc : serverFuncSrc;
                serverFuncTgt = serverFunc.equals(serverFuncTgt) ? serverFunc : serverFuncTgt;
            }
            //将target节点存到source的Set中
            if(serverFuncMap.containsKey(serverFuncSrc))//表示Map中已存在source节点
                serverFuncMap.get(serverFuncSrc).add(serverFuncTgt);
            else {//map中不存在source节点
                Set<ServerFunc> targets = new HashSet<>();
                targets.add(serverFuncTgt);
                serverFuncMap.put(serverFuncSrc,targets);
            }
            if(!serverFuncMap.containsKey(serverFuncTgt))
                serverFuncMap.put(serverFuncTgt,new HashSet<ServerFunc>());
        }
        Set<ServerFunc> serverFuncSet = serverFuncMap.keySet();
        for (ServerFunc serverFunc : serverFuncSet) {
            serverFunc.setTargets(serverFuncMap.get(serverFunc));
        }
        return serverFuncSet;
    }

    @Override
    public void saveNodesAndRelations() {
        Set<ServerFunc> serverFuncSet = prepareNeo();
        for (ServerFunc serverFunc : serverFuncSet) {
            serverFuncRepository.save(serverFunc);
        }
    }

    @Override
    public List<ServerFunc> getAllServerFunc() {
        EndResult<ServerFunc> all = serverFuncRepository.findAll();
        Iterator<ServerFunc> iterator = all.iterator();
        List<ServerFunc> resultList = new ArrayList<>();
        while(iterator.hasNext()){
            resultList.add(iterator.next());
        }
        return resultList;
    }

    @Override
    public void deleteAllServer() {
        serverFuncRepository.deleteAll();
    }

    @Override
    public Map<String, Object> graph(int limit) {
        return toD3Format.toD3FormatAgain(getAllServerFunc());
    }

    @Override
    public int saveServerConnection(ServerConnection serverConnection) {
        return serConMapper.saveServerConnection(serverConnection);
    }
}
