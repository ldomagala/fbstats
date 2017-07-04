package pl.sointeractive.fbstats.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import pl.sointeractive.fbstats.domain.Facebook;
import pl.sointeractive.fbstats.domain.FacebookRepository;

@RunWith(MockitoJUnitRunner.class)
public class LogTimeFullPreloadFacebookCacheTest {

	@Mock
	private FacebookRepository facebookRepository;

	@InjectMocks
	private LogTimeFullPreloadFacebookCache logTimeFullPreloadFacebookCache;

	private static final int FACEBOOK_ID_RANGE_START = 1;
	private static final int FACEBOOK_ID_RANGE_END = 5;

	@Before
	public void setUp(){
		List<Facebook> facebookMockProfiles =
			IntStream.rangeClosed(
				FACEBOOK_ID_RANGE_START, FACEBOOK_ID_RANGE_END)
			.mapToObj(this::createMockFacebookProfile)
			.collect(Collectors.toList());
		when(facebookRepository.getAll()).thenReturn(facebookMockProfiles);

	}

	@Test
	public void shouldNotAccessTheRepositoryAfterAnInitialPreload(){
		//given
		ReflectionTestUtils.invokeMethod(
			logTimeFullPreloadFacebookCache,
			"preloadCacheWithAllFacebookProfiles");

		//when
		Optional<Facebook> facebookFirst = logTimeFullPreloadFacebookCache.getById(
			Integer.toString(FACEBOOK_ID_RANGE_START));
		Optional<Facebook> facebookLast = logTimeFullPreloadFacebookCache.getById(
			Integer.toString(FACEBOOK_ID_RANGE_END));

		//then
		verify(facebookRepository, times(1)).getAll();
		verify(facebookRepository, times(0)).getById(any());
		assertThat(facebookFirst).isNotEmpty();
		assertThat(facebookFirst.get().getId()).isEqualTo(Integer.toString(FACEBOOK_ID_RANGE_START));
		assertThat(facebookLast).isNotEmpty();
		assertThat(facebookLast.get().getId()).isEqualTo(Integer.toString(FACEBOOK_ID_RANGE_END));
	}

	private Facebook createMockFacebookProfile(int id){
		return new Facebook(Integer.toString(id), "DummyName","DummySurname");
	}
}
