package hu.webuni.university.jms;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.jms.ConnectionFactory;

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
	
	
	@Bean
    public ConnectionFactory financeConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        return connectionFactory;
    }
	
	@Bean
    public ConnectionFactory educationConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61617");
        return connectionFactory;
    }
	
	@Bean
    public JmsTemplate educationTemplate(ObjectMapper objectMapper) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(educationConnectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter(objectMapper));
        return jmsTemplate;
    }
	
	@Bean
	public JmsListenerContainerFactory<?> financeFactory(ConnectionFactory financeConnectionFactory, 
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		
		return setPubSubAndDurableSubscription(financeConnectionFactory, configurer, "university-app");
	}

	
	private JmsListenerContainerFactory<?> setPubSubAndDurableSubscription(
			ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer, 
			String clientId) {
		
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setClientId(clientId);
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		factory.setSubscriptionDurable(true);
		
		return factory;
	}
	
	@Bean
	public JmsListenerContainerFactory<?> educationFactory(ConnectionFactory educationConnectionFactory, 
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		
		return setPubSubAndDurableSubscription(educationConnectionFactory, configurer, "university-app");
	}
	
}
