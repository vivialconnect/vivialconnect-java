package net.vivialconnect.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.log.Log;
import net.vivialconnect.model.log.LogCollection;

public class LogTest extends BaseTestCase {

    @Test
    public void test_get_logs() throws VivialConnectException {
        GregorianCalendar endTime = new GregorianCalendar();
        GregorianCalendar startTime = (GregorianCalendar)endTime.clone();
        startTime.add(Calendar.DAY_OF_YEAR, -7); // Get logs for last week

        List<Log> logs = getDataSource().getLogs(startTime.getTime(), endTime.getTime()).getLogs();

        assertNotNull(logs);
        assertTrue(logs.size() > 0);
        Log firstLog = logs.get(0);
        assertEquals(firstLog.getLogId().length(), 56);
    }

    @Test
    public void test_aggregate_logs() throws VivialConnectException {
        GregorianCalendar endTime = new GregorianCalendar();
        GregorianCalendar startTime = (GregorianCalendar)endTime.clone();
        startTime.add(Calendar.DAY_OF_YEAR, -7); // Get logs for last week

        List<Log> logs = getDataSource().getAggregate(startTime.getTime(), endTime.getTime(), "hours").getLogs();
        
        assertNotNull(logs);
        assertTrue(logs.size() > 0);
        Log firstLog = logs.get(0);
        assertEquals(firstLog.getLogTimestamp().length(), 10);
    }

}
