package hu.webuni.university.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.university.api.StudentControllerApi;
import hu.webuni.university.mapper.StudentMapper;
import hu.webuni.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentController implements StudentControllerApi {
	
	private final StudentRepository studentRepository;

	private final StudentMapper studentMapper;

	

}
