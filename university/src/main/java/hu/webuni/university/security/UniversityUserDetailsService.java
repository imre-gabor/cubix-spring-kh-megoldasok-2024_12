package hu.webuni.university.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.Student;
import hu.webuni.university.model.Teacher;
import hu.webuni.university.model.UniversityUser;
import hu.webuni.university.repository.UserRepository;

@Service
public class UniversityUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UniversityUser universityUser = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));
		
		return createUserDetails(universityUser);
	}

	public static UserDetails createUserDetails(UniversityUser universityUser) {
		Set<Course> courses = null;
		if(universityUser instanceof Teacher t) {
			courses = t.getCourses();
		} else if(universityUser instanceof Student s) {
			courses = s.getCourses();
		}
		
		return new UserInfo(universityUser.getUsername(), 
				universityUser.getPassword(), 
				Arrays.asList(new SimpleGrantedAuthority(universityUser.getUserType().toString())),
				courses == null ? Collections.emptyList() : courses.stream().map(Course::getId).toList()
				);
	}

}
