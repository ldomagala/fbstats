package pl.sointeractive.fbstats.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import pl.sointeractive.fbstats.domain.Post;

public class SerialPostAggregatorServiceTest {

	private ArrayList<Post> posts;

	private SerialPostAggregatorService serialPostAggregatorService;

	@Before
	public void setUp(){
		posts = Lists.newArrayList();
		serialPostAggregatorService = new SerialPostAggregatorService();
	}

	@Test
	public void shouldCorrectlyCountWordOccurencesInPosts(){
		//given
		posts.add(samplePost("1","You are so interactive."));
		posts.add(samplePost("2","So are you:)"));
		posts.add(samplePost("3","Thank you, you are so kind!!"));
		posts.add(samplePost("4","?? 1 23,4 $56.78 1018p 3D 4k"));
		//when
		Map<String,Long> wordCount = serialPostAggregatorService.countWordsInPosts(posts.stream());

		//then
		assertThat(wordCount.get("thank")).isEqualTo(1);
		assertThat(wordCount.get("you")).isEqualTo(4);
		assertThat(wordCount.get("are")).isEqualTo(3);
		assertThat(wordCount.get("so")).isEqualTo(3);
		assertThat(wordCount.get("kind")).isEqualTo(1);
		assertThat(wordCount.get("and")).isNull();
		assertThat(wordCount.get("interactive")).isEqualTo(1);
		assertThat(wordCount.get("")).isNull();
		assertThat(wordCount.get("You")).isNull();
		assertThat(wordCount.get("1018p")).isEqualTo(1);
		assertThat(wordCount.get("3d")).isEqualTo(1);
		assertThat(wordCount.keySet()).hasSize(9);

	}

	@Test
	public void shouldFindPostWithKeyword(){
		//given
		posts.add(samplePost("1","You are so interactive."));
		posts.add(samplePost("2","So are you:)"));

		//when
		Set<Post> postsWithSo = serialPostAggregatorService.findPostsByKeyword("so", posts.stream())
			.collect(Collectors.toSet());
		Set<Post> postsWithIn = serialPostAggregatorService.findPostsByKeyword("in", posts.stream())
			.collect(Collectors.toSet());
		Set<Post> postsWithYou = serialPostAggregatorService.findPostsByKeyword("You", posts.stream())
			.collect(Collectors.toSet());

		//then
		assertThat(postsWithSo).hasSize(2);
		assertThat(postsWithIn).hasSize(0);
		assertThat(postsWithYou).hasSize(2);
	}

	private Post samplePost(String postId, String message){
		return new Post(postId, message);
	}
}