package hu.webuni.university.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
//			.httpBasic()
//			.and()
			.csrf(
				csrf -> csrf.disable()
			)
			.sessionManagement(
					sc -> sc.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)			
			.authorizeHttpRequests(auth -> 
				auth.requestMatchers("/api/login/**").permitAll()
				.requestMatchers("/api/stomp/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/courses/**").hasAuthority("TEACHER")
				.requestMatchers(HttpMethod.PUT, "/api/courses/**").hasAuthority("TEACHER")
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.build()
			;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConf) throws Exception {
		return authConf.getAuthenticationManager();
	}
	
}
