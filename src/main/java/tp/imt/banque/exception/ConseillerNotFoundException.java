package tp.imt.banque.exception;

public class ConseillerNotFoundException extends RuntimeException {
    public ConseillerNotFoundException(String message) {
        super(message);
    }
}