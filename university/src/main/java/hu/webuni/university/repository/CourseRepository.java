package hu.webuni.university.repository;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import hu.webuni.university.model.Course;
import hu.webuni.university.model.CourseStat;
import hu.webuni.university.model.QCourse;

public interface CourseRepository extends JpaRepository<Course, Integer>,
	QuerydslPredicateExecutor<Course>,
	QuerydslBinderCustomizer<QCourse>, 
	QuerydslWithEntitiyGrapRepository<Course, Integer> {

	@Override
	default void customize(QuerydslBindings bindings, QCourse course) {
		bindings.bind(course.name).first((path, value) -> path.startsWithIgnoreCase(value));
		
		bindings.bind(course.teachers.any().name).first((path, value) -> path.startsWithIgnoreCase(value));
		
		bindings.bind(course.students.any().semester).all((path, values) ->{
			if(values.size() != 2) {
				return Optional.empty();
			}
			
			Iterator<? extends Integer> it = values.iterator();
			Integer from = it.next();
			Integer to = it.next();
			return Optional.of(path.between(from, to));
		});
		
	}
	List<Course> findByName(String name);
	
	@Query("SELECT c.id as courseId, c.name as courseName, "
			+ "AVG(s.semester) as averageSemesterOfStudents "
			+ "FROM Course c LEFT JOIN c.students s "
			+ "GROUP BY c")
	List<CourseStat> getCourseStats();
}
