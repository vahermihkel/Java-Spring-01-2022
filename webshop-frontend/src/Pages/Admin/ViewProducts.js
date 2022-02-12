import { useState, useEffect, useRef } from "react";
import AdminProduct from "../../Components/AdminProduct";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function ViewProducts() {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [products, updateProducts] = useState([]);
    const [originalProducts, updateOriginalProducts] = useState([]);
    const searchRef = useRef();

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
            updateOriginalProducts(products);
        }), []
    );

    // POST, PUT, DELETE, GET
    // POST - lisa üks juurde
    // PUT - lisa juurde millega asendad ära kõik mis juba olemas on
    // DELETE - kustutamiseks
    // GET - võtmiseks
    
    function deleteAllProductsFromDb() {
        fetch("https://webshop-12-2021-default-rtdb.europe-west1.firebasedatabase.app/products.json",
            { 
                method: "PUT", 
                body: JSON.stringify([])
            }
        );
        updateProducts([]);
    }

    function searchProduct() {
        let productsFound = [];
        originalProducts.forEach(product => {
            // ROLEX.indexOf(R)
            if (product.name.toUpperCase().indexOf(searchRef.current.value.toUpperCase()) > -1
                || product.code.toString().indexOf(searchRef.current.value) > -1 ) {
                productsFound.push(product);
            }
        })
        updateProducts(productsFound);
    }

    function deleteProduct(productCode) {
        const index = products.findIndex(product => product.code === productCode);
        console.log(index);
        products.splice(index,1);
        fetch("https://webshop-12-2021-default-rtdb.europe-west1.firebasedatabase.app/products.json",
            { 
                method: "PUT", 
                body: JSON.stringify(products)
            }
        );
        updateProducts(products.slice());
    }

    return (<div>
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
            <Modal.Title>Hoiatus</Modal.Title>
            </Modal.Header>
            <Modal.Body>Oled kustutamas kõiki tooteid andmebaasist!</Modal.Body>
            <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
                Katkesta
            </Button>
            <Button variant="primary" onClick={deleteAllProductsFromDb}>
                Jah kustuta kõik tooted
            </Button>
            </Modal.Footer>
        </Modal>
        <br />
        <Button variant="danger" onClick={handleShow}>Kustuta kõik tooted andmebaasist</Button>
        <br /><br />
        <input onKeyUp={searchProduct} ref={searchRef} type="text" />
        {products.map(product => <AdminProduct key={product.code} prod={product} onDeleteProduct={deleteProduct} />)}
    </div>)
}

export default ViewProducts;