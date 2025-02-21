package hu.webuni.bonus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.bonus.model.Bonus;
import hu.webuni.bonus.repository.BonusRepository;

@Service
public class BonusService {

    @Autowired
    BonusRepository bonusRepository;

    @Transactional
    public double addPoints(String user, double pointsToAdd) {
        Bonus bonus = bonusRepository.findById(user)
                .orElseGet(() -> bonusRepository.save(new Bonus(user, 0.0)));

        if (-1 * pointsToAdd > bonus.getPoints())
            throw new IllegalArgumentException();
        bonus.setPoints(bonus.getPoints() + pointsToAdd);
        return bonus.getPoints();
    }
}
