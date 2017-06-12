package net.vivialconnect.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.connector.Connector;
import net.vivialconnect.model.connector.Callback;
import net.vivialconnect.model.connector.PhoneNumber;
import net.vivialconnect.model.connector.ConnectorWithCallbacks;
import net.vivialconnect.model.connector.ConnectorWithPhoneNumbers;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectorTest extends BaseTestCase {

    @Test
    public void test_a_create_connector() throws VivialConnectException {
        Connector connector = getDataSource().createConnector();
        connector.setName("ConnectorTest");
        assertTrue(connector.getId() > 0);
    }

    @Test
    public void test_b_get_connectors() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        assertTrue(connectors.size() > 0);
    }

    @Test
    public void test_c_connector_count() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        assertEquals(getDataSource().connectorCount(), connectors.size());
    }

    @Test
    public void test_d_get_connector_by_id() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);
        Connector lookedUpConnector = getDataSource().getConnectorById(connector.getId());
        assertEquals(connector.getId(), lookedUpConnector.getId());
    }

    @Test
    public void test_e_update_connector() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);
        connector.setName("RenamedConnector");
        Connector updatedConnector = getDataSource().updateConnector(connector);
        assertEquals(updatedConnector.getName(), "RenamedConnector");
    }

    @Test
    public void test_f_add_callback() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);

        Callback callback = new Callback();
        callback.setEventType("status");
        callback.setMethod("POST");
        callback.setMessageType("text");
        callback.setUrl("http://bfaa1a06.ngrok.io");

        connector.addCallback(callback);
        Connector connWithCallbacks = (Connector)getDataSource().createCallbacks(connector);
        assertEquals(connector.getId(), connWithCallbacks.getId());
        List<Callback> callbacks = connWithCallbacks.getCallbacks();
        assertTrue(callbacks.size() > 0);
    }

    @Test
    public void test_g_update_callback() throws VivialConnectException, InterruptedException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);

        List<Callback> callbacks = connector.getCallbacks();
        assertEquals(callbacks.size(), 1);
        Callback callback = callbacks.get(0);
        callback.setUrl("http://some.other.url");

        Connector connWithCallbacks = (Connector)getDataSource().updateCallbacks(connector);
        assertEquals(connector.getId(), connWithCallbacks.getId());
        assertEquals(connWithCallbacks.getCallbacks().get(0).getUrl(), "http://some.other.url");

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test_h_deleting_callbacks() throws VivialConnectException, InterruptedException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);

        List<Callback> callbacks = connector.getCallbacks();
        assertEquals(callbacks.size(), 1);

        Callback newCallback = new Callback();
        newCallback.setEventType("incoming");
        newCallback.setMethod("POST");
        newCallback.setMessageType("text");
        connector.addCallback(newCallback);

        newCallback = new Callback();
        newCallback.setEventType("incoming_fallback");
        newCallback.setMethod("POST");
        newCallback.setMessageType("text");
        connector.addCallback(newCallback);

        Connector connWithCallbacks = (Connector)getDataSource().updateCallbacks(connector);

        callbacks = connWithCallbacks.getCallbacks();
        List<Callback> altCallbacks = getDataSource().getCallbacks(connWithCallbacks.getId()).getCallbacks();
        assertEquals(callbacks.size(), 3);
        assertEquals(altCallbacks.size(), 3);
        for (int i = 0; i < callbacks.size(); i++) {
            assertEquals(callbacks.get(i).getDateCreated(), altCallbacks.get(i).getDateCreated());
            assertEquals(callbacks.get(i).getMessageType(), altCallbacks.get(i).getMessageType());
            assertEquals(callbacks.get(i).getUrl(), altCallbacks.get(i).getUrl());
            assertEquals(callbacks.get(i).getMethod(), altCallbacks.get(i).getMethod());
            assertEquals(callbacks.get(i).getEventType(), altCallbacks.get(i).getEventType());
        }

        connWithCallbacks = (Connector)getDataSource().deleteSingleCallback(connector, callbacks.get(2));
        callbacks = connWithCallbacks.getCallbacks();
        assertEquals(callbacks.size(), 2);

        List<Callback> toDelete = new ArrayList<Callback>();
        toDelete.add(callbacks.get(1));
        connWithCallbacks = (Connector)getDataSource().deleteCallbacks(connector, toDelete);
        callbacks = connWithCallbacks.getCallbacks();
        assertEquals(callbacks.size(), 1);

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test_i_delete_all_callbacks() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);
        List<Callback> callbacks = connector.getCallbacks();
        assertTrue(callbacks.size() > 0);

        ConnectorWithCallbacks updatedConnector = getDataSource().deleteAllCallbacks(connector);

        callbacks = updatedConnector.getCallbacks();
        assertEquals(callbacks.size(), 0);
    }

    @Test
    public void test_j_associate_phone_number() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);

        List<AssociatedNumber> associatedNumbers = getDataSource().getAssociatedNumbers();
        // Should have at least two associated numbers for this test
        assertTrue(associatedNumbers.size() >= 2);
        AssociatedNumber lastNumber = associatedNumbers.get(associatedNumbers.size() - 1);

        PhoneNumber number = new PhoneNumber(lastNumber.getId(), lastNumber.getPhoneNumber());

        connector.addPhoneNumber(number);
        Connector connWithNumbers = (Connector)getDataSource().associatePhoneNumbers(connector);
        assertEquals(connector.getId(), connWithNumbers.getId());
        List<PhoneNumber> numbers = connWithNumbers.getPhoneNumbers();
        assertTrue(numbers.size() > 0);
        assertEquals(numbers.size(), getDataSource().phoneNumberCount(connWithNumbers.getId()));
        assertEquals(numbers.get(0).getPhoneNumber(), lastNumber.getPhoneNumber());
    }

    @Test
    public void test_k_update_numbers() throws VivialConnectException, InterruptedException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);

        List<AssociatedNumber> associatedNumbers = getDataSource().getAssociatedNumbers();
        AssociatedNumber associatedNumber = associatedNumbers.get(associatedNumbers.size() - 2);

        PhoneNumber number = new PhoneNumber(associatedNumber.getId(), associatedNumber.getPhoneNumber());
        ArrayList<PhoneNumber> newNumbers = new ArrayList<PhoneNumber>();
        newNumbers.add(number);
        connector.setPhoneNumbers(newNumbers);
        assertEquals(connector.getPhoneNumbers().size(), 1);
        assertEquals(connector.getPhoneNumbers().get(0).getPhoneNumber(), associatedNumber.getPhoneNumber());

        Connector connWithNumbers = (Connector)getDataSource().updateAssociatedPhoneNumbers(connector);
        assertEquals(connector.getId(), connWithNumbers.getId());
        assertEquals(connWithNumbers.getPhoneNumbers().size(), 2);

        Connector otherConn = (Connector)getDataSource().getPhoneNumbers(connector.getId());
        assertEquals(otherConn.getPhoneNumbers().size(), 2);
        for (int i = 0; i < 2; i++) {
            assertEquals(otherConn.getPhoneNumbers().get(i).getPhoneNumberId(),
                         connWithNumbers.getPhoneNumbers().get(i).getPhoneNumberId());
            assertEquals(otherConn.getPhoneNumbers().get(i).getPhoneNumber(),
                         connWithNumbers.getPhoneNumbers().get(i).getPhoneNumber());
        }

        connector.setPhoneNumbers(newNumbers);
        connWithNumbers = (Connector)getDataSource().associatePhoneNumbers(connector);
        assertEquals(connWithNumbers.getPhoneNumbers().size(), 1);

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test_l_delete_all_numbers() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);
        List<PhoneNumber> numbers = connector.getPhoneNumbers();
        assertTrue(numbers.size() > 0);

        ConnectorWithPhoneNumbers updatedConnector = getDataSource().deleteAllPhoneNumbers(connector);

        numbers = updatedConnector.getPhoneNumbers();
        assertEquals(numbers.size(), 0);
    }

    @Test
    public void test_m_deleting_numbers() throws VivialConnectException, InterruptedException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);

        List<AssociatedNumber> associatedNumbers = getDataSource().getAssociatedNumbers();
        AssociatedNumber lastNumber = associatedNumbers.get(associatedNumbers.size() - 1);

        PhoneNumber number = new PhoneNumber(lastNumber.getId(), lastNumber.getPhoneNumber());

        connector.addPhoneNumber(number);
        Connector connWithNumbers = (Connector)getDataSource().updateAssociatedPhoneNumbers(connector);
        List<PhoneNumber> numbers = connWithNumbers.getPhoneNumbers();
        assertEquals(numbers.size(), 1);

        connWithNumbers = (Connector)getDataSource().deleteSinglePhoneNumber(connector, numbers.get(0));
        numbers = connWithNumbers.getPhoneNumbers();
        assertEquals(numbers.size(), 0);

        TimeUnit.SECONDS.sleep(1);

        connector.addPhoneNumber(number);
        connWithNumbers = (Connector)getDataSource().updateAssociatedPhoneNumbers(connector);
        numbers = connWithNumbers.getPhoneNumbers();
        assertEquals(numbers.size(), 1);

        List<PhoneNumber> toDelete = new ArrayList<PhoneNumber>();
        toDelete.add(number);
        connWithNumbers = (Connector)getDataSource().deletePhoneNumbers(connector, toDelete);
        numbers = connWithNumbers.getPhoneNumbers();
        assertEquals(numbers.size(), 0);

        connector.addPhoneNumber(number);
        connWithNumbers = (Connector)getDataSource().updateAssociatedPhoneNumbers(connector);
        numbers = connWithNumbers.getPhoneNumbers();
        assertEquals(numbers.size(), 1);
    }

    @Test
    public void test_n_delete_connector() throws VivialConnectException {
        List<Connector> connectors = getDataSource().getConnectors();
        Connector connector = connectors.get(connectors.size() - 1);
        assertTrue(getDataSource().deleteConnector(connector));
    }
}
