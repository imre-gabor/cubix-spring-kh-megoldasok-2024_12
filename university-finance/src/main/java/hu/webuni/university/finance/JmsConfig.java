package hu.webuni.university.finance;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JmsConfig {

	@Bean
	public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
		
	@Configuration
	public class ArtemisConfig implements ArtemisConfigurationCustomizer {
	    @Override
	    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
	        // Allow Artemis to accept tcp connections (Default port localhost:61616)
	        configuration.addConnectorConfiguration("nettyConnector", new TransportConfiguration(NettyConnectorFactory.class.getName()));
	        configuration.addAcceptorConfiguration(new TransportConfiguration(NettyAcceptorFactory.class.getName()));
	    }
	}

}
