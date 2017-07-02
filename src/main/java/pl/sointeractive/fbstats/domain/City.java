package pl.sointeractive.fbstats.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"countryName",
	"cityName",
	"stateName",
	"coords"
})
public class City {

	@JsonProperty("countryName")
	private String countryName;
	@JsonProperty("cityName")
	private String cityName;
	@JsonProperty("stateName")
	private String stateName;
	@JsonProperty("coords")
	private Coords coords;

	@JsonProperty("countryName")
	public String getCountryName() {
		return countryName;
	}

	@JsonProperty("cityName")
	public String getCityName() {
		return cityName;
	}

	@JsonProperty("stateName")
	public String getStateName() {
		return stateName;
	}

	@JsonProperty("coords")
	public Coords getCoords() {
		return coords;
	}



}