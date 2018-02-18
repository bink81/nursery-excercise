package nursery.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nursery.dao.CheckinRepository;
import nursery.dao.CheckoutRepository;
import nursery.model.Checkin;
import nursery.model.Checkout;
import nursery.model.Report;

@RestController
@RequestMapping("/report")
class ReportController {
    private final CheckinRepository checkinRepository;
    private final CheckoutRepository checkoutRepository;

    @Autowired
    ReportController(final CheckinRepository checkinRepository,
            final CheckoutRepository checkoutRepository) {
        this.checkinRepository = checkinRepository;
        this.checkoutRepository = checkoutRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Report findCheckedInChildren(
            @RequestParam(required = true, value = "start") final long start,
            @RequestParam(required = true, value = "stop") final long stop) {
        Collection<Checkin> checkins;
        Collection<Checkout> checkouts;
        // show all for debugging purpose
        if (start == 0 && stop == 0) {
            checkins = this.checkinRepository.findAll();
            checkouts = this.checkoutRepository.findAll();
        } else {
            checkins = this.checkinRepository.findByTimestampBetween(start,
                    stop);
            checkouts = this.checkoutRepository.findByTimestampBetween(start,
                    stop);
        }
        final Report report = new Report(start, stop, checkins, checkouts);
        return report;
    }
}
