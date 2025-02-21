package hu.webuni.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import hu.webuni.bonus.api.BonusApi;
import hu.webuni.currency.api.CurrencyApi;
import hu.webuni.flights.api.FlightsApi;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {BonusApi.class, CurrencyApi.class, FlightsApi.class})
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

}
