import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class GUI {

    static void createAndShowGUI() {
        JFrame frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
//        GridBagConstraints weekConstraints = new GridBagConstraints();




        JPanel weekNumberContainer = new JPanel();
        JLabel weekNumber = new JLabel("<html><h1>Week " + WeekCalendar.getWeek() + "</h1></html>");
        weekNumberContainer.add(weekNumber);





        JPanel weekViewContainer = new JPanel(new GridBagLayout());
        GridBagConstraints weekConstraints = new GridBagConstraints();
        weekConstraints.fill = GridBagConstraints.BOTH;
        weekViewContainer.setBackground(Color.black);
        weekConstraints.weightx = 1;
        weekConstraints.weighty = 1;
        for (LocalDateTime days : WeekCalendar.getDays()) {
            weekViewContainer.add(new DayContainer(days), weekConstraints);
        }



        container.add(weekNumberContainer);
        container.add(weekViewContainer);




        frame.add(container);
        frame.pack();
        frame.setVisible(true);
    }

    static String writeNote() {
        return JOptionPane.showInputDialog(null, null, "Enter note");
    }

    static class DayContainer extends JPanel implements ActionListener, ListSelectionListener {
        String dayName;
        int date;
        int month;

            DefaultListModel listModel;
            JList noteList;
            ListSelectionModel listSelectionModel;
        public void actionPerformed(ActionEvent e) {
        }
        public void valueChanged(ListSelectionEvent e) {
            }



        DayContainer(LocalDateTime ldtDay) {

            // init the values
            this.dayName = ldtDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
            this.date = ldtDay.getDayOfMonth();
            this.month = ldtDay.getMonthValue();
//            setLayout(new GridBagLayout());
            super.setLayout(new GridBagLayout());
            GridBagConstraints dayContainerConstraints = new GridBagConstraints();
            listModel = new DefaultListModel();
            noteList = new JList<>(listModel);
            noteList.setVisibleRowCount(JList.VERTICAL);
            noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listSelectionModel = noteList.getSelectionModel();
//                noteList.addListSelectionListener();
//                listSelectionModel.addListSelectionListener(checkIfListEmpty);
            JScrollPane noteListScrollPane = new JScrollPane(noteList);
            for (int i = 0; i < 20; i++) {
                listModel.addElement("<html><body style=\"width: 120px\">test see if this actually wordwraps or not, dunno</html>");
            }


            // set the element behaviour within the container
            dayContainerConstraints.fill = GridBagConstraints.BOTH;
            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 0;
//            dayContainerConstraints.anchor = GridBagConstraints.PAGE_END;
            dayContainerConstraints.weighty = 1.0;
            dayContainerConstraints.weightx = 1.0;
            add(noteListScrollPane, dayContainerConstraints);

            // Set up the borders and current day indicator
            Border blackline = BorderFactory.createLineBorder(Color.black);
            TitledBorder dayBorder;
            if (date == WeekCalendar.getNow().getDayOfMonth()) {
                dayBorder = BorderFactory.createTitledBorder(blackline, "<html><h2 style=\"color:red\">" + date + "/" + month + " - " + dayName + "</h2></html>");
            } else {
                dayBorder = BorderFactory.createTitledBorder(blackline, "<html><h2>" + date + "/" + month + " - " + dayName + "</h2></html>");
            }
            setBorder(dayBorder);

            // Set up the noteList
  //          listModel.addElement("<html><body style=\"width: 120px\">test see if this actually wordwraps or not, dunno</html>");
 //           listModel.addElement("test");

            //    JPanel noteContainer = new JPanel();
//            noteContainer.setBackground(Color.green);


            // JLabel dayBorderLabel = new JLabel("<html><h2>" + dayName + "</h2></html>");
            //super.add(dayBorderLabel);
            JButton addNoteButton = new JButton("<html><h3>Add note</h3></html>");
            JButton deleteNoteButton = new JButton("<html><h3>Delete note</h3></html>");


            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 1;
            dayContainerConstraints.fill = GridBagConstraints.HORIZONTAL;
            dayContainerConstraints.anchor = GridBagConstraints.PAGE_END;
            dayContainerConstraints.weighty = 0.0;
            dayContainerConstraints.weightx = 0.0;

            addNoteButton.addActionListener(addNoteListener);
            add(addNoteButton, dayContainerConstraints);
            dayContainerConstraints.gridy = 2;
            deleteNoteButton.addActionListener(deleteNoteListener);
            add(deleteNoteButton, dayContainerConstraints);
        }

        ActionListener addNoteListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNote();
            }
        };
        ActionListener deleteNoteListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNote();
            }
        };
        ListSelectionListener checkIfListEmpty = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (noteList.getSelectedIndex() == -1) {
                        //deleteNoteButton.setEnabled(false);
                    } else {
                       // deleteNoteButton.setEnabled(true);

                    }
                }

            }
        };




        void addNote() {
            String userNote = writeNote();
            if (userNote == null) {
                return;
            }
            // TODO: Figure out some kind of dynamic scaling for the width of the cells.
            listModel.addElement("<html><body style=\"width: 80px\">" + userNote + "</html>");
//            JLabel note = new JLabel("<html><p>" + userNote + "</p></html>");
//            noteContainer.add(note);
            //           noteContainer.add(Box.createRigidArea(new Dimension(0, 5)));
            //          noteContainer.updateUI();
        }


    }


}


