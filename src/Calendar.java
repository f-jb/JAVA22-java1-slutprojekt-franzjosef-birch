import java.time.LocalDateTime;
import java.time.temporal.WeekFields;

public class Calendar {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    static LocalDateTime now = LocalDateTime.now();

    static void getWeek(){
        int weekOfYear = now.get(WeekFields.ISO.weekOfYear());
        System.out.println("The week nr is: " + weekOfYear);
    }
    static void printDays(){
        int current_day = now.getDayOfWeek().getValue();
        LocalDateTime startMonday = now.minusDays(current_day-1);
    for(int i=0;i <=6;i++){
        if( i == current_day-1){
            System.out.println(ANSI_RED + startMonday.plusDays(i).getDayOfWeek() + " " + startMonday.plusDays(i).getDayOfMonth()+"/"+startMonday.plusDays(i).getMonthValue() + ANSI_RESET);
       } else {
            System.out.println(startMonday.plusDays(i).getDayOfWeek() + " " + startMonday.plusDays(i).getDayOfMonth()+"/"+startMonday.plusDays(i).getMonthValue());
       }
    }





}

}
