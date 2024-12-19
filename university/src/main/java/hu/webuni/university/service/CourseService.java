package hu.webuni.university.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.QCourse;
import hu.webuni.university.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	private final CourseRepository courseRepository;

	@Transactional
	public Iterable<Course> searchCourses(Predicate predicate, Pageable pageable) {
		
		//List<Course> courses = courseRepository.findAll(predicate, "Course.students", Sort.unsorted());
		//courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers", Sort.unsorted());
		List<Course> courses = courseRepository.findAll(predicate, pageable).getContent();
		BooleanExpression inByCourseId = QCourse.course.in(courses);
		
		courses = courseRepository.findAll(inByCourseId, "Course.students", Sort.unsorted());
		courses = courseRepository.findAll(inByCourseId, "Course.teachers", pageable.getSort());
		
		return courses;
	}
}
