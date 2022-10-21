import java.time.LocalDateTime;
import java.time.temporal.WeekFields;

public class Calendar {
    static LocalDateTime[] week = new LocalDateTime[7];
    static final LocalDateTime now = LocalDateTime.now();
    static LocalDateTime getNow(){
        return now;
    }

    static String getWeek() {
        int weekOfYear = now.get(WeekFields.ISO.weekOfYear());
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
