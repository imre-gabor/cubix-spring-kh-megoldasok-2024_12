package hu.webuni.university.xmlws;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
	
	private final Bus bus;
	private final TimetableWs timetableWs;

	@Bean
	public Endpoint endPoint() {
		EndpointImpl endpointImpl = new EndpointImpl(bus, timetableWs);
		endpointImpl.publish("/timetable");
		return endpointImpl;
	}
	
}
