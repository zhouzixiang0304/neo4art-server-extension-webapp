package it.inserpio.neo4art.util;



import it.inserpio.neo4art.model.User;
import org.omg.CORBA.UserException;
import org.springframework.data.neo4j.conversion.EndResult;

import java.util.*;

/**
 * Created by lsy on 2017/6/14.
 */
public class ToD3Format<T> {
    private final static ToD3Format toD3Format = new ToD3Format();
    private ToD3Format() {
    }
    public static ToD3Format getInstance(){
        return toD3Format;
    }
    public Map<String,Object>
    toD3Format(Collection<User> persons){
        List<Map<String,Object>> nodes = new ArrayList<>();
        List<Map<String,Object>> rels = new ArrayList<>();
        int i = 0;
        for(User p : persons) {
            nodes.add(map("title", p.getUserName(), "label", "person"));
        }
        for(User p : persons){
            int source = i;
            i++;
            if(p.getFriends().size()!=0) {
                for (User person :p.getFriends()) {
                        Map<String, Object> friend = map("title", person.getUserName(), "label", "person");
                        int target = nodes.indexOf(friend);
                        rels.add(map("source", source, "target", target));
                }
            }
        }
        return map("nodes",nodes,"links",rels);
    }

    public Map<String,Object> map(String key1, Object value1, String key2, Object value2){
        Map<String,Object> result = new HashMap<>(2);
        result.put(key1,value1);
        result.put(key2,value2);
        return result;
    }

    public Set<T> toCollection(EndResult<T> param){
        Set<T> result = new HashSet<>();
        Iterator<T> iterator = param.iterator();
        while(iterator.hasNext()){
            result.add(iterator.next());
        }
        return result;
    }
}
