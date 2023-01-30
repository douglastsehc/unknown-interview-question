package exception;

public class InvalidCommandParamsException extends RuntimeException {

    private final String helperMessage;

    public InvalidCommandParamsException(String message, String helperMessage) {
        super(message);
        this.helperMessage = helperMessage;
    }

    public String getHelpMessage() {
        return helperMessage;
    }
}