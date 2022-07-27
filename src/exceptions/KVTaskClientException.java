package exceptions;

public class KVTaskClientException extends RuntimeException {
    public KVTaskClientException(String message) {
        super(message);
    }
}