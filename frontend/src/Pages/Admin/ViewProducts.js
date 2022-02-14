import { useEffect, useState, useRef } from "react";
import { Link } from "react-router-dom";

import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function ViewProducts() {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [products, updateProducts] = useState([]);
    const [originalProducts, updateOriginalProducts] = useState([]);
    const searchRef = useRef();
    
    useEffect(() => {
        fetch("http://localhost:8080/products")
        .then(res => res.json()) // {type: 'cors', url: 'http://localhost:8080/products', redirected: false, status: 200, ok: true, …}
        .then(data => {
            updateProducts(data)
            updateOriginalProducts(data);
        }); // body
    },[]);
    
    function searchProduct() {
        let productsFound = [];
        originalProducts.forEach(product => {
            if (product.name.toUpperCase().indexOf(searchRef.current.value.toUpperCase()) > -1
                || product.barcode.toString().indexOf(searchRef.current.value) > -1 ) {
                productsFound.push(product);
            }
        })
        updateProducts(productsFound);
    }

    function onDeleteProduct(product) {
        fetch("http://localhost:8080/products/" + product.id, {
            method: "DELETE"
        })
        .then(res => res.json()) 
        .then(data => {
            updateProducts(data)
            updateOriginalProducts(data);
        });
    }

    function onIncreaseQuantity(product) {
        fetch("http://localhost:8080/increase-quantity",{
            method: "PATCH",
            body: JSON.stringify(product),
            headers: {
                "Content-Type":"application/json"
            }
        }).then(res=> {
            if (res.status === 200) {
                fetch("http://localhost:8080/products")
                .then(res => res.json()) // {type: 'cors', url: 'http://localhost:8080/products', redirected: false, status: 200, ok: true, …}
                .then(data => {
                    updateProducts(data)
                    updateOriginalProducts(data);
                }); // body
            } 
        })

        // .then(res=> {
        //     if (res.status == 200) {
        //         const index = products.indexOf(product);
        //         product.quantity++;
        //         products[index] = product;
        //         updateProducts(products.slice()); // slice on mälukoha katkestamine
        //         updateOriginalProducts(products.slice()); // slice abil on täiesti uus muutuja
                                                    // millel on samad väärtused
        //             üleval olev kuju: const [products, updateProducts] = useState([]);
        //     } 
        // })
    }



    function onDecreaseQuantity(product) {
        if (product.quantity > 0) {
            fetch("http://localhost:8080/decrease-quantity",{
                method: "PATCH",
                body: JSON.stringify(product),
                headers: {
                    "Content-Type":"application/json"
                }
            }).then(res=> {
                if (res.status === 200) {
                    fetch("http://localhost:8080/products")
                    .then(res => res.json()) // {type: 'cors', url: 'http://localhost:8080/products', redirected: false, status: 200, ok: true, …}
                    .then(data => {
                        updateProducts(data)
                        updateOriginalProducts(data);
                    }); // body
                } 
            })
        }
    }

    return (<div>
        <h2 className="mb-4">Products</h2>
        <input onKeyUp={searchProduct} ref={searchRef} type="text" />
  <table className="table table-hover table-bordered">
    <thead>
    <tr>
      <th scope="col">Product</th>
      <th scope="col">Price (€)</th>
      <th scope="col">Quantity (pcs)</th>
      <th scope="col">Category</th>
      <th scope="col">Actions</th>
    </tr>
    </thead>
    <tbody>
    {products.map(product => <tr>
      <td>{product.name}</td>
      <td>{product.price}</td>
      <td>{product.quantity}</td>
      <td>{product.category}</td>
      <td>
          <Button onClick={() => onDeleteProduct(product)} variant="danger">X</Button>
          <Link to={"/admin/muuda/" + product.id}>
            <Button variant="warning">Edit</Button>
          </Link>
          <Button onClick={() => onIncreaseQuantity(product)} variant="success">Pcs ++</Button>
          <Button hidden={product.quantity < 1} onClick={() => onDecreaseQuantity(product)}>Pcs --</Button>
      </td>
    </tr>)}

    
    </tbody>
  </table>
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
            <Modal.Title>Hoiatus</Modal.Title>
            </Modal.Header>
            <Modal.Body>Oled kustutamas kõiki tooteid andmebaasist!</Modal.Body>
            <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
                Katkesta
            </Button>
            <Button variant="primary">
                Jah kustuta kõik tooted
            </Button>
            </Modal.Footer>
        </Modal>
        <br />
        <Button variant="danger" onClick={handleShow}>Kustuta kõik tooted andmebaasist</Button>
        <br /><br />
    </div>)
}

export default ViewProducts;

// 11:35