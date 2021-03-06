package nursery.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Contact {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
    private Set<Relationship> relationships = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private User user;

    // for JPA
    public Contact() {
    }

    public Contact(final String name, final Address address,
            final Set<Relationship> relationships) {
        super();
        this.setName(name);
        this.setAddress(address);
        this.setRelationships(relationships);
        this.setUser(this.user);
    }

    public Set<Relationship> getRelationships() {
        return this.relationships;
    }

    public void setRelationships(final Set<Relationship> relationships) {
        this.relationships = relationships;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
