package nursery;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nursery.model.Child;
import nursery.services.CheckinService;
import nursery.services.CheckoutService;
import nursery.services.ChildService;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	// demo data
	@Bean
	CommandLineRunner init(ChildService childService, CheckinService checkinService, CheckoutService checkoutService) {
		Child child1 = childService.saveChild(new Child("1"));
		checkinService.createCheckin(child1.getId());

		Child child2 = childService.saveChild(new Child("2"));
		checkinService.createCheckin(child2.getId());
		checkoutService.createCheckout(child2.getId());

		Child child3 = childService.saveChild(new Child("3"));
		checkinService.createCheckin(child3.getId());
		checkoutService.createCheckout(child3.getId());
		checkinService.createCheckin(child3.getId());

		return null;
	}
}