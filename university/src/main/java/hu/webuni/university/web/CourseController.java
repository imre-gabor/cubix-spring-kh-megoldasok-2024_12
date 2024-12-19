package hu.webuni.university.web;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import hu.webuni.university.dto.CourseDto;
import hu.webuni.university.mapper.CourseMapper;
import hu.webuni.university.model.Course;
import hu.webuni.university.repository.CourseRepository;
import hu.webuni.university.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private final CourseService courseService;
	private final CourseRepository courseRepository;
	
	private final CourseMapper courseMapper;
	
	@PostMapping
	public CourseDto createCourse(@RequestBody @Valid CourseDto courseDto) {
		Course course = courseRepository.save(courseMapper.dtoToCourse(courseDto));
		return courseMapper.courseToDto(course);
	}
	
	@GetMapping("/search")
	public List<CourseDto> searchCourses(@QuerydslPredicate(root = Course.class) Predicate predicate, 
			@RequestParam("full") Optional<Boolean> fullOpt, @SortDefault("id") Pageable pageable) {
		Boolean full = fullOpt.orElse(false);
		
		
//		return full 
//				? courseMapper.coursesToDtos(courseService.searchCourses(predicate))
//				: courseMapper.courseSummariesToDtos(courseRepository.findAll(predicate));
		
		return full 
				? courseMapper.coursesToDtos(courseService.searchCourses(predicate, pageable))
				: courseMapper.courseSummariesToDtos(courseRepository.findAll(predicate, pageable));
	}
	
}
