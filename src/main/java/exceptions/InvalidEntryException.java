package exceptions;

import nva.commons.exceptions.ApiGatewayException;
import org.apache.http.HttpStatus;

public class InvalidEntryException extends ApiGatewayException {

    public static String MESSAGE = "Invalid entry: ";

    public InvalidEntryException(String message) {
        super(MESSAGE + message);
    }

    @Override
    protected Integer statusCode() {
        return HttpStatus.SC_BAD_REQUEST;
    }
}
