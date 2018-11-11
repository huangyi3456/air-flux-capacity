package yilu.task.utils;

import yilu.task.entity.Airplane;
import yilu.task.entity.Schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUtil {
    // TODO read it from config file
    static Map<String, String> airportCityMapping = new HashMap<String, String>(){{
        put("TXL", "Berlin");
        put("MUC", "Munich");
        put("LHR", "London");
        put("HAM", "Hamburg");
    }};
    static Map<String, String> cityAirportMapping = new HashMap<String, String>(){{
        put("Berlin", "TXL");
        put("Munich", "MUC");
        put("London", "LHR");
        put("Hamburg", "HAM");
    }};

    public static Airplane parseStrToPlane(String line) {
        Pattern pattern = Pattern.compile("([\\w]+)\\s([\\w-]+)\\s-\\s([\\w]+),\\sregistration\\s([\\w-]+)");
        Matcher matcher = pattern.matcher(line.trim());
        if (matcher.matches()) {
            Airplane airplane = new Airplane();
            airplane.setType(matcher.group(2));
            airplane.setBase(cityAirportMapping.get(matcher.group(3)));
            airplane.setRegistration(matcher.group(4));
            return airplane;
        } else {
            return null;
        }

    }

    public static Schedule parseStrToSchedule(String line) {
        String[] strs = line.trim().split("\\s+");
        if (strs.length != 4) {
            return null;
        } else {
            Schedule schedule = new Schedule();
            schedule.setFlightTime(TimeDateHelperUtil.toTimeDuration(strs[3]));
            schedule.setDepatureTime(TimeDateHelperUtil.getTimestamp(strs[0], strs[1]));
            schedule.setDestination(strs[2]);
            schedule.setOrigin(strs[1]);
            return schedule;
        }
    }
}
