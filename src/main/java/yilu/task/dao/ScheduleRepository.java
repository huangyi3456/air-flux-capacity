package yilu.task.dao;

import yilu.task.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScheduleRepository {
    Set<Schedule> getSchedule();
}
