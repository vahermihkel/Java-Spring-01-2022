import { useState, useEffect } from "react";
import Accordion from 'react-bootstrap/Accordion';

function SingleProduct() {
    const productCode = window.location.href.split("toode/")[1];
    console.log(productCode);

    const [product, updateProduct] = useState(null);

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
            // eslint-disable-next-line
        }), [] 
    );

    return (
        <div>      
            { product && <div>
                <div>{product.name}</div>
                <div>{product.model}</div>
                <div>{product.price}</div>
                <div>{product.description}</div>
                <div>{product.code}</div>
                <Accordion defaultActiveKey="0">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>Omadused</Accordion.Header>
                        <Accordion.Body>
                            {product.description}
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Täiendav info</Accordion.Header>
                        <Accordion.Body>
                            {product.model}
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
            </div>}
            { !product && <div>Toodet ei leitud</div>}
        </div>)
}

export default SingleProduct;