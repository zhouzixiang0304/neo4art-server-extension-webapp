package it.inserpio.neo4art.service.impl;

import it.inserpio.neo4art.model.User;
import it.inserpio.neo4art.repository.UserRepository;
import it.inserpio.neo4art.service.UserService;
import it.inserpio.neo4art.util.ToD3Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by lsy on 2017/6/23.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        EndResult<User> all = userRepository.findAll();
        Iterator<User> iterator = all.iterator();
        List<User> list = new ArrayList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void setUpUsers() {
        User joe = new User("joe","Joe");
        User bill = new User("bill","Bill");
        User sara = new User("sara","Sara");
        User derrick = new User("derrick","Derrick");
        User ian = new User("ian","Ian");
        User jill = new User("jill","Jill");

        joe.knowsSomeone(bill);
        joe.knowsSomeone(sara);
        sara.knowsSomeone(bill);
        sara.knowsSomeone(jill);
        sara.knowsSomeone(ian);
        bill.knowsSomeone(ian);
        bill.knowsSomeone(derrick);

        userRepository.save(joe);
        userRepository.save(bill);
        userRepository.save(sara);
        userRepository.save(derrick);
        userRepository.save(ian);
        userRepository.save(jill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void purgeDatabase(){
        userRepository.deleteAll();
    }

    @Override
    public Map<String,Object> graph(int limit) {
        Collection<User> result = new HashSet<>();
        EndResult<User> all = userRepository.findAll();
        Iterator<User> iterator = all.iterator();
        while (iterator.hasNext()){
            result.add(iterator.next());
        }
        return ToD3Format.getInstance().toD3Format(result);
    }
}
