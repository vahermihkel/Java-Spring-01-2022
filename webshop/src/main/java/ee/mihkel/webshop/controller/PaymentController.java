package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.request.input.EveryPayResponse;
import ee.mihkel.webshop.model.request.output.EveryPayData;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.Date;


@RestController
public class PaymentController {

    @Value("${everypay.url}")
    private String everyPayUrl;

    @Value("${everypay.username}")
    private String everyPayUsername;

    @Value("${everypay.accountname}")
    private String everyPayAccountname;

    @Value("${everypay.authorization}")
    private String authorization;

    @Value("${everypay.customerurl}")
    private String customerurl;


    @PostMapping("payment")
    public EveryPayLink getPaymentLink() {

        Instant now = Instant.now();
        ZonedDateTime timeStamp = ZonedDateTime.ofInstant(now, ZoneId.systemDefault());

        EveryPayData everyPayData = new EveryPayData();
        everyPayData.setApi_username(everyPayUsername);
        everyPayData.setAccount_name(everyPayAccountname);
        everyPayData.setAmount(300);
        everyPayData.setOrder_reference(4321L);
        everyPayData.setTimestamp(timeStamp);
        everyPayData.setNonce(everyPayUsername + 4321L + timeStamp);
        everyPayData.setCustomer_url(customerurl);

        System.out.println(everyPayData);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        System.out.println(authorization);
        System.out.println(headers);
        HttpEntity<EveryPayData> httpEntity = new HttpEntity<>(everyPayData, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EveryPayResponse> response =
                restTemplate.exchange(everyPayUrl, HttpMethod.POST,httpEntity, EveryPayResponse.class);
        System.out.println(response);
        System.out.println(response.getBody());

        EveryPayLink link = new EveryPayLink();
        link.setPayment_link("asdasdsa");
        return link;
    }

    // Bean abil funktsiooni
    // Configuration file
    // API päringuid

    // Saatmise põhja
    // Tegema valmis Body, Headerid, need kokku siduma
    // Päringu tegema Interneti

    // Response

    // Mõlema jaoks model: Body, Response
}
