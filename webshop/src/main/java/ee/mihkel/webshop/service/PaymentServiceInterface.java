package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.entity.Product;
import ee.mihkel.webshop.model.request.input.CartProduct;
import ee.mihkel.webshop.model.request.input.PaymentState;
import ee.mihkel.webshop.model.request.output.EveryPayData;
import ee.mihkel.webshop.model.request.output.EveryPayLink;
import ee.mihkel.webshop.model.request.output.EveryPayPaymentCheck;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PaymentServiceInterface {

    List<Product> getProductsFromDb(List<CartProduct> products) throws ExecutionException;

    double getOrderSum(List<Product> products);

    EveryPayLink getPaymentLinkFromEveryPay(double orderSum, Long orderId);

    PaymentState checkIfOrderPaid(EveryPayPaymentCheck everyPayPaymentCheck);
}
