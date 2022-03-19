package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.model.request.input.CartProduct;
import ee.mihkel.webshop.model.request.input.PaymentState;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import ee.mihkel.webshop.model.request.output.EveryPayPaymentCheck;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PersonRepository personRepository;

    @PostMapping("payment")
    public ResponseEntity<EveryPayLink> getPaymentLink(
                        @RequestBody List<CartProduct> products) throws ExecutionException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String personCode = personRepository.getPersonByEmail(email).getPersonCode();

        List<Product> productsFromDb = paymentService.getProductsFromDb(products);
        double orderSum = paymentService.getOrderSum(productsFromDb);

        Long orderId = paymentService.saveOrderToDb(orderSum,productsFromDb, personCode);

        return ResponseEntity.ok()
                .body(paymentService.getPaymentLinkFromEveryPay(orderSum,orderId));
    }

    @PostMapping("check-payment")               //{order_reference: 120051, payment_reference: 312ads}
    public ResponseEntity<PaymentState> checkPayment(@RequestBody EveryPayPaymentCheck everyPayPaymentCheck) {
        PaymentState paymentState = paymentService.checkIfOrderPaid(everyPayPaymentCheck);

        return ResponseEntity.ok()
                .body(paymentState);
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
