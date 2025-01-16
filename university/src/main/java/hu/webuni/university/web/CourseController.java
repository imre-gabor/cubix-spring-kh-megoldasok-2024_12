package hu.webuni.university.web;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import hu.webuni.university.api.CourseControllerApi;
import hu.webuni.university.api.model.CourseDto;
import hu.webuni.university.api.model.HistoryDataCourseDto;
import hu.webuni.university.mapper.CourseMapper;
import hu.webuni.university.model.Course;
import hu.webuni.university.repository.CourseRepository;
import hu.webuni.university.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CourseController implements CourseControllerApi {

	private final CourseService courseService;
	private final CourseRepository courseRepository;
	
	private final CourseMapper courseMapper;
	private final NativeWebRequest request;
	private final MethodArgumentResolverHelper resolverHelper;

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(request);
	}

	@Override
	public ResponseEntity<CourseDto> createCourse(@Valid CourseDto courseDto) {
		Course course = courseRepository.save(courseMapper.dtoToCourse(courseDto));
		return ResponseEntity.ok(
				courseMapper.courseToDto(course)
				);
	}


	@Override
	public ResponseEntity<List<HistoryDataCourseDto>> getHistory(Integer id) {
		return ResponseEntity.ok(
			courseMapper.coursesHistoryToHistoryDataCourseDtos(courseService.getHistoryById(id))
		);
	}

	@Override
	public ResponseEntity<CourseDto> getVersionAt(Integer id, @NotNull @Valid OffsetDateTime at) {
		return ResponseEntity.ok(
				courseMapper.courseToDto(
					courseService.getVersionAt(id, at)
				)
			);
	}

	public void configPageable(@SortDefault("id") Pageable pageable) {}
	
	public void configurePredicate(@QuerydslPredicate(root = Course.class) Predicate predicate) {}
	
	@Override
	public ResponseEntity<List<CourseDto>> search(@Valid Boolean full, @Valid Integer page, @Valid Integer size,
			@Valid List<String> sort) {
		boolean isFull = full == null ? false : full;
		
		Pageable pageable = resolverHelper.createPageable(this.getClass(), "configPageable", request);

		Predicate predicate = resolverHelper.createPredicate(this.getClass(), "configurePredicate", request);
		if(isFull) {
			Iterable<Course> courses = courseService.searchCourses(
					predicate, 
					pageable);
			return ResponseEntity.ok(courseMapper.coursesToDtos(courses));
		} else {
			Iterable<Course> courses = courseRepository.findAll(predicate, pageable);
			return ResponseEntity.ok(courseMapper.courseSummariesToDtos(courses));
		}
	}
	
}
