package hu.webuni.university.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import org.hibernate.envers.Audited;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Audited
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NamedEntityGraph(name="Course.students", attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(name="Course.teachers", attributeNodes = @NamedAttributeNode("teachers"))
public class Course {

	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private int id;

	@ToString.Include
	private String name;
	
	@ManyToMany
	private Set<Student> students;

	@ManyToMany
	private Set<Teacher> teachers;
	
	@OneToMany(mappedBy = "course")
	private Set<TimeTableItem> timeTableItems;
	
	private Semester semester;
	
	public void addTimeTableItem(TimeTableItem timeTableItem) {
		timeTableItem.setCourse(this);
		if(this.timeTableItems == null)
			this.timeTableItems = new HashSet<>();
		this.timeTableItems.add(timeTableItem);
	}
}
