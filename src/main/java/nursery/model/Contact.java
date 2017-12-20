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

	public Contact(String name, Address address, Set<Relationship> relationships) {
		super();
		this.setName(name);
		this.setAddress(address);
		this.setRelationships(relationships);
		this.setUser(user);
	}

	public Set<Relationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(Set<Relationship> relationships) {
		this.relationships = relationships;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return user != null ? user.getId() : null;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
