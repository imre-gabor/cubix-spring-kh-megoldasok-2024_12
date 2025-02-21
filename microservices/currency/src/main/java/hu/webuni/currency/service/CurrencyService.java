package hu.webuni.currency.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class CurrencyService {
    
    @Value("${currency.exchangePremium}")
    private double exchangePremium;
    
	private Map<RateDescriptor, Double> rates;

	@PostConstruct
	public void init() {
	    rates = Map.of(
	            new RateDescriptor("HUF", "EUR"), 1.0 / 370,
	            new RateDescriptor("EUR", "HUF"), 370.0*(1 + exchangePremium),
	            new RateDescriptor("HUF", "USD"), 1.0 / 340,
	            new RateDescriptor("USD", "HUF"), 340.0*(1 + exchangePremium),
                new RateDescriptor("USD", "EUR"), 1.0 / 1.1,
                new RateDescriptor("EUR", "USD"), 1.1 * (1 + exchangePremium));
	}
	
	public double getRate(RateDescriptor rd) {
		return rates.get(rd);
	}

}
