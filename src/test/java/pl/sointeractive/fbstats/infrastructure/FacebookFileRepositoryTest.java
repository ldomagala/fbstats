package pl.sointeractive.fbstats.infrastructure;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import pl.sointeractive.fbstats.domain.Facebook;
import pl.sointeractive.fbstats.domain.Post;

public class FacebookFileRepositoryTest {

	private FacebookFileRepository facebookFileRepository;
	private static String VALID_FACEBOOK_FILES_DIRECTORY_PATH = "./src/test/resources/facebook-files";

	@Before
	public void setUp(){

		facebookFileRepository = new FacebookFileRepository();
		ReflectionTestUtils.setField(
			facebookFileRepository,"facebookFilesDirectoryPath",VALID_FACEBOOK_FILES_DIRECTORY_PATH);
	}

	@Test
	public void shouldRepositoryReturnAFacebookProfileForAnExistingFacebookId(){
		//given
		//a valid location of facebook files
		String idOfAnExistingFacebookProfile = "1";

		//when
		Optional<Facebook> facebookOptional = facebookFileRepository.getById(idOfAnExistingFacebookProfile);

		//then
		assertThat(facebookOptional.isPresent()).isTrue();
		Facebook facebook = facebookOptional.get();
		assertThat(facebook.getId()).isEqualTo(idOfAnExistingFacebookProfile);
		assertThat(facebook.getPosts()).hasSize(3);
		Post thirdPost = facebook.getPosts().get(2);
		assertThat(thirdPost.getId()).isEqualTo("3");
		assertThat(thirdPost.getMessage()).startsWith("We definitely wound up with buyers");

	}


	@Test
	public void shouldNotReturnAFacebookProfileForANonExistingFacebookId(){
		//given
		//a valid location of facebook files
		String idOfANonExistingFacebookProfile = "XYZ";

		//when
		Optional<Facebook> facebookOptional = facebookFileRepository.getById(idOfANonExistingFacebookProfile);

		//then
		assertThat(facebookOptional.isPresent()).isFalse();

	}

	@Test
	public void shouldReturnAllAvailableFacebookProfiles(){
		//given
		//a valid location of facebook files

		//when
		List<Facebook> facebookProfiles = facebookFileRepository.getAll();

		//then
		assertThat(facebookProfiles).hasSize(5);

	}

}
