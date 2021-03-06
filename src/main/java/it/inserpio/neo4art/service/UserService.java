package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lsy on 2017/6/23.
 */
public interface UserService {
    /**
     * 得到所有用户
     * @return
     */
    List<User> getAllUsers();

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 根据用户id删除用户
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 添加用户数据
     */
    void setUpUsers();

    /**
     * 删除数据库中的所有用户
     */
    void purgeDatabase();

    /**
     * 查找用户并以图形式返回
     * @param limit
     * @return
     */
    Map<String,Object> graph(int limit);

    /**
     * 查找某人的所有朋友
     * @param name
     * @return
     */
    Map<String,Object> searchFriends(String name);

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    User searchByName(String name);

    Set<User> getFriendOfFriend(String name);
}
