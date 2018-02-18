package nursery.model;

import java.util.Collection;

public class Report {

    private final Collection<Checkin> checkins;
    private final Collection<Checkout> checkouts;
    private final long start;
    private final long stop;

    public Report(final long start, final long stop,
            final Collection<Checkin> checkins,
            final Collection<Checkout> checkouts) {
        this.start = start;
        this.stop = stop;
        this.checkins = checkins;
        this.checkouts = checkouts;
    }

    public Collection<Checkin> getCheckins() {
        return this.checkins;
    }

    public Collection<Checkout> getCheckouts() {
        return this.checkouts;
    }

    @Override
    public String toString() {
        return "Report [checkins=" + this.checkins + ", checkouts="
                + this.checkouts + "]";
    }

    public long getStart() {
        return this.start;
    }

    public long getStop() {
        return this.stop;
    }
}
