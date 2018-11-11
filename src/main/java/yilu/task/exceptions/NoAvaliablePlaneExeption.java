package yilu.task.exceptions;

import yilu.task.entity.Schedule;

public class NoAvaliablePlaneExeption extends RuntimeException {
    private static String ERROR = "There is not any plane which is able to carry out the schedule";

    public NoAvaliablePlaneExeption(Schedule schedule) {
        super(ERROR + ": " + schedule);
    }

}
