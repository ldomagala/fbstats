package pl.sointeractive.fbstats.infrastructure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.sointeractive.fbstats.domain.Facebook;
import pl.sointeractive.fbstats.domain.FacebookRepository;

@Repository
public class FacebookFileRepository implements FacebookRepository {

	@Value("${facebook.directory.path}")
	private String facebookFilesDirectoryPath;

	private ObjectMapper objectMapper;
	private static String FACEBOOK_FILE_NAME_PREFIX = "f";
	private static String FACEBOOK_FILE_NAME_EXTENSION = ".json";


	public FacebookFileRepository() {
		this.objectMapper = new ObjectMapper();
	}

	private String getFacebookFilenameForId(String facebookId){
		return FACEBOOK_FILE_NAME_PREFIX+facebookId+FACEBOOK_FILE_NAME_EXTENSION;
	}

	@Override
	public Optional<Facebook> getById(String facebookId) {
		String facebookFilename = getFacebookFilenameForId(facebookId);
		return Optional.of(Paths.get(facebookFilesDirectoryPath,facebookFilename))
			.map(Path::toFile)
			.filter(File::exists)
			.filter(File::isFile)
			.map(this::safeReadFacebookFile);
	}


	@Override
	public List<Facebook> getAll() {
		return getAllFacebookFiles()
			.map(this::safeReadFacebookFile)
			.collect(Collectors.toList());
	}

	private Stream<File> getAllFacebookFiles() {
		try {
			return Files.list(Paths.get(facebookFilesDirectoryPath))
				.filter(Files::isRegularFile)
				.filter(p -> p.getFileName().toString().startsWith(FACEBOOK_FILE_NAME_PREFIX))
				.filter(p -> p.getFileName().toString().endsWith(FACEBOOK_FILE_NAME_EXTENSION))
				.map(p -> p.toFile());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Facebook safeReadFacebookFile(File facebookFile){
		try {
			return objectMapper.readValue(facebookFile, Facebook.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
