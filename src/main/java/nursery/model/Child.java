package nursery.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private final Set<Checkin> checkins = new HashSet<>();

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private final Set<Checkin> checkouts = new HashSet<>();

    private boolean checkedin;

    public boolean getCheckedin() {
        return this.checkedin;
    }

    public void setCheckedin(final boolean checkedin) {
        this.checkedin = checkedin;
    }

    public Child(final String name) {
        this.name = name;
    }

    // for JPA
    protected Child() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Child [id=" + this.id + ", name=" + this.name + ", address="
                + this.address + ", checkins=" + this.checkins + ", checkouts="
                + this.checkouts + ", checkedin=" + this.checkedin + "]";
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }
}
