package nursery;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nursery.model.Address;
import nursery.model.Child;
import nursery.model.Contact;
import nursery.model.Relationship;
import nursery.model.RelationshipType;
import nursery.model.User;
import nursery.services.CheckinService;
import nursery.services.CheckoutService;
import nursery.services.ChildService;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    // demo data
    @Bean
    CommandLineRunner init(final ChildService childService,
            final CheckinService checkinService,
            final CheckoutService checkoutService) {
        final Child child1 = childService.saveChild(new Child("1"));
        checkinService.createCheckin(child1.getId());

        final Child child2 = childService.saveChild(new Child("2"));
        checkinService.createCheckin(child2.getId());
        checkoutService.createCheckout(child2.getId());

        final Child child3 = childService.saveChild(new Child("3"));
        checkinService.createCheckin(child3.getId());
        checkoutService.createCheckout(child3.getId());
        checkinService.createCheckin(child3.getId());

        final Set<Contact> contacts = new HashSet<>();
        final User user = new User("aaaaaa", contacts, "123");

        final Contact emptyContact = new Contact("empty", null,
                Collections.emptySet());
        childService.saveContact(emptyContact);

        final Address address = new Address("aaaaaa");
        final Set<Relationship> relationships = new HashSet<>();
        final Contact contact = new Contact("123", address, relationships);
        relationships.add(
                new Relationship(RelationshipType.MOTHER, contact, child1));
        relationships.add(
                new Relationship(RelationshipType.GRANDMA, contact, child2));
        childService.saveContact(contact);
        contacts.add(contact);

        childService.saveUser(user);
        contact.setUser(user);
        childService.saveContact(contact);
        return null;
    }
}