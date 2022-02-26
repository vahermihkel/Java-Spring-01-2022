package ee.mihkel.webshop.model.request.input;

import ee.mihkel.webshop.model.entity.Product;
import lombok.Data;

@Data // getterid, setterid, noargsconstructori kõikidele omadustele
public class CartProduct {
    private Product cartProduct;
    private int quantity;
}
