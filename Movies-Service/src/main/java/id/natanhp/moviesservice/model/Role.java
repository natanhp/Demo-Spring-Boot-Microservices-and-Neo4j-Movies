package id.natanhp.moviesservice.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Collection;

@Data
@RelationshipProperties
public class Role {
    @Id
    @GeneratedValue
    Long id;

    private Collection<String> roles;

    @TargetNode
    private Person person;
}
