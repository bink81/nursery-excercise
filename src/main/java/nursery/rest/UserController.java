package nursery.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nursery.dao.UserRepository;
import nursery.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    UserController(final UserRepository childRepository) {
        this.userRepository = childRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    User findChild(@PathVariable final Long id) {
        return this.userRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<User> findChildren() {
        return this.userRepository.findAll();
    }
}
