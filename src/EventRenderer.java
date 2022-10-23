import net.fortuna.ical4j.model.component.VEvent;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class EventRenderer extends DefaultListCellRenderer {
    public EventRenderer() {
    }
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value,index,isSelected,cellHasFocus);
        VEvent event = (VEvent) value;
        setText(event.getStartDate().toString().substring(17,21) + " " + event.getSummary().toString().substring(8));
        return this;


    }
}
