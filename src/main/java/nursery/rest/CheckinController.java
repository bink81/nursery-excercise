package nursery.rest;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import nursery.model.Checkin;
import nursery.services.CheckinService;

@RestController
@RequestMapping("/children/{childId}/checkins")
class CheckinController {

    private final CheckinService checkinService;

    @Autowired
    CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Checkin> readChildren(@PathVariable Long childId) {
        return this.checkinService.findByChild(childId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Checkin> add(@PathVariable Long childId) {
        final Checkin newCheckin = this.checkinService.createCheckin(childId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newCheckin.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
