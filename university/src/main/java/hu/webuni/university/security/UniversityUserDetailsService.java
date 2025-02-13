package hu.webuni.university.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.university.model.UniversityUser;
import hu.webuni.university.repository.UserRepository;

@Service
public class UniversityUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UniversityUser universityUser = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));
		
		return new User(username, universityUser.getPassword(), 
				Arrays.asList(new SimpleGrantedAuthority(universityUser.getUserType().toString())));
	}

}
