package nursery.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	@OneToMany(mappedBy = "child")
	private Set<Checkin> checkins = new HashSet<>();

	private boolean checkedin;

	public boolean getCheckedin() {
		return checkedin;
	}

	public void setCheckedin(boolean checkedin) {
		this.checkedin = checkedin;
	}

	public Child(String name) {
		this.name = name;
		// this.checkedin = false;
	}

	// for JPA
	public Child() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Child [id=" + id + ", name=" + name + ", checkins=" + checkins + ", checkedin=" + checkedin + "]";
	}
}
