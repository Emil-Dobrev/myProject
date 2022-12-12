package exceptions;

public class EmailAlreadyTaken extends RuntimeException{

    public EmailAlreadyTaken(String errorMessage) {
        super(errorMessage);
    }
}
