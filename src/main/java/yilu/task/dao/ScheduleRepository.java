package yilu.task.dao;

import yilu.task.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository {
    List<Schedule> getSchedule();
}
