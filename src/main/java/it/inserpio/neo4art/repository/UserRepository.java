package it.inserpio.neo4art.repository;

import it.inserpio.neo4art.domain.Museum;
import it.inserpio.neo4art.model.User;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by lsy on 2017/6/23.
 */
public interface UserRepository extends GraphRepository<User> {

}
