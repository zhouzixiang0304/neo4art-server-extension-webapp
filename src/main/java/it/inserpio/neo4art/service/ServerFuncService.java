package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.ServerFunc;
import it.inserpio.neo4art.mybatis.dao.SerConMapper;
import it.inserpio.neo4art.mybatis.entity.ServerConnection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lsy on 2017/7/11.
 */
@Deprecated
public interface ServerFuncService {
    /**
     * 查找到所有的链接
     * @return
     */
    List<ServerConnection> findAllConnection();

    /**
     * 将查找到的服务连接构建为Node
     * @return
     */
    Collection<ServerFunc> prepareNeo();

    /**
     * 保存节点和关系
     */
    void saveNodesAndRelations();

    /**
     * 得到所有服务方法
     * @return
     */
    List<ServerFunc> getAllServerFunc();

    /**
     * 删除所有服务
     */
    void deleteAllServer();

    /**
     * 查找用户并以图形式返回
     * @param limit
     * @return
     */
    Map<String,Object> graph(int limit);

}
