package hu.webuni.university.ws;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import hu.webuni.university.security.UserInfo;

@Component
public class CourseChatGuard {
	
	public boolean checkCourseId(Authentication authentication, int courseId) {
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		return userInfo.getCourseIds().contains(courseId);
	}

}
