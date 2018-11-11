package yilu.task.dao;

import yilu.task.entity.Airplane;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AirplaneRepoFromFile implements AirplaneRepository {
    @Override
    public List<Airplane> getAirplane() {
        return new ArrayList<>();
    }
}
