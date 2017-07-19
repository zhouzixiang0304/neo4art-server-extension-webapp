package it.inserpio.neo4art.repository;

import it.inserpio.neo4art.model.Server;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by lsy on 2017/7/18.
 */
public interface ServerRepository extends GraphRepository<Server> {
}
