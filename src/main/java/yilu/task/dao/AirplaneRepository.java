package yilu.task.dao;

import yilu.task.entity.Airplane;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AirplaneRepository {
    Set<Airplane> getAirplane();

}
