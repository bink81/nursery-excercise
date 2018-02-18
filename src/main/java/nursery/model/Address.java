package nursery.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Column
    private String street;

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    // for JPA
    public Address() {
    }

    public Address(String street) {
        super();
        this.street = street;
    }
}
