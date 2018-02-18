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
        final Child child = this.childRepository.findOne(id);
        if (child == null) {
            throw new ChildNotFoundException(id);
        }
        return child;
    }

    public Collection<Child> findChildren(Boolean checkedin) {
        if (checkedin == null) {
            return this.childRepository.findAll();
        }
        return this.childRepository.findByCheckedin(checkedin);
    }

    public Child saveChild(Child child) {
        return this.childRepository.save(child);
    }

    public Collection<Child> findAllCheckedin(boolean checkedIn) {
        return this.childRepository.findByCheckedin(checkedIn);
    }

    public Contact saveContact(Contact contact) {
        final Set<Relationship> relationships = contact.getRelationships();
        for (final Relationship relationship : relationships) {
            this.relationshipRepository.save(relationship);
        }
        return this.contactRepository.save(contact);
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
