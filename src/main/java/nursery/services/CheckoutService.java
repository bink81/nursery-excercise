package nursery.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nursery.dao.CheckoutRepository;
import nursery.model.Checkout;
import nursery.model.Child;

@Service
@Transactional
public class CheckoutService {
    private final CheckoutRepository checkoutRepository;

    private final ChildService childService;

    @Autowired
    CheckoutService(CheckoutRepository checkinRepository,
            ChildService childRepository) {
        this.checkoutRepository = checkinRepository;
        this.childService = childRepository;
    }

    public Collection<Checkout> findByChild(Long childId) {
        final Child child = this.childService.findChild(childId);
        return this.checkoutRepository.findByChild(child);
    }

    public Checkout createCheckout(Long childId) {
        final Child child = this.childService.findChild(childId);
        if (!child.getCheckedin()) {
            throw new CheckoutException(childId);
        }
        child.setCheckedin(false);
        this.childService.saveChild(child);

        return this.checkoutRepository
                .save(new Checkout(child, System.currentTimeMillis()));
    }
}
