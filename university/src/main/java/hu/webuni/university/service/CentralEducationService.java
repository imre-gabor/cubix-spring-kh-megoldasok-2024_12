package hu.webuni.university.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import hu.webuni.eduservice.wsclient.StudentXmlWsImplService;
import hu.webuni.jms.dto.FreeSemesterRequest;
import hu.webuni.university.aspect.Retry;
import jakarta.jms.Destination;

@Service
@Retry(times = 2)
public class CentralEducationService {

	private Random random = new Random();
	@Autowired
	private JmsTemplate educationJmsTemplate;
	

	public int getNumFreeSemestersForStudent(int eduId) {
		int rnd = random.nextInt(0, 2);
		if (rnd == 0) {
			throw new RuntimeException("Central Education Service timed out.");
		} else {
			return new StudentXmlWsImplService()
					.getStudentXmlWsImplPort()
					.getNumFreeSemestersForStudent(eduId);
		}
	}

	public void askNumFreeSemestersForStudent(int eduId) {
		FreeSemesterRequest freeSemesterRequest = new FreeSemesterRequest();
		freeSemesterRequest.setStudentId(eduId);
		 
		Destination topic = educationJmsTemplate.execute(session ->
		session.createTopic("free_semester_responses"));
		
		educationJmsTemplate.convertAndSend("free_semester_requests",
			freeSemesterRequest,
			message -> {
				message.setJMSReplyTo(topic);
				return message;
			});
	}
}
