package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import ee.mihkel.webshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("payment")
    public ResponseEntity<EveryPayLink> getPaymentLink(@RequestBody List<Product> products) {

        List<Product> productsFromDb = paymentService.getProductsFromDb(products);
        double orderSum = paymentService.getOrderSum(productsFromDb);

        Long orderId = paymentService.saveOrderToDb(orderSum,productsFromDb);

        return ResponseEntity.ok()
                .body(paymentService.getPaymentLinkFromEveryPay(orderSum,orderId));
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
