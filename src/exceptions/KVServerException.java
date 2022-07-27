package exceptions;

public class KVServerException extends RuntimeException {
    public KVServerException(String message) {
        super(message);
    }
}