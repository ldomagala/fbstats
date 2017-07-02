package pl.sointeractive.fbstats.infrastructure;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import pl.sointeractive.fbstats.domain.Post;
import pl.sointeractive.fbstats.domain.PostAggregatorService;

@Service
public class SerialPostAggregatorService implements PostAggregatorService {

	private static String REMOVE_PUNCTUATION_REGEX="[,.!?():;$]";
	private static String REMOVE_INTEGER_REGEX="^\\d+$";
	private static String SPLIT_REGEX="[\\s/]";

	@Override
	public Map<String,Long> countWordsInPosts(Stream<Post> posts){
		return posts
			.map(Post::getMessage)
			.flatMap(message -> Arrays.stream(
				message.split(SPLIT_REGEX)))
			.map(String::toLowerCase)
			.map(word -> word.replaceAll(REMOVE_PUNCTUATION_REGEX,""))
			.map(word -> word.replaceAll(REMOVE_INTEGER_REGEX,""))
			.filter(word -> !word.isEmpty())
			.collect(
				Collectors.groupingBy(identity(),counting()));
	}

	@Override
	public Stream<Post> findPostsByKeyword(String word, Stream<Post> posts){
		return posts
			.filter(post -> post
					.getMessage()
					.toLowerCase()
					.matches(".*\\b"+word.toLowerCase()+"\\b.*"));
	}

}
