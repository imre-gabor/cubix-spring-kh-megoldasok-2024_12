package hu.webuni.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.university.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

}
