package pl.sointeractive.fbstats.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"id",
	"birthday",
	"firstname",
	"lastname",
	"occupation",
	"gender",
	"city",
	"work",
	"friends",
	"school",
	"location",
	"relationship",
	"posts"
})
public class Facebook {

	@JsonProperty("id")
	private String id;
	@JsonProperty("birthday")
	private Long birthday;
	@JsonProperty("firstname")
	private String firstname;
	@JsonProperty("lastname")
	private String lastname;
	@JsonProperty("occupation")
	private String occupation;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("city")
	private City city;
	@JsonProperty("work")
	private String work;
	@JsonProperty("friends")
	private List<String> friends = null;
	@JsonProperty("school")
	private String school;
	@JsonProperty("location")
	private String location;
	@JsonProperty("relationship")
	private String relationship;
	@JsonProperty("posts")
	private List<Post> posts = null;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("birthday")
	public Long getBirthday() {
		return birthday;
	}

	@JsonProperty("firstname")
	public String getFirstname() {
		return firstname;
	}

	@JsonProperty("lastname")
	public String getLastname() {
		return lastname;
	}

	@JsonProperty("occupation")
	public String getOccupation() {
		return occupation;
	}

	@JsonProperty("gender")
	public String getGender() {
		return gender;
	}

	@JsonProperty("city")
	public City getCity() {
		return city;
	}

	@JsonProperty("work")
	public String getWork() {
		return work;
	}

	@JsonProperty("friends")
	public List<String> getFriends() {
		return friends;
	}

	@JsonProperty("school")
	public String getSchool() {
		return school;
	}

	@JsonProperty("location")
	public String getLocation() {
		return location;
	}

	@JsonProperty("relationship")
	public String getRelationship() {
		return relationship;
	}

	@JsonProperty("posts")
	public List<Post> getPosts() {
		return posts;
	}

	public Facebook() {
	}

	public Facebook(String id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
}