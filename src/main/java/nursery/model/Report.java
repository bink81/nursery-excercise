package nursery.model;

import java.util.Collection;

public class Report {

	private final Collection<Checkin> checkins;
	private final Collection<Checkout> checkouts;
	private final long start;
	private final long stop;

	public Report(long start, long stop, Collection<Checkin> checkins, Collection<Checkout> checkouts) {
		this.start = start;
		this.stop = stop;
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

	public long getStart() {
		return start;
	}

	public long getStop() {
		return stop;
	}
}
