import { useEffect, useRef, useState } from "react"; // REACTI HOOK

import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
// import Alert from "react-bootstrap/Alert";
import { ToastContainer, toast } from 'react-toastify';
import { useTranslation } from "react-i18next";

function AddProduct() {
    const nameRef = useRef(); 
    const priceRef = useRef();
    const categoryRef = useRef();
    const imgSrcRef = useRef();
    const descriptionRef = useRef();
    const barcodeRef = useRef();
    const { t } = useTranslation();
    // const [message, setMessage] = useState("");
    // const [warningMessage, setWarningMessage] = useState("");
    // const [errorMessage, setErrorMessage] = useState("");
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/categories")
        .then(res => res.json()) // {type: 'cors', url: 'http://localhost:8080/products', redirected: false, status: 200, ok: true, …}
        .then(body => {
            setCategories(body);
        }); // body
    },[]);


    function addToDatabase() {
        const newProduct = {
            name: nameRef.current.value,
            price: priceRef.current.value,
            category: {id: categoryRef.current.value},
            imgSrc: imgSrcRef.current.value,
            description: descriptionRef.current.value,
            barcode: barcodeRef.current.value,
            quantity: 0,
            active: false
        }
        fetch("http://localhost:8080/products",{
            method: "POST",
            body: JSON.stringify(newProduct),
            headers: {
                "Content-Type":"application/json"
            }
        }).then(res => {
            console.log(res);
            if (res.status === 201) {
                // setMessage("Toode edukalt lisatud!");
                // setErrorMessage("");
                toast.success("Toode edukalt lisatud!");
            } else {
                return res.json(); 
            }
        }) // responseEntity
        .then(data => {
            // if (data.message === "Mitteunikaalne ribakood") {
            //     setMessage("Lisatavale tootele pandi mitteunikaalne ribakood");
            // } else if (data.message === "Kõik nõutud väljad on täitmata") {
            //     setMessage(data.message);
            // }
            // setMessage("");
            // setErrorMessage(data.message);
            toast.error(data.message);
        }) // body
    }

    return ( // "Sõnum" ---> {message: "Sõnum", type: "warning"} className={message.type === "warning" ? "sad" : "" }
    <div>
        <ToastContainer />
        {/* {message && <Alert variant="success">{message}</Alert>}
        {errorMessage && <Alert variant="danger">{errorMessage}</Alert>} */}
        {/* <div className="successful-message">{message}</div> */}
        {/* <div>{warningMessage}</div> */}
        {/* <div className="error-message">{errorMessage}</div> */}
        <Form.Label>{t("product.name")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-name")} ref={nameRef} /> <br />
        <Form.Label>{t("product.price")}</Form.Label> <br />
        <Form.Control placeholder={t("product.product-price")} ref={priceRef} /> <br />
        <Form.Label>{t("product.category")}</Form.Label> <br />
        {/* <Form.Control placeholder={t("product.product-category")} ref={categoryRef} /> <br /> */}
        <Form.Select ref={categoryRef}>{ // Form. <--- BootStrapist ilus kujundus  ref <-- otseühendus mingi muutujaga
            categories.map(category => // <--- categories saime andmebaasist, andsime talle väärtust setCategories(body) - .map <--- asenda HTMLiga
                <option value={category.id}>{category.name}</option> ) // value= <--- mis väärtus läheb ref sisse
            }
        </Form.Select>
        <Form.Label>Pildi URL</Form.Label> <br />
        <Form.Control placeholder="Pildi URL aadress" ref={imgSrcRef} /> <br />
        <Form.Label>Kirjeldus</Form.Label> <br />
        <Form.Control placeholder="Toote pikem kirjeldus" ref={descriptionRef} /> <br />
        <Form.Label>Ribakood</Form.Label> <br />
        <Form.Control placeholder="Unikaalne ribakood" ref={barcodeRef} /> <br />
        <div className="center">
            <Button variant="dark" onClick={addToDatabase}>{t("product.add-button")}</Button>
        </div>
    </div>)
}

export default AddProduct;