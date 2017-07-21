package it.inserpio.neo4art.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.*;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lsy on 2017/7/11.
 */
@Deprecated
@NodeEntity
public class ServerFunc extends IdentifiableEntity {

    @Indexed(unique = true)
    private String serverFuncId;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "clazzName")
    private String className;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "funcName")
    private String funcName;

    @Fetch
    @RelatedTo(type = "Invoke", direction = Direction.OUTGOING)
    private Set<ServerFunc> targets = new HashSet<>();


//    @Fetch
//    @Relationship(type = "Invoke", direction = Relationship.OUTGOING)
//    private List<Invoke> invokes = new ArrayList<>();
//
//    @Fetch
//    @Relationship(type = "Invoke", direction = Relationship.INCOMING)
//    private List<Invoke> invoked = new ArrayList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public Set<ServerFunc> getTargets() {
        return targets;
    }

    public void setTargets(Set<ServerFunc> targets) {
        this.targets = targets;
    }

    public ServerFunc() {
    }

    public ServerFunc(String className, String funcName) {
        this.className = className;
        this.funcName = funcName;
        this.serverFuncId = "" + this.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ServerFunc &&
                ((ServerFunc)obj).className.equals(this.className) &&
                ((ServerFunc)obj).funcName.equals(this.funcName);
    }

    @Override
    public int hashCode() {
        return  (className == null || funcName == null ) ? super.hashCode() : className.hashCode()+funcName.hashCode()*31;
    }
}
