package hu.webuni.university.finance.web;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.university.finance.dto.PaymentDto;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PaymentController {
	
	private final JmsTemplate jmsTemplate;

	@PostMapping("/api/payments")
	public void registerPayment(@RequestBody PaymentDto payment) {
		this.jmsTemplate.convertAndSend("payments", payment);
	}
}
