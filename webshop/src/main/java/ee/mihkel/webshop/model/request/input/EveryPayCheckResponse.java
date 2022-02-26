package ee.mihkel.webshop.model.request.input;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class EveryPayCheckResponse {
    private String account_name;
    private String order_reference;
    private String email;
    private String customer_ip;
    private String customer_url;
    private ZonedDateTime payment_created_at;
    private BigDecimal initial_amount;
    private BigDecimal standing_amount;
    private String payment_reference;
    private String payment_link;
    private String api_username;
    private long stan;
    private long fraud_score;
    private PaymentState payment_state;
    private String payment_method;
    private CcDetails cc_details;
    private ZonedDateTime transaction_time;
    private ErrorObj processing_error;
}

@Data
class ErrorObj {
    private String code;
    private String last_four_digits;
}

@Data
class CcDetails {
    private String bin;
    private String last_four_digits;
    private String month;
    private String year;
    private String holder_name;
    private String type;
    private String issuer_country;
    private String issuer;
    private String cobrand;
    private String funding_source;
    private String product;
    private String state_3ds;
    private String authorisation_code;
}
