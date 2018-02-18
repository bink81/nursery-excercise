package nursery.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nursery.model.Checkin;
import nursery.model.Child;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {
    Collection<Checkin> findByChild(Child child);

    Collection<Checkin> findByTimestampBetween(long start, long stop);
}
