package yilu.task.dao;

import yilu.task.entity.Airplane;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepository {
    List<Airplane> getAirplane();

}
