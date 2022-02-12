import { useState, useEffect, useRef } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useTranslation } from "react-i18next";

function EditProduct() {
    const productCode = window.location.href.split("muuda/")[1];

    const nameRef = useRef(); // Rolex
    const modelRef = useRef(); // T-112
    const descriptionRef = useRef();
    const priceRef = useRef();
    const codeRef = useRef();
    const categoryRef = useRef();
    const { t } = useTranslation();
    const [product, updateProduct] = useState(null);
    const [productsFromDb, updateProducts] = useState([]);
    const [buttonDisabled, setButtonDisabled] = useState(false);

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
            const product = products.find(prod => prod.code === productCode);
            updateProduct(product);
            updateProducts(products);
            // eslint-disable-next-line
        }), [] 
    );

    function editFromDatabase() {
        const index = productsFromDb.findIndex(element => element.code === productCode);
        const product = {
            name: nameRef.current.value,
            model: modelRef.current.value,
            description: descriptionRef.current.value,
            price: priceRef.current.value,
            code: codeRef.current.value,
            category: categoryRef.current.value,
        };
        productsFromDb[index] = product;
        // aadress kuhu tooted pannakse
        fetch("https://webshop-12-2021-default-rtdb.europe-west1.firebasedatabase.app/products.json",
            { 
                method: "PUT", 
                body: JSON.stringify(productsFromDb)
            }
        );
        updateProduct(product);
    }

    // läheb käima iga kord kui keyUp() ehk vajutus on lõpule jõudnud
    function checkCode() {
            // kood on 8 märki pikk JA praegune sisestatud kood ei võrdu productCode (mis saadi URLst)
            // siis lähen if sisse kontrollima kas kellelgi teisel on selline code
        if (codeRef.current.value.length === 8 && codeRef.current.value !== productCode) {
           
                            // [{...code: 12}, {..code:31}];
            const index = productsFromDb.findIndex(element => element.code === codeRef.current.value);
            if (index !== -1) {
                setButtonDisabled(true);
            } else {
                setButtonDisabled(false);
            }

        //    productsFromDb.forEach(element => {
        //        if (element.code === codeRef.current.value) {
        //             console.log("ON KELLEGAGI SAMA!!!")
        //        }
        //     });
        } else if (codeRef.current.value.length < 8) {
            setButtonDisabled(true);
        } else if (codeRef.current.value === productCode) {
            setButtonDisabled(false);
        }
    }

    return (
    <div>
       {product && <div>
        <div>      
                <div>{product.name}</div>
                <div>{product.model}</div>
                <div>{product.price}</div>
                <div>{product.description}</div>
                <div>{product.code}</div>
            </div>
            <div>
                <Form.Label>{t("product.name")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-name")} ref={nameRef} defaultValue={product.name} /> <br />
                <Form.Label>{t("product.model")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-model")} ref={modelRef} defaultValue={product.model} /> <br />
                <Form.Label>{t("product.description")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-description")} ref={descriptionRef} defaultValue={product.description} /> <br />
                <Form.Label>{t("product.price")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-price")} ref={priceRef} defaultValue={product.price} /> <br />
                <Form.Label>{t("product.code")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-code")} onChange={checkCode} minLength={8} maxLength={8} ref={codeRef} defaultValue={product.code} /> <br />
                <Form.Label>{t("product.category")}</Form.Label> <br />
                <Form.Control placeholder={t("product.product-category")} ref={categoryRef} defaultValue={product.category} /> <br />
                <div className="center">
                    <Button variant="dark" disabled={buttonDisabled} onClick={editFromDatabase}>{t("product.edit-button")}</Button>
                </div>
            </div>
       </div>}
       { !product && <div>Toodet ei leitud</div>}
    </div>
        )
}

export default EditProduct;