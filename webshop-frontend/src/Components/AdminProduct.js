import { Link } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import { useTranslation } from 'react-i18next';

function AdminProduct(props) {
    const { t } = useTranslation();

    function deleteHandler() {
        console.log(props.prod.code);
        props.onDeleteProduct(props.prod.code);
    }

    return (<div>
        <div>{props.prod.name}</div>
        <div>{props.prod.model}</div>
        <div>{props.prod.price}</div>
        <Link to={`/admin/muuda/${props.prod.code}`}>
            <Button variant='warning'>{t("admin.change-product-button")}</Button>
        </Link>
        <Button variant='danger' onClick={deleteHandler}>{t("admin.delete-product-button")}</Button>
    </div>)
}

export default AdminProduct;