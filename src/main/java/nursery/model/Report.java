package nursery.model;

import java.util.Collection;

public class Report {

	private final Collection<Checkin> checkins;
	private final Collection<Checkout> checkouts;

	public Report(Collection<Checkin> checkins, Collection<Checkout> checkouts) {
		this.checkins = checkins;
		this.checkouts = checkouts;
	}

	public Collection<Checkin> getCheckins() {
		return checkins;
	}

	public Collection<Checkout> getCheckouts() {
		return checkouts;
	}

	@Override
	public String toString() {
		return "Report [checkins=" + checkins + ", checkouts=" + checkouts + "]";
	}
}
