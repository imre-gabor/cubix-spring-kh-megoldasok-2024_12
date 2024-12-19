package hu.webuni.university.repository;


import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.Predicate;

import hu.webuni.university.model.Course;
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
	
}
