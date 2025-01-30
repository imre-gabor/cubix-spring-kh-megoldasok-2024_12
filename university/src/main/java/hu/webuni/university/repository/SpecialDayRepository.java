package hu.webuni.university.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.university.model.SpecialDay;

public interface SpecialDayRepository extends JpaRepository<SpecialDay, Integer>{

	@Query("SELECT s from SpecialDay s WHERE s.sourceDay BETWEEN :from and :until OR s.targetDay BETWEEN :from and :until")
	List<SpecialDay> findBySourceDayOrTargetDay(LocalDate from, LocalDate until);
}
