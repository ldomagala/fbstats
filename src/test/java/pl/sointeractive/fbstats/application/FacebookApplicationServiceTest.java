package pl.sointeractive.fbstats.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import pl.sointeractive.fbstats.domain.Facebook;
import pl.sointeractive.fbstats.domain.FacebookRepository;

@RunWith(MockitoJUnitRunner.class)
public class FacebookApplicationServiceTest {

	@Mock
	private FacebookRepository facebookRepository;

	@InjectMocks
	private FacebookApplicationService facebookApplicationService;

	@Before
	public void setUp() throws Exception {

	}


	@Test
	public void shoulGetAlldReturnASortedSet(){
		//given
		List<Facebook> facebookProfiles = Lists.newLinkedList();
		addSampleFacebook(facebookProfiles,"1", "Zenon", "Adamowicz");
		addSampleFacebook(facebookProfiles,"2", "Adam", "Kaleta");
		addSampleFacebook(facebookProfiles,"3", "Adam", "Świst");
		addSampleFacebook(facebookProfiles,"4", "Adrian", "Nowicki");
		when(facebookRepository.getAll()).thenReturn(facebookProfiles);

		//when
		ArrayList<Facebook> sortedFacebookProfiles = Lists.newArrayList();
		facebookApplicationService
			.findAll()
			.forEach(sortedFacebookProfiles::add);

		//then
		assertThat(sortedFacebookProfiles).hasSize(4);
		assertThat(sortedFacebookProfiles.get(0).getFirstname()).isEqualTo("Adam");
		assertThat(sortedFacebookProfiles.get(0).getLastname()).isEqualTo("Kaleta");
		assertThat(sortedFacebookProfiles.get(1).getFirstname()).isEqualTo("Adam");
		assertThat(sortedFacebookProfiles.get(1).getLastname()).isEqualTo("Świst");
		assertThat(sortedFacebookProfiles.get(2).getFirstname()).isEqualTo("Adrian");
		assertThat(sortedFacebookProfiles.get(2).getLastname()).isEqualTo("Nowicki");
		assertThat(sortedFacebookProfiles.get(3).getFirstname()).isEqualTo("Zenon");
		assertThat(sortedFacebookProfiles.get(3).getLastname()).isEqualTo("Adamowicz");
	}


	private void addSampleFacebook(List facefookProfiles, String id, String firstName, String lastName){
		Facebook facebook = new Facebook(id, firstName, lastName);
		facefookProfiles.add(facebook);
	}
}