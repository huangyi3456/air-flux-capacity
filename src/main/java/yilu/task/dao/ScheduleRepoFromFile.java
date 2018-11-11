package yilu.task.dao;

import org.springframework.util.ResourceUtils;
import yilu.task.entity.Schedule;
import org.springframework.stereotype.Repository;
import yilu.task.utils.ParserUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ScheduleRepoFromFile implements ScheduleRepository {

    @Override
    public Set<Schedule> getSchedule() {
        Set<Schedule> scheduleList = new HashSet<>();
        BufferedReader reader = null;
        try {
            File file = ResourceUtils.getFile("classpath:flight-schedules");
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Schedule schedule = ParserUtil.parseStrToSchedule(line);
                if (schedule != null) {
                    scheduleList.add(schedule);
                }
            }
            return scheduleList;
        } catch (IOException e2) {
            throw new RuntimeException("File does no exist or failed to parse file to list of schedules");
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
