package nursery;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import nursery.dao.ChildRepository;
import nursery.dao.ContactRepository;
import nursery.dao.RelationshipRepository;
import nursery.dao.UserRepository;
import nursery.model.Checkin;
import nursery.model.Child;
import nursery.services.CheckinService;
import nursery.services.CheckoutService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

	private static final String ZERO = "0";

	private static final String STOP_PARAMETER = "stop";

	private static final String START_PARAMETER = "start";

	private static final String CHECKINS_PATH = "/checkins/";

	private static final String CHECKOUTS_PATH = "/checkouts/";

	private static final String CHILDREN_PATH = "/children/";

	private static final String REPORT_PATH = "/report/";

	private static final String CHILD_ID = "1";

	private static final String MISSING_CHILD_ID = "1111";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private Child child;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private nursery.dao.CheckinRepository checkinRepository;

	@Autowired
	private nursery.dao.CheckoutRepository checkoutRepository;

	@Autowired
	private CheckinService checkinService;

	@Autowired
	private CheckoutService checkoutService;

	@Autowired
	private ChildRepository childRepository;

	@Autowired
	private RelationshipRepository relationshipRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		checkinRepository.deleteAllInBatch();
		checkoutRepository.deleteAllInBatch();
		relationshipRepository.deleteAllInBatch();
		contactRepository.deleteAllInBatch();
		childRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

		child = childRepository.save(new Child(CHILD_ID));
	}

	@Test
	public void givenAlreadyCreatedChild_whenQueryForItsId_thenItIsFound() throws Exception {
		mockMvc.perform(get(CHILDREN_PATH + child.getId()).contentType(contentType)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
	}

	@Test
	public void givenAlreadyCreatedChild_whenQueryForAll_thenOneIsFound() throws Exception {
		mockMvc.perform(get(CHILDREN_PATH).contentType(contentType)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void givenAlreadyCreatedChild_whenQueryForAnotherId_thenItIsNotFound() throws Exception {
		mockMvc.perform(get(CHILDREN_PATH + MISSING_CHILD_ID).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenAlreadyCreatedChild_whenCheckingIn_thenItIsNotFound() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + MISSING_CHILD_ID + CHECKINS_PATH).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenAlreadyCreatedChild_whenCheckingOut_thenItIsNotFound() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + MISSING_CHILD_ID + CHECKOUTS_PATH).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenAlreadyCreatedChild_whenCheckingOut_thenItCheckoutIsCreated() throws Exception {
		checkinService.createCheckin(child.getId());

		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKOUTS_PATH).contentType(contentType))
				.andExpect(status().isCreated());
	}

	@Test
	public void givenCheckedOutChild_whenCheckingOut_thenErrorIsShown() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKOUTS_PATH).contentType(contentType))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void givenCheckedOutChild_whenCheckingIn_thenCheckinIsCreated() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKINS_PATH).contentType(contentType))
				.andExpect(status().isCreated());
	}

	@Test
	public void givenCheckedInChild_whenCheckingIn_thenErrorIsShown() throws Exception {
		checkinService.createCheckin(child.getId());

		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKINS_PATH).contentType(contentType))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void givenChildWithCheckinsAndCheckouts_whenReportingAll_thenAllAreShown() throws Exception {
		checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());
		checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());

		mockMvc.perform(
				get(REPORT_PATH).param(START_PARAMETER, ZERO).param(STOP_PARAMETER, ZERO).contentType(contentType))
				.andExpect(jsonPath("$.checkins", hasSize(2))).andExpect(jsonPath("$.checkouts", hasSize(2)));
	}

	@Test
	public void givenChildWithCheckinsAndCheckouts_whenReportingSubset_thenOneIsShown() throws Exception {
		Checkin first = checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());
		checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());

		mockMvc.perform(get(REPORT_PATH).param(START_PARAMETER, ZERO)
				.param(STOP_PARAMETER, Long.toString(first.getTimestamp() + 1)).contentType(contentType))
				.andExpect(jsonPath("$.checkins", hasSize(1))).andExpect(jsonPath("$.checkouts", hasSize(0)));
	}

	@Test
	public void givenChildWithCheckinsAndCheckouts_whenReportingSubset_thenNoneIsShown() throws Exception {
		Checkin first = checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());
		checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());

		mockMvc.perform(get(REPORT_PATH).param(START_PARAMETER, ZERO)
				.param(STOP_PARAMETER, Long.toString(first.getTimestamp() - 1)).contentType(contentType))
				.andExpect(jsonPath("$.checkins", hasSize(0)));
	}
}
