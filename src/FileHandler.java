import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler {
    static void export(Calendar calendar) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("mycalendar.ics");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        CalendarOutputter outputter = new CalendarOutputter();
        try {
            outputter.output(calendar,fout);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
