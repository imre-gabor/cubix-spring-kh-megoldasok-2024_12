package hu.webuni.booking.web;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.bonus.api.BonusApi;
import hu.webuni.booking.dto.PurchaseData;
import hu.webuni.booking.dto.TicketData;
import hu.webuni.currency.api.CurrencyApi;
import hu.webuni.flights.api.FlightsApi;
import hu.webuni.flights.dto.Airline;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Value("${booking.bonus}")
    double bonusRate;
    
    @Autowired
    BonusApi bonusApi;
    @Autowired
    CurrencyApi currencyApi;
    @Autowired
    FlightsApi flightsApi;

    @PostMapping("/ticket")
    public PurchaseData buyTicket(@RequestBody TicketData ticketData) {
    	
    	PurchaseData purchaseData = new PurchaseData();

		try {
			List<Airline> flightsFound = flightsApi.searchFlight(ticketData.getFrom(), ticketData.getTo());
			if (flightsFound.isEmpty())
				return purchaseData;

			purchaseData.setSuccess(true);

			for (Airline flight : flightsFound) {
				String currency = flight.getCurrency();
				double price = flight.getPrice();
				if (!"USD".equals(currency)) {
					flight.setPrice(price * currencyApi.getRate(currency, "USD"));
				}
			}
			
			Airline cheapestFlight = flightsFound.stream().min(Comparator.comparing(Airline::getPrice)).get();
			double price = cheapestFlight.getPrice();

			String user = ticketData.getUser();
			if (ticketData.isUseBonus()) {
				double points = bonusApi.getPoints(user);
				if (points > 0.0) {
					double pointsUsed = price > points ? points : price;
					price -= pointsUsed;
					bonusApi.addPoints(user, -1 * pointsUsed);
					purchaseData.setBonusUsed(pointsUsed);
				}
			}

			purchaseData.setPrice(price);
			if (price > 0.0) {
				double bonusEarned = price * bonusRate;
				bonusApi.addPoints(user, bonusEarned);
				purchaseData.setBonusEarned(bonusEarned);
			}
		} catch (Exception e) {
			purchaseData.setSuccess(false);
			e.printStackTrace();
		}

		return purchaseData;
    }
}
