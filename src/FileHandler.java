import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler {
    static void export(Calendar calendar, File filePath) {
        FileOutputStream fout;
        try {
            fout = new FileOutputStream(filePath+".ics");
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
