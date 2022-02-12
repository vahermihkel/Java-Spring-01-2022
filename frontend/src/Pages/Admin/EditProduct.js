import { useState, useRef } from "react";
import { useParams } from "react-router";

import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useTranslation } from "react-i18next";
import { useEffect } from "react";

function EditProduct() {
    const { id } = useParams();

    const nameRef = useRef();
    const priceRef = useRef();
    const categoryRef = useRef();
    const { t } = useTranslation();
    const [product, updateProduct] = useState(null);

    useEffect(()=>{
        fetch("http://localhost:8080/products/" + id)
        .then(res => res.json())
        .then(data => updateProduct(data));
    },[]);

    // !!! ID on vaja ka kaasa saata
    

    return (
    <div>
       {product && <div>
            <div>  
                <div>{product.name}</div>
                <div>{product.price}</div>
                <div>{product.category}</div>
            </div>
            <div>
                <Form.Label>{t("product.name")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-name")} ref={nameRef} defaultValue={product.name} /> <br />
                <Form.Label>{t("product.price")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-price")} ref={priceRef} defaultValue={product.price} /> <br />
                <Form.Label>{t("product.category")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-category")} ref={categoryRef} defaultValue={product.category} /> <br />
                <div className="center">
                    <Button variant="dark">{t("product.edit-button")}</Button>
                </div>
            </div>
       </div>}
       { !product && <div>Toodet ei leitud</div>}
    </div>
        )
}

export default EditProduct;