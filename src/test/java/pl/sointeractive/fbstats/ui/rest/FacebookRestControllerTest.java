package pl.sointeractive.fbstats.ui.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.sointeractive.fbstats.application.FacebookApplicationService;
import pl.sointeractive.fbstats.application.exceptions.NotFoundException;
import pl.sointeractive.fbstats.common.ui.rest.RestApiExceptionHandler;
import pl.sointeractive.fbstats.domain.Facebook;

@RunWith(MockitoJUnitRunner.class)
public class FacebookRestControllerTest {

	private MockMvc mockMvc;

	private static final String EXISTING_FACEBOOK_ID = "123";
	private static final String EXISTING_FACEBOOK_NAME = "SomeName";
	private static final String NON_EXISTING_FACEBOOK_ID = "999";

	@Mock
	private FacebookApplicationService facebookApplicationService;

	@InjectMocks
	private FacebookRestController facebookRestController = new FacebookRestController();


	@Before
	public void setUp(){

		mockMvc = MockMvcBuilders
			.standaloneSetup(facebookRestController)
			.setControllerAdvice(new RestApiExceptionHandler())
			.build();
	}

	@Test
	public void shouldReturnNotFoundWhenNonexistentFacebookIdRequested() throws Exception {
		//given
		doThrow(new NotFoundException())
			.when(facebookApplicationService).findById(matches(NON_EXISTING_FACEBOOK_ID));

		//when
		ResultActions resultActions = mockMvc.perform(
			get("/fbstats/profile/"+NON_EXISTING_FACEBOOK_ID));

		//then
		resultActions.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void shouldReturnFacebookProfileWhenValidFacebookIdPassed() throws Exception {
		//given
		Facebook facebook = new Facebook(EXISTING_FACEBOOK_ID, EXISTING_FACEBOOK_NAME, "SomeSurname");
		when(facebookApplicationService.findById(matches(EXISTING_FACEBOOK_ID))).thenReturn(facebook);

		//when
		ResultActions resultActions = mockMvc.perform(
			get("/fbstats/profile/"+EXISTING_FACEBOOK_ID));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		String responseJson = resultActions.andReturn().getResponse().getContentAsString();
		assertThat(responseJson).contains(EXISTING_FACEBOOK_ID);
		assertThat(responseJson).contains(EXISTING_FACEBOOK_NAME);

	}

}