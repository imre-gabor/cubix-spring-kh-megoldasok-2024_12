package hu.webuni.university.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo extends User {
	
	private List<Integer> courseIds;

	public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities, List<Integer> courseIds) {
		super(username, password, authorities);
		this.courseIds = courseIds;
	}

}
