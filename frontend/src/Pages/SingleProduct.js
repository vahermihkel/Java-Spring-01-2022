import { useState } from "react";

import { useTranslation } from 'react-i18next';

function SingleProduct() {
    
    const { t } = useTranslation();
    const [product, updateProduct] = useState(null);

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