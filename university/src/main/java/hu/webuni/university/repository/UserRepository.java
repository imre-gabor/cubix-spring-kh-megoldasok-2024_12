package hu.webuni.university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.university.model.UniversityUser;

public interface UserRepository extends JpaRepository<UniversityUser, Integer>{

	Optional<UniversityUser> findByUsername(String username);

}
