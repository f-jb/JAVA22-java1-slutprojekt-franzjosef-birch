import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;

class Event {

    private static TimeZone getTimeZone(){
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timeZone = registry.getTimeZone("Europe/Stockholm");
        return timeZone;
    }
    private static VTimeZone getTz(){
        return getTimeZone().getVTimeZone();
    }


    private static java.util.Calendar startDate(int month, int day) {
        java.util.Calendar startDate = new GregorianCalendar();
        startDate.setTimeZone(getTimeZone());
        startDate.set(java.util.Calendar.MONTH, month);
        startDate.set(java.util.Calendar.YEAR, 2022);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, day);
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
    static class DateBuilder{
        private java.util.Calendar date = new GregorianCalendar();
        private int hour;
        private int minute;
        DateBuilder(LocalDateTime localDateTime) {
            date.setTimeZone(getTimeZone());
            date.set(localDateTime.getYear(), localDateTime.getMonth().getValue()-1, localDateTime.getDayOfMonth());
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        DateTime build() {
            return new DateTime(date.getTime());
        }
    }

    static class EventBuilder {
        Calendar calendar;
        String summary;
        DateTime startTime;
        DateTime endTime;

        public void setEndTime(DateTime endTime) {
            this.endTime = endTime;
        }

        public void setStartTime(DateTime startTime) {
            this.startTime = startTime;
        }

        public void setCalendar(Calendar calendar) {
            this.calendar = calendar;
        }


        public void setSummary(String summary) {
            this.summary = summary;
        }


        void buildEvent(){
            VEvent meeting = new VEvent(startTime, endTime, summary);
            meeting.getProperties().add(getTz().getTimeZoneId());
            UidGenerator ug = new RandomUidGenerator();
            Uid uid = ug.generateUid();
            meeting.getProperties().add(uid);
            System.out.println(meeting.getProperties());
            calendar.getComponents().add(meeting);
        }
    }

    static void deleteByUid(Calendar calendar, Property uidToDelete){
        VEvent[] currentCalendar = calendar.getComponents().toArray(new VEvent[0]);
        int indexOfDelete = 0;
        for(VEvent event: currentCalendar){
            if(event.getUid().equals(uidToDelete)){
                WeekCalendar.getCurrentCalendar().getComponents().remove(indexOfDelete);
                break;
            }
            indexOfDelete++;
        }
    }

    static VEvent createStuff(Calendar calendar, String summary, int month, int date) {

        DateTime start = new DateTime(startDate(month, date).getTime());
        DateTime end = new DateTime(endDate().getTime());
        String eventName = summary;

        VEvent meeting = new VEvent(start, end, eventName);

        meeting.getProperties().add(getTz().getTimeZoneId());


        UidGenerator ug = new RandomUidGenerator();
        Uid uid = ug.generateUid();
        meeting.getProperties().add(uid);

        calendar.getComponents().add(meeting);
        return meeting;
    }
}