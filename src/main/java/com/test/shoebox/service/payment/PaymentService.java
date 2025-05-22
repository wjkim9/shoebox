package com.test.shoebox.service.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private IamportClient iamportClient;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    public IamportResponse<Payment> verifyPayment(String impUid, String merchantUid, BigDecimal amount) 
            throws IamportResponseException, IOException {
        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(impUid);

        if (payment.getResponse().getAmount().compareTo(amount) == 0 &&
                payment.getResponse().getMerchantUid().equals(merchantUid)) {
            return payment;
        } else {
            throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
        }
    }

    public IamportResponse<Payment> getPaymentStatus(String impUid) 
            throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(impUid);
    }
}
