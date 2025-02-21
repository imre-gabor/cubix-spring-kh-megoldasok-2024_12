package hu.webuni.bonus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.bonus.model.Bonus;

public interface BonusRepository extends JpaRepository<Bonus, String>{

}
