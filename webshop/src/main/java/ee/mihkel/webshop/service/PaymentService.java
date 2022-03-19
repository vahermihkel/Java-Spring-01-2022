package ee.mihkel.webshop.service;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.model.entity.Order;
import ee.mihkel.webshop.model.entity.Person;
import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.model.request.input.CartProduct;
import ee.mihkel.webshop.model.request.input.EveryPayCheckResponse;
import ee.mihkel.webshop.model.request.input.EveryPayResponse;
import ee.mihkel.webshop.model.request.input.PaymentState;
import ee.mihkel.webshop.model.request.output.EveryPayData;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import ee.mihkel.webshop.model.request.output.EveryPayPaymentCheck;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PaymentService {

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

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductCache productCache;

    //        List<Product> productsFromDb = new ArrayList<>();
//        for (Product p: products) {
//            Product productFound = productRepository.findById(p.getId()).get();
//            productsFromDb.add(productFound);
//        }

    // orElse(null)
    // [Product{price: 1.0}, Product{price: 1.0}]
    // .map() --> // [Product{price: 2.0}, Product{price: 3.0}]


    public List<Product> getProductsFromDb(List<CartProduct> products) throws ExecutionException {
//        log.info(products.get(0).getCartProduct().getId());
//        return products.stream() // avab streami
//                .map(e -> productRepository.findById(e.getCartProduct().getId()).get()) // muutmine - map() abil
//                .collect(Collectors.toList()); // iga Stream peab omama lõpptulemist (tagastab Listi, summeerimise, average, true/false)
            List<Long> ids = products.stream()    // stream avab listi, et seda efektiivselt manipuleerida
                    .map(e -> e.getCartProduct().getId()) // võtab iga elemendi ja asendab selle parempoolse osaga
                    .collect(Collectors.toList()); // pannakse Stream kinni --- listiks, kogusumma, boolean
            return productCache.getProducts(ids);
    }

    public double getOrderSum(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Long saveOrderToDb(double orderSum, List<Product> products, String personCode) {
        Order order = new Order();
        order.setTimeStamp(new Date());
        order.setSum(orderSum);
        order.setOrderProducts(products);
        Person person = personRepository.findById(personCode).get();
        order.setPerson(person);

        Order updatedOrder = orderRepository.save(order);
        return updatedOrder.getId();
    }

    public EveryPayLink getPaymentLinkFromEveryPay(double orderSum, Long orderId) {
        EveryPayData everyPayData = setEveryPayData(orderSum, orderId);

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
                restTemplate.exchange(everyPayUrl + "oneoff", HttpMethod.POST,httpEntity, EveryPayResponse.class);

        // { payment_link: null }
        EveryPayLink link = new EveryPayLink();
        if (response.getBody() != null) {
            // EveryPayResponse(...., payment_link=https://igw-demo.every-pay.com/lp/wg984x/aUkIAv7A,....)
            link.setPayment_link(response.getBody().getPayment_link());
            // EveryPayLink { payment_link: "https://igw-demo.every-pay.com/lp/....." }
        }
        return link;
    }

    private EveryPayData setEveryPayData(double orderSum, Long orderId) {
        ZonedDateTime timeStamp = ZonedDateTime.now();

        EveryPayData everyPayData = new EveryPayData();
        everyPayData.setApi_username(everyPayUsername);
        everyPayData.setAccount_name(everyPayAccountname);
        everyPayData.setAmount(orderSum);
        everyPayData.setOrder_reference(orderId);
        everyPayData.setTimestamp(timeStamp.toString());
        everyPayData.setNonce(everyPayUsername + orderId + timeStamp);
        everyPayData.setCustomer_url(customerurl);
        return everyPayData;
    }

    public PaymentState checkIfOrderPaid(EveryPayPaymentCheck everyPayPaymentCheck) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<EveryPayData> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<EveryPayCheckResponse> response =
                restTemplate.exchange(
                        everyPayUrl + everyPayPaymentCheck.getPayment_reference() + "?api_username=" + everyPayUsername,
                        HttpMethod.GET,httpEntity, EveryPayCheckResponse.class);
        if (response.getBody() != null) {
            PaymentState paymentState = response.getBody().getPayment_state();
            Order order = orderRepository.getById(everyPayPaymentCheck.getOrder_reference());
            if (paymentState == PaymentState.failed ||
                    paymentState == PaymentState.abandoned ||
                    paymentState == PaymentState.voided) {
                order.setPaid(false);
            } else if (paymentState == PaymentState.settled) {
                order.setPaid(true);
            }
//            order.setPaid(paymentState);
            orderRepository.save(order);
            return paymentState;
        }
        return null;
    }

    // mapToDouble
    //  [Product{price: 2.0}, Product{price: 3.0}]
    // [2.0,3.0].sum()

    //        double sum2 = 0;
//        products.forEach(e -> sum2 += e.getPrice());

//        double sum = 0;
//        for (Product p: products) {
//            sum += p.getPrice();
//        }


    // meid suunatakse pärast makse teostamist VÕI
    // makse ebaõnnestumist everypay'sse saadetud lingile
    // mihkelmihkel.heroku.app/tellimus

    // sinna lingile paneb EveryPay kaasa
    // order_reference: 120051
    // payment_reference: asdjna12312nkejanej23n13

    // mihkelmihkel.heroku.app/tellimus?order_reference=120051&payment_reference=asdjna12312nkejanej23n13
    // localhost:3000/tellimus --- frontendis toimub kontroll, kas on olemas order_reference ja payment_reference
    // siis tee backendi päring localhost:8080/check-payment @RequestBody - {order_reference: 120051, payment_reference: 312ads}
    // teeme uue päringu EveryPaysse --- https://igw-demo.every-pay.com/api/v4/payments/check-payment POST 312ads
    // TOIMUS MAKSE / EI TOIMINUD MAKSE ---
    //
    // 1.otsin üles orderi 120051 ja muudan ta UNPAID / PAID staatusesse
    // 2.KUI TOIMIS, vähendan selle Producti Quantity't.

    // hiljem MAKSTUD tellimuste nägemine isikupõhiselt
    // vahemälu / cache
}
