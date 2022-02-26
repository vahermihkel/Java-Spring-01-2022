package ee.mihkel.webshop.model.request.output;

import lombok.Data;

@Data
public class EveryPayPaymentCheck {
    private Long order_reference;
    private String payment_reference;
}
