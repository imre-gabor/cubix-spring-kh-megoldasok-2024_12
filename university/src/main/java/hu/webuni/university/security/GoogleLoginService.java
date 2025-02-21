package hu.webuni.university.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import hu.webuni.university.model.Student;
import hu.webuni.university.model.UniversityUser;
import hu.webuni.university.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {
	
	private static final String GOOGLE_BASE_URI = "https://oauth2.googleapis.com";
	
	private final UserRepository userRepository;
	
	@Value("${university.google-client-id}")
	private String googleClientId;
	
	@Getter
	@Setter
	public static class GoogleData {
		private String sub;
		private String email;

		private String aud;

		private String iss;
		private String azp;
		private String email_verified;
		private String at_hash;
		private String name;
		private String picture;
		private String given_name;
		private String family_name;
		private String locale;
		private String iat;
		private String exp;
		private String jti;
		private String alg;
		private String kid;
		private String typ;
	}
	
	@Transactional
	public UserDetails getUserDetailsForToken(String googleToken) {
		
		GoogleData googleData = getGoogleDataForToken(googleToken);
		if(!this.googleClientId.equals(googleData.getAud()))
			throw new BadCredentialsException("The aud claim of the google id token does not match the client id");
		
		UniversityUser universityUser = findOrCreateUser(googleData);
		return UniversityUserDetailsService.createUserDetails(universityUser);
	}

	private UniversityUser findOrCreateUser(GoogleData googleData) {
		String googleId = googleData.getSub();
		Optional<UniversityUser> optionalExistingUser = userRepository.findByGoogleId(googleId);
		if(optionalExistingUser.isEmpty()) {
			
			return userRepository.save(Student.builder()
			.googleId(googleId)
			.username(googleData.getEmail())
			.password("dummy")
			.courses(Collections.emptySet())
			.build());
		}
		
		return optionalExistingUser.get();
	}

	private GoogleData getGoogleDataForToken(String googleToken) {
		
		return WebClient.create(GOOGLE_BASE_URI)
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/tokeninfo")
						.queryParam("id_token", googleToken)
						.build())
				.retrieve()
				.bodyToMono(GoogleData.class)
				.block();
	}

}
