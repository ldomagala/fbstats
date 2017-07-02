package pl.sointeractive.fbstats.domain;

import java.util.Map;
import java.util.stream.Stream;

public interface PostAggregatorService {
	Map<String,Long> countWordsInPosts(Stream<Post> posts);
	Stream<Post> findPostsByKeyword(String word, Stream<Post> posts);
}
