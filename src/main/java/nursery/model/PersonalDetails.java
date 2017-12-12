package nursery.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PersonalDetails {
	@Column
	private String attention;
	private String firstName;
	private String middleName;
	private String lastName;

	private String gender;
	private String age;

	// for JPA
	public PersonalDetails() {
	}

}
