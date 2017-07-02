package pl.sointeractive.fbstats.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import pl.sointeractive.fbstats.domain.Facebook;
import pl.sointeractive.fbstats.domain.FacebookRepository;

/**
 * Logarithmic time random access cache, with full preload
 */
@Component
public class LogTimeFullPreloadFacebookCache implements FacebookRepository {


	@Autowired
	@Qualifier("facebookFileRepository")
	private FacebookRepository facebookRepository;

	private TreeMap<String,Facebook> facebookDictionary = Maps.newTreeMap();

	@PostConstruct
	private void preloadCacheWithAllFacebookProfiles(){
		facebookRepository
			.getAll()
			.forEach(facebook -> facebookDictionary
				.put(facebook.getId(),facebook));
	}

	@Override
	public Optional<Facebook> getById(String facebookId) {
		return Optional.ofNullable(
			facebookDictionary.get(facebookId));
	}

	@Override
	public List<Facebook> getAll() {
		return Lists.newLinkedList(
			facebookDictionary.values());
	}
}
