import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.property.*;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;

public class WeekCalendar {
    private static final LocalDateTime[] week = new LocalDateTime[7];
    private static final LocalDateTime now = LocalDateTime.now();
    private static final Calendar currentCalendar = createCalendar();
    static Calendar createCalendar(){
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//FJ Birch//GritCalendar//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        return calendar;
    }
    static Calendar getCurrentCalendar(){
        return currentCalendar;
    }
    static void exportCalender(Calendar calendar){
        FileHandler.export(calendar);
    }
    static LocalDateTime getNow(){
        return now;
    }

    static String getWeek() {
        // För lite intressant läsning av skillnaden mellan WeekFields och ChronoField för att få veckonummer:
        // https://webstep.se/nyheter--blogginlagg/blogginlagg/2022-05-27-weekfields-to-the-rescue
        int weekOfYear = now.get(WeekFields.ISO.weekOfYear());
//        int weekOfYear1 = now.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        return java.lang.String.valueOf(weekOfYear);
    }

    static LocalDateTime[] getDays() {
        // TODO: Clean up the logic. It's a bit ugly with the arithmetics.
        int current_day = now.getDayOfWeek().getValue();
        LocalDateTime startMonday = now.minusDays(current_day - 1);
        for (int i = 0; i <= 6; i++) {
            week[i] = startMonday.plusDays(i);
        }
        return week;
    }
}
