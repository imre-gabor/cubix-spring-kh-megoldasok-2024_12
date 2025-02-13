package hu.webuni.university.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.university.model.UniversityUser;
import hu.webuni.university.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {
	
	private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com/v13.0"; 
	
	private final UserRepository userRepository;

	@Getter
	@Setter
	public static class FacebookData {
		private String email;
		private long id;
	}

	
	@Transactional
	public UserDetails getUserDetailsForToken(String fbToken) {
		FacebookData fbData = getEmailOfFbUser(fbToken);
		UniversityUser universityUser = findOrCreateUser(fbData);
		return null;//UniversityUserDetailsService.createUserDetails(universityUser);

	}

	private FacebookData getEmailOfFbUser(String fbToken) {
		//TODO
		return null;
	}
	

	private UniversityUser findOrCreateUser(FacebookData facebookData) {
		//TODO
		return null;
	}

}
