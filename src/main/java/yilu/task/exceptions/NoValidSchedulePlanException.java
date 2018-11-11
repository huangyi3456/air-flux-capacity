package yilu.task.exceptions;

public class NoValidSchedulePlanException extends RuntimeException {
    private static String Error_message = "No Flight Plan / Operations Plan is generated. Please verify input data.";
    public NoValidSchedulePlanException() {
        super(Error_message);
    }
}
