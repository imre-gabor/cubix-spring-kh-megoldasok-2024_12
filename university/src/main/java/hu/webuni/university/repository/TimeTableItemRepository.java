package hu.webuni.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.university.model.Semester.SemesterType;
import hu.webuni.university.model.TimeTableItem;

public interface TimeTableItemRepository extends JpaRepository<TimeTableItem, Integer> {

	@Query("SELECT t FROM TimeTableItem t WHERE t.course IN ("
			+	"SELECT c FROM Course c JOIN c.students s "
			+ 	"WHERE s.id=:studentId AND c.semester.year = :year AND c.semester.semesterType = :semesterType"
			+ ")")
	public List<TimeTableItem> findByStudentAndSemester(int studentId, int year, SemesterType semesterType);
}
