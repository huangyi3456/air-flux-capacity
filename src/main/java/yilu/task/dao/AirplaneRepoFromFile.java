package yilu.task.dao;

import org.springframework.util.ResourceUtils;
import yilu.task.entity.Airplane;
import org.springframework.stereotype.Repository;
import yilu.task.utils.ParserUtil;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AirplaneRepoFromFile implements AirplaneRepository {
    @Override
    public Set<Airplane> getAirplane() {
        Set<Airplane> airplaneSet = new HashSet<>();
        BufferedReader reader = null;
        try {
            File file = ResourceUtils.getFile("classpath:aircrafts");
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Airplane airplane = ParserUtil.parseStrToPlane(line);
                if (airplane != null)
                    airplaneSet.add(airplane);
            }
            return airplaneSet;
        } catch (IOException e2) {
            throw new RuntimeException("File does no exist or failed to parse file to list of airplanes");
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
