package hu.webuni.university.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.webuni.jms.dto.FreeSemesterResponse;
import hu.webuni.university.service.StudentService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FreeSemesterResponseConsumer {

	private final StudentService studentService;

	@JmsListener(destination = "free_semester_responses", containerFactory = "educationFactory")
	public void onFreeSemesterResponse(FreeSemesterResponse response) {
		studentService.updateFreeSemesters(response.getStudentId(), response.getNumFreeSemesters());
	}
}
