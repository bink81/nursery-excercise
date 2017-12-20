package nursery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Checkout {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JsonIgnore
	private Child child;

	@Column(nullable = false)
	private long timestamp;

	// for JPA
	protected Checkout() {
	}

	public Checkout(Child child, long timestamp) {
		super();
		this.child = child;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public Long getChildId() {
		return child.getId();
	}

	@Override
	public String toString() {
		return "Checkout [id=" + id + ", child=" + child.getId() + ", timestamp=" + timestamp + "]";
	}
}
