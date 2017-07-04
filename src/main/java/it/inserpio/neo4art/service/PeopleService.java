package it.inserpio.neo4art.service;

import it.inserpio.neo4art.model.User;

/**
 * Created by lsy on 2017/6/30.
 */
public interface PeopleService {
    User findByUserName(String userName);
}
