import { useEffect, useRef, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useTranslation } from "react-i18next";

function AddProduct() {
    const nameRef = useRef(); // Rolex
    const modelRef = useRef(); // T-112
    const descriptionRef = useRef();
    const priceRef = useRef();
    const codeRef = useRef();
    const categoryRef = useRef();
    const { t } = useTranslation();
    const [productsFromDb, updateProducts] = useState([]);
    const [buttonDisabled, setButtonDisabled] = useState(true);

    useEffect(() => 
        fetch("https://webshop-12-2021-default-rtdb.europe-west1.firebasedatabase.app/products.json")
        .then(response => {
            return response.json();
        })
        .then(object => {
            let products = [];
            for (const key in object) {
                products.push(object[key]);
            }
            updateProducts(products);
            // eslint-disable-next-line
        }), [] 
    );

    function addToDatabase() {
        const product = {
            name: nameRef.current.value,
            model: modelRef.current.value,
            description: descriptionRef.current.value,
            price: priceRef.current.value,
            code: codeRef.current.value,
            category: categoryRef.current.value,
        };
        // aadress kuhu tooted pannakse
        fetch("https://webshop-12-2021-default-rtdb.europe-west1.firebasedatabase.app/products.json",
            { 
                method: "POST", 
                body: JSON.stringify(product)
            }
        );
        nameRef.current.value = "";
    }

    function checkCode() {
        if (codeRef.current.value.length === 8) {
            // otsin kas on samasugune kood
            const index = productsFromDb.findIndex(element => element.code === codeRef.current.value);
            if (index !== -1) {
                setButtonDisabled(true);
            } else {
                setButtonDisabled(false);
            }
        } else {
            // ei saagi olla samasugust koodi - panen nupu kinni
            setButtonDisabled(true);
        }
    }

    return (
    <div>
        <Form.Label>{t("product.name")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-name")} ref={nameRef} /> <br />
        <Form.Label>{t("product.model")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-model")} ref={modelRef} /> <br />
        <Form.Label>{t("product.description")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-description")} ref={descriptionRef} /> <br />
        <Form.Label>{t("product.price")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-price")} ref={priceRef} /> <br />
        <Form.Label>{t("product.code")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-code")} onChange={checkCode} minLength={8} maxLength={8} ref={codeRef} /> <br />
        <Form.Label>{t("product.category")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-category")} ref={categoryRef} /> <br />
        <div className="center">
            <Button variant="dark" disabled={buttonDisabled} onClick={addToDatabase}>{t("product.add-button")}</Button>
        </div>
    </div>)
}

export default AddProduct;