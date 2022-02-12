import { useState } from 'react';
import Tabs from 'react-bootstrap/Tabs';
import Tab from 'react-bootstrap/Tab';

function ControlledTabs() {
  const [key, setKey] = useState('home');

  return (
    <Tabs
      id="controlled-tab-example"
      activeKey={key}
      onSelect={(k) => setKey(k)}
      className="mb-3"
    >
      <Tab eventKey="home" title="Kodu">
        <div>HOME</div>
      </Tab>
      <Tab eventKey="profile" title="Profiil">
        <div>PROFILE</div>
      </Tab>
      <Tab eventKey="taab" title="TAAB">
        <div>tab</div>
      </Tab>
      <Tab eventKey="contact" title="Kontakt" disabled>
        <div>CONTACT</div>
      </Tab>
    </Tabs>
  );
}

export default ControlledTabs;