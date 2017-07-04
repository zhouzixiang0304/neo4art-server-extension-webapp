package it.inserpio.neo4art.service.impl;

import it.inserpio.neo4art.model.User;
import it.inserpio.neo4art.repository.PeopleRepository;
import it.inserpio.neo4art.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lsy on 2017/6/30.
 */
@Service("peopleService")
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    PeopleRepository peopleRepository;
    @Override
    public User findByUserName(String userName) {
        return peopleRepository.findByUserName(userName);
    }
}
