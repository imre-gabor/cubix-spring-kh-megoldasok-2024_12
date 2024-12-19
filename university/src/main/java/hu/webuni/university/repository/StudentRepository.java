package hu.webuni.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.university.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
