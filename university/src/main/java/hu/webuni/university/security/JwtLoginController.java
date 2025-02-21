package hu.webuni.university.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.university.dto.LoginDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtLoginController {

	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final FacebookLoginService facebookLoginService;
	private final GoogleLoginService googleLoginService;
	
	@PostMapping("/api/login")
	public String login(@RequestBody LoginDto loginDto) {
		
		UserDetails userDetails = null;
		String fbToken = loginDto.getFbToken();
		String googleToken = loginDto.getGoogleToken();
		if(ObjectUtils.isEmpty(fbToken)) {
		
			if(ObjectUtils.isEmpty(googleToken)) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
				userDetails = (UserDetails) authentication.getPrincipal();
			} else {
				userDetails = googleLoginService.getUserDetailsForToken(googleToken);
			}
		} else {
			userDetails = facebookLoginService.getUserDetailsForToken(fbToken);
		}
		return "\""+ jwtService.creatJwtToken(userDetails) + "\"";
	}
}
