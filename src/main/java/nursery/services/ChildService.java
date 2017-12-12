package nursery.services;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nursery.dao.ChildRepository;
import nursery.dao.ContactRepository;
import nursery.dao.RelationshipRepository;
import nursery.dao.UserRepository;
import nursery.model.Child;
import nursery.model.Contact;
import nursery.model.Relationship;
import nursery.model.User;

@Service
@Transactional
public class ChildService {
	@Autowired
	private ChildRepository childRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private RelationshipRepository relationshipRepository;
	@Autowired
	private UserRepository userRepository;

	public Child findChild(Long id) {
		Child child = childRepository.findOne(id);
		if (child == null) {
			throw new ChildNotFoundException(id);
		}
		return child;
	}

	public Collection<Child> findChildren(Boolean checkedin) {
		if (checkedin == null) {
			return childRepository.findAll();
		}
		return childRepository.findByCheckedin(checkedin);
	}

	public Child saveChild(Child child) {
		return childRepository.save(child);
	}

	public Collection<Child> findAllCheckedin(boolean checkedIn) {
		return childRepository.findByCheckedin(checkedIn);
	}

	public Contact saveContact(Contact contact) {
		Set<Relationship> relationships = contact.getRelationships();
		for (Relationship relationship : relationships) {
			relationshipRepository.save(relationship);
		}
		return contactRepository.save(contact);
	}

	public void saveUser(User user) {
		// Set<Contact> relationships = user.getContacts();
		// for (Contact relationship : relationships) {
		// contactRepository.save(relationship);
		// }
		userRepository.save(user);
	}
}
