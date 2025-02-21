package hu.webuni.eduservice.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import hu.webuni.eduservice.StudentXmlWs;
import hu.webuni.jms.dto.FreeSemesterRequest;
import hu.webuni.jms.dto.FreeSemesterResponse;
import jakarta.jms.Topic;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FreeSemesterRequestConsumer {

	private final StudentXmlWs studentXmlWs;
	private final JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "free_semester_requests")
	public void onMessage(Message<FreeSemesterRequest> message) {
		int studentId = message.getPayload().getStudentId();
		int freeSemesters = studentXmlWs.getNumFreeSemestersForStudent(studentId);
		
		FreeSemesterResponse response = new FreeSemesterResponse();
		response.setNumFreeSemesters(freeSemesters);
		response.setStudentId(studentId);
		
		jmsTemplate.convertAndSend(
			(Topic)message.getHeaders().get(JmsHeaders.REPLY_TO), response);
	}
}
