package nursery.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nursery.model.Checkout;
import nursery.model.Child;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
	Collection<Checkout> findByChild(Child child);

	Collection<Checkout> findByTimestampBetween(long start, long stop);
}
