import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;

public class Calendar {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    static LocalDateTime now = LocalDateTime.now();

    static void getWeek(){
        int weekOfYear = now.get(WeekFields.ISO.weekOfYear());
        System.out.println(weekOfYear);
    }
    static void printDays(){
    for(int i=1;i <=7;i++){
        if( now.getDayOfWeek() == DayOfWeek.of(i)){
            System.out.println(ANSI_RED + DayOfWeek.of(i)+ ANSI_RESET);
        } else {
            System.out.println(DayOfWeek.of(i));
        }
    }





}

}
