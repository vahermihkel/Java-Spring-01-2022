import './App.css';

function App() {

  function saadaBackendi() {
    const toode = {
        "name": "Apple",
        "price": 499.0,
        "quantity": 0,
        "store": null
    }

    fetch("http://localhost:8080/products", {
      method: "POST",
      body: JSON.stringify(toode),
      headers: {
        "Content-Type": "application/json"
      }
    });
  }

  return (
    <div>
        <button onClick={saadaBackendi}>Lisa backendi</button>
        <div>Tooted: </div>
    </div>
  );
}

export default App;
