package yilu.task.dao;

import yilu.task.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepoFromFile implements ScheduleRepository {

    @Override
    public List<Schedule> getSchedule() {
        return new ArrayList<>();
    }
}
