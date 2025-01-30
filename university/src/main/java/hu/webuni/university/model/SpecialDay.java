package hu.webuni.university.model;

import java.time.LocalDate;

import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Audited
public class SpecialDay {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private int id;
	private LocalDate sourceDay;
	private LocalDate targetDay;	//null means sourceDay is holiday
}
