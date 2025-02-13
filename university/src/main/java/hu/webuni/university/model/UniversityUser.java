package hu.webuni.university.model;

import java.time.LocalDate;

import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UniversityUser {
	
	public enum UserType {
		TEACHER, STUDENT;
	}

	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private int id;

	@ToString.Include
	private String name;
	
	private LocalDate birthdate;
	
	private String username;
	private String password;
	
	public abstract UserType getUserType();
}
