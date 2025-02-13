package hu.webuni.university.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

		messages.simpSubscribeDestMatchers("/topic/courseChat/{courseId}")
		.access("@courseChatGuard.checkCourseId(authentication, #courseId)");
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}

	
}
