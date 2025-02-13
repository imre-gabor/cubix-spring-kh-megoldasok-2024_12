package hu.webuni.university.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.hibernate.envers.Audited;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Audited
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Cacheable
public class Student extends UniversityUser {

	
	private int semester;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;
	
	private Integer eduId;
	private Integer numFreeSemesters;
	
	private int balance;
	@Override
	public UserType getUserType() {
		return UserType.STUDENT;
	}
}
