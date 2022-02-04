package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.model.request.input.EveryPayResponse;
import ee.mihkel.webshop.model.request.output.EveryPayData;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("payment")
    public EveryPayLink getPaymentLink(@RequestBody List<Product> products) {
        // R 12.15-15.45
        // 10.02 N 13.00-16.15

        // 1. List<Product> products
        // arvutame nende pealt kogusumma

        // mapToDouble
        //  [Product{price: 2.0}, Product{price: 3.0}]
        // [2.0,3.0].sum()

//        List<Product> productsFromDb = new ArrayList<>();
//        for (Product p: products) {
//            Product productFound = productRepository.findById(p.getId()).get();
//            productsFromDb.add(productFound);
//        }

        // orElse(null)
        List<Product> productsFromDb = products.stream() // avab streami
                .map(e -> productRepository.findById(e.getId()).get()) // muutmine - map() abil
                .collect(Collectors.toList()); // iga Stream peab omama lõpptulemist (tagastab Listi, summeerimise, average, true/false)


        double orderSum = productsFromDb.stream().mapToDouble(Product::getPrice).sum();

//        double sum2 = 0;
//        products.forEach(e -> sum2 += e.getPrice());

//        double sum = 0;
//        for (Product p: products) {
//            sum += p.getPrice();
//        }

        // 2. võtame igaühel ID, otsime ta andmebaasist
        // ja võtame andmebaasist leitud tootelt hinna

        // 3. teeme tellimuse Entity (andmebaasitabeli)
        // 4. teeme tellimuse Repository (andmebaasiühenduse)
        // 5. siit paneme andmebaasi
        // 6. pärast andmebaasi panemist, võtame ta andmebaasist uuesti,
        // ja võtame tema ID

        ZonedDateTime timeStamp = ZonedDateTime.now();

        EveryPayData everyPayData = new EveryPayData();
        everyPayData.setApi_username(everyPayUsername);
        everyPayData.setAccount_name(everyPayAccountname);
        everyPayData.setAmount(orderSum);
        everyPayData.setOrder_reference(4321L);
        everyPayData.setTimestamp(timeStamp.toString());
        everyPayData.setNonce(everyPayUsername + 4321L + timeStamp);
        everyPayData.setCustomer_url(customerurl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<EveryPayData> httpEntity = new HttpEntity<>(everyPayData, headers);

//        //mäluröövel
//        1. korduvad andmebaasipäringud
//        2. korduvad instantside loomised
//        3. korduvad muutujad deklaarerimine
//        4. korduvad while/for tüsklid
        // restTemplate tänu autowired -- @adnwdawnkawj
        // 1000x - @dad32wewqe
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EveryPayResponse> response =
                restTemplate.exchange(everyPayUrl, HttpMethod.POST,httpEntity, EveryPayResponse.class);
        System.out.println(response);
        System.out.println(response.getBody());

        // { payment_link: null }
        EveryPayLink link = new EveryPayLink();
        if (response.getBody() != null) {
                // EveryPayResponse(...., payment_link=https://igw-demo.every-pay.com/lp/wg984x/aUkIAv7A,....)
            link.setPayment_link(response.getBody().getPayment_link());
            // EveryPayLink { payment_link: "https://igw-demo.every-pay.com/lp/....." }
        }
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

    // Service - mis teeb musta tööd Controller läheb pikaks -> tõstan Servicesse loogikat
    // Util - mida taaskasutatakse -> teen isikukoodi kontrolli
}
