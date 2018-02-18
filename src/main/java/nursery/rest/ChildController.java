package nursery.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nursery.model.Child;
import nursery.services.ChildService;

@RestController
@RequestMapping("/children")
public class ChildController {
    private final ChildService childService;

    @Autowired
    ChildController(ChildService childRepository) {
        this.childService = childRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Child findChild(@PathVariable Long id) {
        return this.childService.findChild(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Child> findChildren(
            @RequestParam(required = false, value = "checkedin") Boolean checkedin) {
        return this.childService.findChildren(checkedin);
    }
}
