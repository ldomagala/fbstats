package pl.sointeractive.fbstats.application;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pl.sointeractive.fbstats.application.exceptions.NotFoundException;
import pl.sointeractive.fbstats.domain.Facebook;
import pl.sointeractive.fbstats.domain.FacebookRepository;
import pl.sointeractive.fbstats.domain.Post;
import pl.sointeractive.fbstats.domain.PostAggregatorService;

@Service
public class FacebookApplicationService implements FacebookService {

	@Autowired
	@Qualifier("logTimeFullPreloadFacebookCache")
	private FacebookRepository facebookRepository;

	@Autowired
	private PostAggregatorService postAggregatorService;

	@Override
	public Facebook findById(String id) throws NotFoundException {
		return facebookRepository.getById(id)
			.orElseThrow(NotFoundException::new);
	}

	@Override
	public Map<String, Long> findMostCommonWords() {
		Stream<Post> posts = facebookRepository.getAll().stream()
			.flatMap(facebook -> facebook.getPosts().stream());
		return postAggregatorService.countWordsInPosts(posts) ;
	}

	@Override
	public Set<String> findPostIdsByKeyword(String word) {
		Stream<Post> posts = facebookRepository.getAll().stream()
			.flatMap(facebook -> facebook.getPosts().stream());

		return postAggregatorService
			.findPostsByKeyword(word, posts)
			.map(Post::getId)
			.collect(Collectors.toSet());
	}

	@Override
	public Set<Facebook> findAll() {
		return facebookRepository.getAll()
			.stream()
			.sorted(Comparator
				.comparing(Facebook::getFirstname)
				.thenComparing(Facebook::getLastname))
			.collect(Collectors.toCollection(
				LinkedHashSet::new));

	}

}
