package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.model.request.input.CartProduct;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import ee.mihkel.webshop.model.request.output.EveryPayPaymentCheck;
import ee.mihkel.webshop.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("payment/{personCode}")
    public ResponseEntity<EveryPayLink> getPaymentLink(
                        @RequestBody List<CartProduct> products,
                        @PathVariable String personCode) {

        List<Product> productsFromDb = paymentService.getProductsFromDb(products);
        double orderSum = paymentService.getOrderSum(productsFromDb);

        Long orderId = paymentService.saveOrderToDb(orderSum,productsFromDb, personCode);

        return ResponseEntity.ok()
                .body(paymentService.getPaymentLinkFromEveryPay(orderSum,orderId));
    }

    @PostMapping("check-payment")               //{order_reference: 120051, payment_reference: 312ads}
    public ResponseEntity<Boolean> checkPayment(@RequestBody EveryPayPaymentCheck everyPayPaymentCheck) {
        Boolean isPaid = paymentService.checkIfOrderPaid(everyPayPaymentCheck);

        return ResponseEntity.ok()
                .body(isPaid);
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
