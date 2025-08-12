package exceptions;

public class AddTaskException extends RuntimeException {
    public AddTaskException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
