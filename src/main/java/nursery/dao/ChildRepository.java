package nursery.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nursery.model.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
	Collection<Child> findByCheckedin(boolean checkedin);
}
