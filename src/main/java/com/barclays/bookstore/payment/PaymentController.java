package com.barclays.bookstore.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(("/payment"))
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping()
    public PaymentResponse pay(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        return paymentService.generatePaymentRequest(paymentRequestDTO);
    }

}
