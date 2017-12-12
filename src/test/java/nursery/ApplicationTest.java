package nursery;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.UnsupportedEncodingException;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import nursery.dao.ChildRepository;
import nursery.model.Checkin;
import nursery.model.Child;
import nursery.services.CheckinService;
import nursery.services.CheckoutService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

	private static final String ID_TAG = "\"id\":";

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

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		checkinRepository.deleteAllInBatch();
		checkoutRepository.deleteAllInBatch();
		childRepository.deleteAllInBatch();
		child = childRepository.save(new Child(CHILD_ID));
	}

	@Test
	public void childFound() throws Exception {
		mockMvc.perform(get(CHILDREN_PATH + child.getId()).contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public void childrenNonEmpty() throws Exception {
		mockMvc.perform(get(CHILDREN_PATH).contentType(contentType))
				.andDo(result -> assertTrue(doesContentContainAnEntry(result)));
	}

	@Test
	public void childNotFound() throws Exception {
		mockMvc.perform(get(CHILDREN_PATH + MISSING_CHILD_ID).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void childNotFoundDuringCheckin() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + MISSING_CHILD_ID + CHECKINS_PATH).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void childNotFoundDuringCheckout() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + MISSING_CHILD_ID + CHECKOUTS_PATH).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void childChecksOut() throws Exception {
		checkinService.createCheckin(child.getId());
		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKOUTS_PATH).contentType(contentType))
				.andExpect(status().isCreated());
	}

	@Test
	public void childAlreadyCheckedOut() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKOUTS_PATH).contentType(contentType))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void childChecksIn() throws Exception {
		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKINS_PATH).contentType(contentType))
				.andExpect(status().isCreated());
	}

	@Test
	public void childAlreadyCheckedIn() throws Exception {
		checkinService.createCheckin(child.getId());

		mockMvc.perform(post(CHILDREN_PATH + child.getId() + CHECKINS_PATH).contentType(contentType))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void showAllAttendance() throws Exception {
		checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());
		checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());

		mockMvc.perform(get(REPORT_PATH).param("start", "0").param("stop", "0").contentType(contentType))
				.andDo(result -> assertTrue(doesContentContainAnEntry(result)));
	}

	@Test
	public void showNoAttendance() throws Exception {
		Checkin first = checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());
		checkinService.createCheckin(child.getId());
		checkoutService.createCheckout(child.getId());

		mockMvc.perform(get(REPORT_PATH).param("start", "0").param("stop", Long.toString(first.getTimestamp() - 1))
				.contentType(contentType)).andDo(result -> assertFalse(doesContentContainAnEntry(result)));
	}

	// this quick validation of content could be replaced with parsing and
	// comparing all details
	private boolean doesContentContainAnEntry(MvcResult result) throws UnsupportedEncodingException {
		String contentAsString = result.getResponse().getContentAsString();
		return contentAsString.contains(ID_TAG);
	}
}
