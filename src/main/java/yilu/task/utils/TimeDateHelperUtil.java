package yilu.task.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class TimeDateHelperUtil {
    // TODO find timezone of city??
    static Map<String, ZoneId> mapping = new HashMap<String, ZoneId>() {{
        put("TXL", ZoneId.of("Europe/Berlin"));
        put("MUC", ZoneId.of("Europe/Berlin"));
        put("HAM", ZoneId.of("Europe/Berlin"));
        put("LHR", ZoneId.of("Europe/London"));
    }};

    public static long toTimeDuration(String flightTime) {
        String[] strs = flightTime.trim().split(":");
        return Long.parseLong(strs[0]) * 3600 + Long.parseLong(strs[1]) * 60;
    }

    public static long getTimestamp(String time, String airport) {
        String[] strs = time.trim().split(":");
        int hours = Integer.parseInt(strs[0]);
        int minutes = Integer.parseInt(strs[1]);

        LocalDate localDate = LocalDate.of(2018, 4, 13);
        ZonedDateTime zdt = localDate.atStartOfDay(mapping.get(airport));
        zdt = zdt.plusHours(hours).plusMinutes(minutes);
        return zdt.toInstant().getEpochSecond();
    }

    public static String getStrFromEpochSec(long epochSec, String airport) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(epochSec), mapping.get(airport));
        return zdt.toString();
    }
}
