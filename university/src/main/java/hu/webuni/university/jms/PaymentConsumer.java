package hu.webuni.university.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.webuni.university.finance.dto.PaymentDto;
import hu.webuni.university.service.StudentService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

	private final StudentService studentService;
	
	@JmsListener(destination = "payments", containerFactory = "myFactory")
	public void onPaymentMessaage(PaymentDto payment) {
		studentService.updateBalance(payment.getStudentId(), payment.getAmount());
	}
}
