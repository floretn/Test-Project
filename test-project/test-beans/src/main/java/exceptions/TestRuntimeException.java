package exceptions;

public class TestRuntimeException extends RuntimeException {

    public TestRuntimeException(String msg) {
        super(msg);
    }

    public TestRuntimeException(String msg, Exception ex) {
        super(msg, ex);
    }
}
