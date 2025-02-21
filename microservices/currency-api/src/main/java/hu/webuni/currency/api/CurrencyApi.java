package hu.webuni.currency.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="currency", url = "${feign.currency.url}")
public interface CurrencyApi {

  @GetMapping("/rate/{from}/{to}")
  double getRate(@PathVariable("from") String from, @PathVariable("to") String to);

}