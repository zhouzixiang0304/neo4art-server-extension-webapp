package it.inserpio.neo4art.repository;

import it.inserpio.neo4art.model.ServerFunc;
import it.inserpio.neo4art.mybatis.entity.ServerConnection;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by lsy on 2017/7/11.
 */
public interface ServerFuncRepository extends GraphRepository<ServerFunc> {
}
