package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.entity.Order;
import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.model.request.input.EveryPayResponse;
import ee.mihkel.webshop.model.request.output.EveryPayData;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.ProductRepository;
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
import java.util.stream.Collectors;

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

    //        List<Product> productsFromDb = new ArrayList<>();
//        for (Product p: products) {
//            Product productFound = productRepository.findById(p.getId()).get();
//            productsFromDb.add(productFound);
//        }

    // orElse(null)
    // [Product{price: 1.0}, Product{price: 1.0}]
    // .map() --> // [Product{price: 2.0}, Product{price: 3.0}]


    public List<Product> getProductsFromDb(List<Product> products) {
        return products.stream() // avab streami
                .map(e -> productRepository.findById(e.getId()).get()) // muutmine - map() abil
                .collect(Collectors.toList()); // iga Stream peab omama lõpptulemist (tagastab Listi, summeerimise, average, true/false)

    }

    public double getOrderSum(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Long saveOrderToDb(double orderSum, List<Product> products) {
        Order order = new Order();
        order.setTimeStamp(new Date());
        order.setSum(orderSum);
        order.setOrderProducts(products);

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
                restTemplate.exchange(everyPayUrl, HttpMethod.POST,httpEntity, EveryPayResponse.class);

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

    // mapToDouble
    //  [Product{price: 2.0}, Product{price: 3.0}]
    // [2.0,3.0].sum()

    //        double sum2 = 0;
//        products.forEach(e -> sum2 += e.getPrice());

//        double sum = 0;
//        for (Product p: products) {
//            sum += p.getPrice();
//        }
}
