package ee.mihkel.webshop.model.request.input;

public enum PaymentState {
    initial, waiting_for_sca, sent_for_processing,
    waiting_for_3ds_response, settled, failed, abandoned,
    voided, refunded, chargebacked
}
