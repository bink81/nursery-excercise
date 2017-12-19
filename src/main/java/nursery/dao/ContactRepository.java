package nursery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nursery.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
