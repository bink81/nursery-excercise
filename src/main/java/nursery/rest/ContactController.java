package nursery.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nursery.dao.ContactRepository;
import nursery.model.Contact;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactRepository contactRepository;

    @Autowired
    ContactController(final ContactRepository childRepository) {
        this.contactRepository = childRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Contact findChild(@PathVariable final Long id) {
        return this.contactRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Contact> findChildren() {
        return this.contactRepository.findAll();
    }
}
