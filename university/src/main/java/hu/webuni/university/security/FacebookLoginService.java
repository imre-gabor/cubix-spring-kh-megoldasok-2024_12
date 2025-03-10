package hu.webuni.university.security;

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
import hu.webuni.university.security.FacebookLoginService.DebugTokenData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {
	
	
	private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com"; 
	
	private final UserRepository userRepository;
	
	@Value("${university.fb-app-id}")
	private String fbAppId;

	@Getter
	@Setter
	public static class FacebookData {
		private String email;
		private long id;
	}
	
	@Getter
	@Setter
	public static class DebugTokenResponse {
		private DebugTokenData data;
	}
	
	@Getter
	@Setter
	public static class DebugTokenData {
		private String app_id;
	}


	
	@Transactional
	public UserDetails getUserDetailsForToken(String fbToken) {
		FacebookData fbData = getEmailOfFbUser(fbToken);
		UniversityUser universityUser = findOrCreateUser(fbData);
		return UniversityUserDetailsService.createUserDetails(universityUser);

	}

	private FacebookData getEmailOfFbUser(String fbToken) {
		checkAppId(fbToken);
		
		return WebClient.create(GRAPH_API_BASE_URL)
		.get()
		.uri(uriBuilder -> uriBuilder
				.path("/me")
				.queryParam("fields", "email,name")
				.build())
		.headers(headers -> headers.setBearerAuth(fbToken))
		.retrieve()
		.bodyToMono(FacebookData.class)
		.block();
	}
	

	private void checkAppId(String fbToken) {
		String appId = 
			WebClient.create(GRAPH_API_BASE_URL)
			.get()
			.uri(uriBuilder -> uriBuilder
					.path("/debug_token")
					.queryParam("input_token", fbToken)
					.build())
			.headers(headers -> headers.setBearerAuth(fbToken))
			.retrieve()
			.bodyToMono(DebugTokenResponse.class)
			.block()
			.getData().getApp_id();
		if(!fbAppId.equals(appId)) {
			throw new BadCredentialsException("The facebook auth token is for a different app!");
		}
	}

	private UniversityUser findOrCreateUser(FacebookData facebookData) {
		String facebookId = String.valueOf(facebookData.getId());
		Optional<UniversityUser> optExistingUser = userRepository.findByFacebookId(facebookId);
		if(optExistingUser.isEmpty()) {
			Student newUser = Student.builder()
			.facebookId(facebookId)
			.username(facebookData.getEmail())
			.password("dummy")
			.build();
			return userRepository.save(newUser);
		}
		return optExistingUser.get();
	}

}
