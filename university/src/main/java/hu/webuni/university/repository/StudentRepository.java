package hu.webuni.university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.university.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	Optional<Student> findByEduId(int eduId);

}
