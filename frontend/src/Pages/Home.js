import { useEffect, useState } from "react";

import Button from "react-bootstrap/Button";
import { useTranslation } from 'react-i18next';

import Product from "../Components/Product";


function Home() {
    const { t } = useTranslation();
    const [products, updateProducts] = useState([]); // useState -- uuendab lehte

    useEffect(() => {
        fetch("https://mihkeljava.herokuapp.com/products")
        .then(res => res.json()) // {type: 'cors', url: 'http://localhost:8080/products', redirected: false, status: 200, ok: true, …}
        .then(data => updateProducts(data)); // body
    },[]);
    
    function sortNameAsc() {
        products.sort((a, b) => a.name.localeCompare(b.name));
        updateProducts(products.slice());
    }

    function sortNameDesc() {
        products.sort((a, b) => b.name.localeCompare(a.name));
        updateProducts(products.slice());
    }

    function sortPriceAsc() {
        products.sort((a, b) => a.price - b.price);
        updateProducts(products.slice());
    }

    function sortPriceDesc() {
        products.sort((a, b) => b.price - a.price);
        updateProducts(products.slice());
    }

    return (
    <div>
        <div className="App">
        </div> <br/><br/>
       { products.length > 0 && <div>
        <Button onClick={sortNameAsc}>{t("home.sort-az")}</Button>
        <Button onClick={sortNameDesc}>{t("home.sort-za")}</Button>
        <Button onClick={sortPriceAsc}>{t("home.sort-price-asc")}</Button>
        <Button onClick={sortPriceDesc}>{t("home.sort-price-desc")}</Button>
        </div>}
        {products.map(product => {return product.quantity > 0 && <Product key={product.id} prod={product} />})}
    </div>)
}

export default Home;