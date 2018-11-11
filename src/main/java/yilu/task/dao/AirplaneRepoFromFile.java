package yilu.task.dao;

import yilu.task.entity.Airplane;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class AirplaneRepoFromFile implements AirplaneRepository {
    @Override
    public Set<Airplane> getAirplane() {
        return new HashSet<>();
    }
}
