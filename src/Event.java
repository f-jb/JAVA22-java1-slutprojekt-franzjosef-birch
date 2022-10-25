import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;

class Event {

    private static TimeZone getTimeZone() {
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        return registry.getTimeZone("Europe/Stockholm");
    }

    private static VTimeZone getTz() {
        return getTimeZone().getVTimeZone();
    }

    static class DateBuilder {
        private final java.util.Calendar date = new GregorianCalendar();
        private int hour;
        private int minute;

        DateBuilder(LocalDateTime localDateTime) {
            date.setTimeZone(getTimeZone());
            date.set(localDateTime.getYear(), localDateTime.getMonth().getValue() - 1, localDateTime.getDayOfMonth());
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        DateTime build() {
            date.set(java.util.Calendar.HOUR_OF_DAY, hour);
            date.set(java.util.Calendar.MINUTE, minute);
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


        void buildEvent() {
            VEvent meeting = new VEvent(startTime, endTime, summary);
            meeting.getProperties().add(getTz().getTimeZoneId());
            UidGenerator ug = new RandomUidGenerator();
            Uid uid = ug.generateUid();
            meeting.getProperties().add(uid);
            WeekCalendar.getCurrentCalendar().getComponents().add(meeting);
        }
    }

    static void deleteByUid(Calendar calendar, Property uidToDelete) {
        VEvent[] currentCalendar = calendar.getComponents().toArray(new VEvent[0]);
        int indexOfDelete = 0;
        for (VEvent event : currentCalendar) {
            if (event.getUid().equals(uidToDelete)) {
                WeekCalendar.getCurrentCalendar().getComponents().remove(indexOfDelete);
                break;
            }
            indexOfDelete++;
        }
    }
}
