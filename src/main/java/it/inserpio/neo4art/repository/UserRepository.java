package it.inserpio.neo4art.repository;

import it.inserpio.neo4art.domain.Museum;
import it.inserpio.neo4art.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Created by lsy on 2017/6/23.
 */
public interface UserRepository extends GraphRepository<User> {

/*    @Query("MATCH (p:User)-[r:KNOWS]->(a:User) RETURN p,r,a")
    Collection<User> graph(@Param("limit") int limit);*/
}
