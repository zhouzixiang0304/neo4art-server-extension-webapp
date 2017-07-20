package it.inserpio.neo4art.repository;

import it.inserpio.neo4art.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

/**
 * Created by lsy on 2017/6/23.
 */
public interface UserRepository extends GraphRepository<User> {

/*    @Query("MATCH (p:User)-[r:KNOWS]->(a:User) RETURN p,r,a")
    Collection<User> graph(@Param("limit") int limit);*/

//    @Query("START user=node:User(userName={userName}) RETURN user")

    @Query("Match (fi:User)-[:KNOWS]->(u:User)-[:KNOWS]->(fo:User) where u.userName={0} return fo")
    Collection<User> searchFriends(String name);

    @Query("MATCH (user:User) WHERE user.userName={0} RETURN user")
    User getUserFromName(String name);

    @Query("MATCH (user:User)-[:KNOWS*2..2]->(fof:User) WHERE not((user)-[:KNOWS]->(fof)) AND user.userName={name} RETURN fof")
    Set<User> getFriendOfFriend(@Param("name") String name);
}
