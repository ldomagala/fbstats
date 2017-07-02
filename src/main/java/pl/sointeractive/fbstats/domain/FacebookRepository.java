package pl.sointeractive.fbstats.domain;

import java.util.List;
import java.util.Optional;

public interface FacebookRepository {
	Optional<Facebook> getById(String facebookId);
	List<Facebook> getAll();
}
