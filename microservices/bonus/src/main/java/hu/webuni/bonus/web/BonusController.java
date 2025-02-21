package hu.webuni.bonus.web;

import hu.webuni.bonus.api.BonusApi;
import hu.webuni.bonus.model.Bonus;
import hu.webuni.bonus.repository.BonusRepository;
import hu.webuni.bonus.service.BonusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class BonusController implements BonusApi {

    @Autowired
    BonusRepository bonusRepository;
    
    @Autowired
    BonusService bonusService;
    
    @Override
    public double getPoints(String user) {
        return bonusRepository.findById(user)
                .orElseGet(Bonus::new)
                .getPoints();
    }
    
    @Override
    public double addPoints(String user, double pointsToAdd) {
        try {
            return bonusService.addPoints(user, pointsToAdd);
        } catch(IllegalArgumentException e) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
