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

import nursery.model.Checkout;
import nursery.services.CheckoutService;

@RestController
@RequestMapping("/children/{childId}/checkouts")
class CheckoutController {

	private final CheckoutService checkoutService;

	@Autowired
	CheckoutController(CheckoutService checkinService) {
		this.checkoutService = checkinService;
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Checkout> readChildren(@PathVariable Long childId) {
		return checkoutService.findByChild(childId);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Checkout> add(@PathVariable Long childId) {
		Checkout newCheckout = checkoutService.createCheckout(childId);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCheckout.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
