package hu.webuni.bonus.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="bonus", url = "${feign.bonus.url}")
public interface BonusApi {

    @GetMapping("/bonus/{user}")
    double getPoints(@PathVariable("user") String user);

    @PutMapping("/bonus/{user}")
    double addPoints(@PathVariable("user") String user, @RequestBody double pointsToAdd);

}