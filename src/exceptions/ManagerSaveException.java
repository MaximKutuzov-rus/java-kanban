package exceptions;

public class ManagerSaveException extends Exception{
    public ManagerSaveException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
