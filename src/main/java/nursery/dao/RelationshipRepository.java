package nursery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nursery.model.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
}
