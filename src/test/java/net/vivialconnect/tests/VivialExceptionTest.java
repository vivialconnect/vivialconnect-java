package net.vivialconnect.tests;

import net.vivialconnect.model.error.*;
import net.vivialconnect.tests.data.DataSource;
import org.junit.Test;

public class VivialExceptionTest extends BaseTestCase {

    @Test(expected = BadRequestException.class)
    public void test_bad_request_exception() throws VivialConnectException {
        DataSource dataSource = getDataSource();
        dataSource.throwBadRequestException();
    }

    @Test(expected = UnauthorizedAccessException.class)
    public void test_unauthorized_access_exception() throws VivialConnectException {
        DataSource dataSource = getDataSource();
        dataSource.throwUnauthorizedAccessException();
    }

    @Test(expected = ForbiddenAccessException.class)
    public void test_forbidden_access_exception() throws VivialConnectException {
        DataSource dataSource = getDataSource();
        dataSource.throwForbiddenAccessException();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_resource_not_found_exception() throws VivialConnectException {
        DataSource dataSource = getDataSource();
        dataSource.throwResourceNotFoundException();
    }

    @Test(expected = RateLimitException.class)
    public void test_rate_limit_exception() throws VivialConnectException {
        DataSource dataSource = getDataSource();
        dataSource.throwRateLimitException();
    }

    @Test(expected = MessageErrorException.class)
    public void test_throw_message_exception() throws VivialConnectException {
        DataSource dataSource = getDataSource();
        dataSource.throwMessageErrorException();
    }

    @Test(expected = ApiRequestException.class)
    public void test_throw_unexpected_exception() throws VivialConnectException {
        DataSource dataSource = getDataSource();
        dataSource.throwUnexpectedErrorException();
    }

}
