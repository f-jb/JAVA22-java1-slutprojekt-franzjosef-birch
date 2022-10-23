import net.fortuna.ical4j.filter.PropertyFilter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;

public class Main {
    public static void main(String[] args) {
        WeekCalendar.createCalendar();
        GUI.createAndShowGUI();
        Event.create(WeekCalendar.getCurrentCalendar(),"test");
        System.out.println(WeekCalendar.getCurrentCalendar().toString());
        WeekCalendar.exportCalender(WeekCalendar.getCurrentCalendar());
//        FileHandler.export(WeekCalendar.getCurrentCalendar());
        System.out.println(WeekCalendar.getCurrentCalendar().getComponents().getComponent(VEvent.VEVENT).getProperty("SUMMARY"));
    }
}