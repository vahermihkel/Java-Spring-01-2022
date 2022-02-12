import i18n from "i18next";
import { initReactI18next } from "react-i18next";

const resources = {
    EN: {
      translation: {
        "navbar": {
          "admin-link": "Administration view",
          "cart-link": "Cart"
        },
        "home": {
          "add-cart-button": "Add to cart"
        },
        "product": {
          "name": "Name",
          "model": "Model",
          "description": "Description",
          "price": "Price",
          "code": "Productcode",
          "category": "Category",
          "product-name": "Product name",
          "product-model": "Product model",
          "product-description": "Product description",
          "product-price": "Product price",
          "product-code": "Product code",
          "product-category": "Product category",
          "add-button": "Add product",
          "edit-button": "Edit product"
        },
        "admin": {
          "add-product": "Add product",
          "change-products": "Change products",
          "change-product-button": "Edit",
          "delete-product-button": "Delete"
        }
      }
    },
    EE: {
      translation: {
        "navbar": {
          "admin-link": "Administraatori vaatesse",
          "cart-link": "Ostukorv"
        },
        "home": {
          "add-cart-button": "Lisa ostukorvi"
        },
        "product": {
          "name": "Nimetus",
          "model": "Mudel",
          "description": "Kirjeldus",
          "price": "Hind",
          "code": "Tootekood",
          "category": "Kategooria",
          "product-name": "Toote nimetus",
          "product-model": "Toote mudel",
          "product-description": "Toote kirjeldus",
          "product-price": "Toote hind",
          "product-code": "Toote tootekood",
          "product-category": "Toote kategooria",
          "add-button": "Lisa toode",
          "edit-button": "Muuda toode"
        },
        "admin": {
          "add-product": "Lisa toode",
          "change-products": "Halda tooteid",
          "change-product-button": "Muuda",
          "delete-product-button": "Kustuta"
        }
      }
    },
    RU: {
      translation: {
        "navbar": {
          "admin-link": "RU Administraatori vaatesse",
          "cart-link": "RU Ostukorv"
        },
        "home": {
          "add-cart-button": "RU Lisa ostukorvi"
        },
        "product": {
          "name": "RU Nimetus",
          "model": "RU Mudel",
          "description": "RU Kirjeldus",
          "price": "RU Hind",
          "code": "RU Tootekood",
          "category": "RU Kategooria",
          "product-name": "RU Toote nimetus",
          "product-model": "RU Toote mudel",
          "product-description": "RU Toote kirjeldus",
          "product-price": "RU Toote hind",
          "product-code": "RU Toote tootekood",
          "product-category": "RU Toote kategooria",
          "add-button": "RU Lisa toode",
          "edit-button": "RU Muuda toode"
        },
        "admin": {
          "add-product": "RU Lisa toode",
          "change-products": "RU Halda tooteid",
          "change-product-button": "RU Muuda",
          "delete-product-button": "RU Kustuta"
        }
      }
    }
}

i18n
  .use(initReactI18next)
  .init({
    resources,
    lng: localStorage.getItem("language"), 
    fallbackLng: "EE",
    interpolation: {
      escapeValue: false 
    }
  });

export default i18n;