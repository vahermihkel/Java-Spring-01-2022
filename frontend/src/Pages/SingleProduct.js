import { useEffect, useState } from "react";
import { useParams } from "react-router"; // URL-st parameetrite võtmiseks

import { useTranslation } from 'react-i18next';

function SingleProduct() {
    const { id } = useParams(); // { id } tähistab App.js failis URL-s olevat :id kohta
    
    const { t } = useTranslation();
    const [product, updateProduct] = useState(null);

    useEffect(()=>{
        fetch("http://localhost:8080/products/" + id)
        .then(res => res.json())
        .then(data => updateProduct(data));
    },[]);

    return (
        <div>      
            { product && <div>
                <div>{product.name}</div>
                <div>{product.price}</div>
                <div>{product.category}</div>
                <div>{product.id}</div>
            </div>}
            { !product && <div>{t("product-not-found")}</div>}
        </div>)
}

export default SingleProduct;