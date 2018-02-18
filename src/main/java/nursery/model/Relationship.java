package nursery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Relationship {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private RelationshipType relationshipType;

    @ManyToOne
    @JsonIgnore
    private Contact contact;

    @OneToOne
    private Child child;

    // for JPA
    public Relationship() {
    }

    public Relationship(RelationshipType relationshipType, Contact contact,
            Child child) {
        super();
        this.setRelationshipType(relationshipType);
        this.contact = contact;
        this.setChild(child);
    }

    public RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Long getChildId() {
        return this.child.getId();
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
