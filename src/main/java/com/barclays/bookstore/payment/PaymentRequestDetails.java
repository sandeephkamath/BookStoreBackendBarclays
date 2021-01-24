package com.barclays.bookstore.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDetails {

    private boolean allow_repeated_payments;
    private double amount;
    private String buyer_name;
    private String purpose;
    private String redirect_url;
    private String phone;
    private boolean send_email;
    private String webhook;
    private boolean send_sms;
    private String email;

    public static PaymentRequestDetails getDefault(double amount) {
        return PaymentRequestDetails.builder()
            .allow_repeated_payments(true)
            .amount(amount)
            .buyer_name("John")
            .purpose("Book")
            .redirect_url("http://www.example.com/redirect/")
            .phone("9999999999")
            .send_sms(false)
            .send_email(false)
            .email("foo@example.com")
            .build();
    }

}
