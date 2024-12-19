package hu.webuni.university.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.university.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
