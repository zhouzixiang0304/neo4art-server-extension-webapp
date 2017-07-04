package it.inserpio.neo4art.repository;

import it.inserpio.neo4art.model.User;
import org.springframework.data.neo4j.repository.CRUDRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lsy on 2017/6/30.
 */
public interface PeopleRepository extends CrudRepository<User,Long> {
    User findByUserName(String userName);
}
