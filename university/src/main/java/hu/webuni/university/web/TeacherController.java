package hu.webuni.university.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.university.dto.TeacherDto;
import hu.webuni.university.mapper.TeacherMapper;
import hu.webuni.university.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	
	private final TeacherRepository teacherRepository;

	private final TeacherMapper teacherMapper;

	@GetMapping("/{id}")
	public TeacherDto findById(@PathVariable("id") int id) {
		return teacherMapper.teacherToDto(teacherRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

}
