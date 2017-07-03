package pl.sointeractive.fbstats.ui.rest;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.sointeractive.fbstats.application.FacebookService;
import pl.sointeractive.fbstats.application.exceptions.NotFoundException;
import pl.sointeractive.fbstats.domain.Facebook;

@RestController
@RequestMapping(value = "/fbstats")
public class FacebookRestController {

	@Autowired
	private  FacebookService facebookService;

	@RequestMapping(value = "/profile/{facebookId}", produces = "application/json", method = RequestMethod.GET)
	public Facebook profile(@PathVariable String facebookId) throws NotFoundException {
		return facebookService.findById(facebookId);
	}

	@RequestMapping(value = "/profiles", produces = "application/json", method = RequestMethod.GET)
	public Set<Facebook> profiles(){
		return facebookService.findAll();
	}

	@RequestMapping(value = "/word-statistics", produces = "application/json", method = RequestMethod.GET)
	public Map<String,Long> wordStatistics(){
		return facebookService.findMostCommonWords();
	}

	@RequestMapping(value = "/post-search", produces = "application/json", method = RequestMethod.GET)
	public Set<String> findPostIdsByKeyword(@RequestParam("keyword") String keyword){
		return facebookService.findPostIdsByKeyword(keyword);
	}
}
