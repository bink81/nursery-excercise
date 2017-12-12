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
	CheckoutService(CheckoutRepository checkinRepository, ChildService childRepository) {
		this.checkoutRepository = checkinRepository;
		this.childService = childRepository;
	}

	public Collection<Checkout> findByChild(Long childId) {
		Child child = childService.findChild(childId);
		return checkoutRepository.findByChild(child);
	}

	public Checkout createCheckout(Long childId) {
		Child child = childService.findChild(childId);
		if (!child.getCheckedin()) {
			throw new CheckoutException(childId);
		}
		child.setCheckedin(false);
		childService.saveChild(child);

		return checkoutRepository.save(new Checkout(child, System.currentTimeMillis()));
	}
}
