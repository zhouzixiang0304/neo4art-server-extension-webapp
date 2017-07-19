package it.inserpio.neo4art.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.StartNode;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Created by lsy on 2017/7/11.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@RelationshipEntity(type = "Invoke")
public class Invoke extends IdentifiableEntity {
    @StartNode
    private ServerFunc startFunc;
    @EndNode
    private ServerFunc endFunc;

    private String description;

    public ServerFunc getStartFunc() {
        return startFunc;
    }

    public void setStartFunc(ServerFunc startFunc) {
        this.startFunc = startFunc;
    }

    public ServerFunc getEndFunc() {
        return endFunc;
    }

    public void setEndFunc(ServerFunc endFunc) {
        this.endFunc = endFunc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Invoke() {
    }

    public Invoke(ServerFunc startFunc, ServerFunc endFunc, String description) {
        this.startFunc = startFunc;
        this.endFunc = endFunc;
        this.description = description;
    }
}
