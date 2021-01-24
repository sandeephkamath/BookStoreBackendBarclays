package com.barclays.bookstore.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentClient paymentClient;

    public PaymentResponse generatePaymentRequest(PaymentRequestDTO paymentRequestDTO) {
        return paymentClient.post(PaymentRequestDetails.getDefault(paymentRequestDTO.getAmount()));
    }

}
