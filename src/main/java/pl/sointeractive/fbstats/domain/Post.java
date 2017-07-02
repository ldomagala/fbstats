package pl.sointeractive.fbstats.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"id",
	"message"
})
public class Post {

	@JsonProperty("id")
	private String id;
	@JsonProperty("message")
	private String message;

	@JsonProperty("id")
	public String getId() {
		return id;
	}
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	public Post() {
	}

	public Post(String id, String message) {
		this.id = id;
		this.message = message;
	}
}