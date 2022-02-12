import { Link } from "react-router-dom";
import Button from "react-bootstrap/Button"
import { useTranslation } from 'react-i18next';
import ControlledTabs from "../../Components/ControlledTabs";

function AdminHome() {
    const { t } = useTranslation();

    return (
    <div>
        <Link to="/admin/lisa-toode">
            <Button variant="dark" className="admin-button">{t("admin.add-product")}</Button>
        </Link>
        <Link to="/admin/tooted">
            <Button variant="dark" className="admin-button">{t("admin.change-products")}</Button>
        </Link>
        <br/><br/>
        <ControlledTabs />
    </div>)
}

export default AdminHome;