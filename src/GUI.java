import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class GUI {

    static void createAndShowGUI() {
        JFrame frame = new JFrame("Calender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
//        GridBagConstraints weekConstraints = new GridBagConstraints();

        JPanel weekNumber = new JPanel();
        //weekNumber.setPreferredSize(new Dimension(700,80));
        JLabel month = new JLabel("<html><h1>Week " + Calendar.getWeek() + "</h1></html>");
        weekNumber.add(month);


        JPanel weekView = new JPanel(new GridBagLayout());
        GridBagConstraints weekConstraints = new GridBagConstraints();
        weekConstraints.fill = GridBagConstraints.BOTH;
        weekView.setBackground(Color.black);
//        weekView.setPreferredSize(new Dimension(700, 400));
        weekConstraints.weightx = 1;
        weekConstraints.weighty = 1;
        for (LocalDateTime days : Calendar.getDays()) {
            weekView.add(new DayContainer(days), weekConstraints);
        }

        container.add(weekNumber);
        container.add(weekView);
        frame.add(container);
        frame.pack();
        frame.setVisible(true);
    }

    static String writeNote() {
        return JOptionPane.showInputDialog(null, null, "Enter note");
    }

    static class DayContainer extends JPanel implements ActionListener {
        String dayName;
        int date;
        int month;
        JPanel noteContainer = new JPanel();

        DayContainer(LocalDateTime ldtDay) {
            this.dayName = ldtDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
            this.date = ldtDay.getDayOfMonth();
            this.month = ldtDay.getMonthValue();
            super.setLayout(new GridBagLayout());
            GridBagConstraints dayContainerConstraints = new GridBagConstraints();
            dayContainerConstraints.fill = GridBagConstraints.BOTH;
            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 0;

            dayContainerConstraints.anchor = GridBagConstraints.PAGE_END;
            dayContainerConstraints.weighty = 1.0;
            dayContainerConstraints.weightx = 1.0;

            // Setting up the borders
            Border blackline = BorderFactory.createLineBorder(Color.black);
            if (date == Calendar.getNow().getDayOfMonth()) {
                TitledBorder dayBorder = BorderFactory.createTitledBorder(blackline, "<html><h2 style=\"color:red\">" + date + "/" + month + " - " + dayName + "</h2></html>");
                super.setBorder(dayBorder);
            } else {
                TitledBorder dayBorder = BorderFactory.createTitledBorder(blackline, "<html><h2>" + date + "/" + month + " - " + dayName + "</h2></html>");
                super.setBorder(dayBorder);
            }

            //    JPanel noteContainer = new JPanel();
//            noteContainer.setBackground(Color.green);
            noteContainer.setLayout(new BoxLayout(noteContainer, BoxLayout.PAGE_AXIS));
            this.add(noteContainer, dayContainerConstraints);


            // JLabel dayBorderLabel = new JLabel("<html><h2>" + dayName + "</h2></html>");
            //super.add(dayBorderLabel);
            JButton addNoteButton = new JButton("<html><h3>Add note</h3></html>");
            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 1;
            dayContainerConstraints.fill = GridBagConstraints.HORIZONTAL;
            dayContainerConstraints.anchor = GridBagConstraints.PAGE_END;
            dayContainerConstraints.weighty = 1.0;
            dayContainerConstraints.weightx = 1.0;

            addNoteButton.addActionListener(this);
            super.add(addNoteButton, dayContainerConstraints);
        }

        public void actionPerformed(ActionEvent e) {
            addNote();
        }

        void addNote() {
            String userNote = writeNote();
            if(userNote== null) {
                return;
            }
            JLabel note = new JLabel("<html><p>" + userNote + "</p></html>");
            noteContainer.add(note);
            noteContainer.add(Box.createRigidArea(new Dimension(0, 5)));
            noteContainer.updateUI();
        }
    }


}

