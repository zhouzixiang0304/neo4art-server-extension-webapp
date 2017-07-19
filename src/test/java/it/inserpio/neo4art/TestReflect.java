package it.inserpio.neo4art;

import it.inserpio.neo4art.model.User;
import it.inserpio.neo4art.util.ToD3Format;
import org.junit.Test;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by lsy on 2017/7/18.
 */
public class TestReflect {
    @Test
    public void testReflectAnnotion() throws NoSuchFieldException{
        Class<User> clazz = User.class;
        Field field = clazz.getDeclaredField("friends");
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
        System.out.println(field.isAnnotationPresent(RelatedTo.class));
    }
}
