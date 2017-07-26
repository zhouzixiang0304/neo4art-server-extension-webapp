package it.inserpio.neo4art.mybatis.dao;

import it.inserpio.neo4art.mybatis.entity.ServerConnection;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lsy on 2017/7/11.
 */
public interface SerConMapper {
    List<ServerConnection> findAll();

    List<ServerConnection> findByColumn(ServerConnection serverConnection);

    int saveServerConnection(ServerConnection serverConnection);
}
