package hu.webuni.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailService(PasswordEncoder passwordEncoder) {
		
		UserBuilder builder = User.builder();
		return new InMemoryUserDetailsManager(
				builder.username("user1")
				.password(passwordEncoder.encode("pass"))
				.authorities("user")
				.build(),
				builder.username("user2")
				.password(passwordEncoder.encode("pass"))
				.authorities("search")
				.build()
				);
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(
				csrf -> csrf.disable()
			)
			.sessionManagement(
					sc -> sc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)			
			.authorizeHttpRequests(auth -> 
				auth.requestMatchers("/api/login/**").permitAll()				
				.anyRequest().authenticated()
			)
			.build()
			;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConf) throws Exception {
		return authConf.getAuthenticationManager();
	}
	
}
