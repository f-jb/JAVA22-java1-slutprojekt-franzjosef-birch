import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.util.GregorianCalendar;

class Event {

    static TimeZone getTimeZone(){
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timeZone = registry.getTimeZone("Europe/Stockholm");
        return timeZone;
    }
    static VTimeZone getTz(){
        return getTimeZone().getVTimeZone();

    }

    private static java.util.Calendar startDate() {
        java.util.Calendar startDate = new GregorianCalendar();
        startDate.setTimeZone(getTimeZone());
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.OCTOBER);
        startDate.set(java.util.Calendar.YEAR, 2022);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 24);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);
        return startDate;
    }

    private static java.util.Calendar endDate() {
        java.util.Calendar endDate = new GregorianCalendar();
        endDate.setTimeZone(getTimeZone());
        endDate.set(java.util.Calendar.YEAR, 2022);
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.OCTOBER);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 24);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 15);
        endDate.set(java.util.Calendar.MINUTE, 0);
        endDate.set(java.util.Calendar.SECOND, 0);
        return endDate;
    }

    static void create(Calendar calendar, String summary) {
        DateTime start = new DateTime(startDate().getTime());
        DateTime end = new DateTime(endDate().getTime());
        String eventName = summary;

        VEvent meeting = new VEvent(start, end, eventName);

        meeting.getProperties().add(getTz().getTimeZoneId());


        UidGenerator ug = new RandomUidGenerator();
        Uid uid = ug.generateUid();
        meeting.getProperties().add(uid);

        calendar.getComponents().add(meeting);
        System.out.println(calendar.getComponents());
    }
    static VEvent createEvent(String summary) {
        DateTime start = new DateTime(startDate().getTime());
        DateTime end = new DateTime(endDate().getTime());
        String eventName = summary;

        VEvent meeting = new VEvent(start, end, eventName);

        meeting.getProperties().add(getTz().getTimeZoneId());


        UidGenerator ug = new RandomUidGenerator();
        Uid uid = ug.generateUid();
        meeting.getProperties().add(uid);

        return meeting;
    }
}