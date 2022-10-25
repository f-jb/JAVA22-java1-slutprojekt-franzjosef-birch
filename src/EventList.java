import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.LocalDateTime;

public class EventList extends JPanel {
    private final JList eventList;
    private final DefaultListModel listModel = new DefaultListModel();
    int getSelectedIndex(){
        return eventList.getSelectedIndex();
    }
    void modelRemove(int i){
        listModel.remove(i);
    }
    int modelGetSize(){
       return listModel.getSize();
    }
    void clear(){
        listModel.clear();
    }

    Property getEventUid(int index){
        VEvent vEvent = (VEvent) listModel.get(index);
        return vEvent.getUid();

    }


    EventList() {
        setLayout(new GridBagLayout());
        GridBagConstraints eventListConstraints = new GridBagConstraints();
        eventListConstraints.fill = GridBagConstraints.BOTH;
        eventListConstraints.gridx = 0;
        eventListConstraints.gridy = 0;
        eventListConstraints.weighty = 1.0;
        eventListConstraints.weightx = 1.0;


        EventRenderer eventRenderer = new EventRenderer();

        eventList = new JList<>(listModel);
        eventList.setCellRenderer(eventRenderer);
        eventList.setVisibleRowCount(JList.VERTICAL);
        eventList.setSelectedIndex(0);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane eventListScrollPane = new JScrollPane(eventList);

        add(eventListScrollPane,eventListConstraints);
    }
    void populateEventList(LocalDateTime ldt){
        VEvent[] vEvents = WeekCalendar.getCurrentCalendar().getComponents().toArray(new VEvent[0]);
        for (VEvent event : vEvents) {
            String date = ldt.toString().substring(0,ldt.toString().indexOf('T')).replace("-","");
            if(event.getStartDate().toString().substring(8,16).equals(date)) {
                listModel.addElement(event);
            }
        }
    }
    void setEventListListener(ListSelectionListener listListener){
        eventList.addListSelectionListener(listListener);

    }

    void deleteEvent(Property uidToDelete) {
            Event.deleteByUid(WeekCalendar.getCurrentCalendar(), uidToDelete);

    }

    public void setSelectedIndex(int index) {
        eventList.setSelectedIndex(index);
    }

    public void ensureIndexIsVisible(int index) {
        eventList.ensureIndexIsVisible(index);
    }
}

