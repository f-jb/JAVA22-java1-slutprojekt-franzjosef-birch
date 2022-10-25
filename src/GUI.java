import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class GUI {
    static void choosePathAndExport(JFrame frame) {
        final JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            WeekCalendar.exportCalendar(fileChooser.getSelectedFile());
        }
    }

    static void createAndShowGUI() {
        JFrame frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));

        JMenuBar menuBar;
        JMenuItem exportCalendar;
        exportCalendar = new JMenuItem("Export Calendar");
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Import and export");
        menu.add("Import Calendar(TBD)");
        menu.add(exportCalendar);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        exportCalendar.addActionListener(e -> choosePathAndExport(frame));




        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));


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

    static class CreateAndAskEvent extends JFrame {

        TextArea summaryArea;
        JSpinner hoursSpinner;
        JSpinner minutesSpinner;
        LocalDateTime localDateTime;
        JFrame frame;
        ActionListener cancelButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        };
        ActionListener createButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event.DateBuilder startBuild = new Event.DateBuilder(localDateTime);
                startBuild.setHour(getHours());
                startBuild.setMinute(getMinutes());
                DateTime startTime = startBuild.build();


                Event.EventBuilder eventBuild = new Event.EventBuilder();
                eventBuild.setStartTime(startTime);
                eventBuild.setSummary(getSummary());
                eventBuild.setEndTime(startTime);
                eventBuild.setCalendar(WeekCalendar.getCurrentCalendar());
                eventBuild.buildEvent();
                frame.dispose();
            }
        };

        CreateAndAskEvent(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            frame = new JFrame("Event Creation");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setPreferredSize(new Dimension(600, 400));
            JPanel container = new JPanel();
            container.setLayout(new GridBagLayout());
            GridBagConstraints eventCreationConstraints = new GridBagConstraints();
            eventCreationConstraints.fill = GridBagConstraints.NONE;
            eventCreationConstraints.gridx = 0;
            eventCreationConstraints.gridy = 0;
            eventCreationConstraints.weighty = 0;
            eventCreationConstraints.weightx = 0;


            JPanel hoursContainer = new JPanel();
            hoursContainer.setLayout(new BoxLayout(hoursContainer, BoxLayout.PAGE_AXIS));

            JLabel hoursLabel = new JLabel("<html><h2>Hours</h2></html>");
            hoursContainer.add(hoursLabel, eventCreationConstraints);
            SpinnerNumberModel hours = new SpinnerNumberModel(12, 0, 23, 1);
            hoursSpinner = new JSpinner(hours);
            hoursContainer.add(hoursSpinner, eventCreationConstraints);
            container.add(hoursContainer);

            eventCreationConstraints.gridx = 1;
            eventCreationConstraints.gridy = 0;

            JPanel minutesContainer = new JPanel();
            minutesContainer.setLayout(new BoxLayout(minutesContainer, BoxLayout.PAGE_AXIS));

            JLabel minutesLabel = new JLabel("<html><h2>Minutes</h2></html>");
            minutesContainer.add(minutesLabel, eventCreationConstraints);

            SpinnerNumberModel minutes = new SpinnerNumberModel(0, 0, 59, 1);
            minutesSpinner = new JSpinner(minutes);
            minutesContainer.add(minutesSpinner, eventCreationConstraints);
            container.add(minutesContainer);
            eventCreationConstraints.weighty = 1.0;
            eventCreationConstraints.weightx = 1.0;

            eventCreationConstraints.gridx = 2;
            eventCreationConstraints.gridy = 0;

            JPanel summaryContainer = new JPanel();
            summaryContainer.setLayout(new GridBagLayout());
            GridBagConstraints summaryContainerConstraints = new GridBagConstraints();

            JLabel summaryLabel = new JLabel("<html><h2>Summary</h2></html>");

            summaryContainerConstraints.gridy = 0;
            summaryContainerConstraints.gridx = 0;

            summaryContainer.add(summaryLabel, summaryContainerConstraints);
            summaryContainerConstraints.fill = GridBagConstraints.BOTH;
            summaryContainerConstraints.gridy = 1;
            summaryContainerConstraints.gridx = 0;
            summaryContainerConstraints.weightx = 1.0;
            summaryContainerConstraints.weighty = 1.0;


            summaryArea = new TextArea(1, 10);
            summaryContainer.add(summaryArea, summaryContainerConstraints);
            eventCreationConstraints.fill = GridBagConstraints.BOTH;
            eventCreationConstraints.weighty = 1.0;
            eventCreationConstraints.weightx = 1.0;

            container.add(summaryContainer, eventCreationConstraints);
            eventCreationConstraints.fill = GridBagConstraints.NONE;


            JPanel buttonContainer = new JPanel();
            buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.LINE_AXIS));
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(cancelButtonListener);
            buttonContainer.add(cancelButton);

            JButton createButton = new JButton("Create");
            createButton.addActionListener(createButtonListener);
            buttonContainer.add(createButton);

            eventCreationConstraints.gridx = 2;
            eventCreationConstraints.gridy = 3;
            eventCreationConstraints.weighty = 0;
            eventCreationConstraints.weightx = 0;
            container.add(buttonContainer, eventCreationConstraints);
            frame.add(container);
            frame.pack();
            frame.setVisible(true);
        }

        JFrame getFrame() {
            return this.frame;
        }

        int getHours() {
            return (int) hoursSpinner.getValue();
        }

        int getMinutes() {
            return (int) minutesSpinner.getValue();
        }

        String getSummary() {
            return summaryArea.getText();
        }

    }

    static class DayContainer extends JPanel {
        LocalDateTime localDateTime;
        String dayName;
        int date;
        int month;
        int year;
        EventList eventList;
        JButton addEventButton;
        JButton removeEventButton;
        ActionListener removeEventListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = eventList.getSelectedIndex();
                Property uidToDelete = eventList.getEventUid(index);
                eventList.deleteEvent(uidToDelete);

                eventList.modelRemove(index);
                int size = eventList.modelGetSize();

                if (size == 0) {
                    removeEventButton.setEnabled(false);
                } else {
                    if (index == eventList.modelGetSize()) {
                        index--;
                    }
                    eventList.setSelectedIndex(index);
                    eventList.ensureIndexIsVisible(index);
                }
                eventList.clear();
                eventList.populateEventList(localDateTime);
            }
        };
        ActionListener addEventListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAndAskEvent askEvent = new CreateAndAskEvent(localDateTime);
                askEvent.getFrame().addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        eventList.clear();
                        eventList.populateEventList(localDateTime);
                        super.windowClosed(e);
                    }

                });

            }
        };
        ListSelectionListener checkIfListEmpty = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    removeEventButton.setEnabled(eventList.getSelectedIndex() != -1);
                }

            }
        };

        DayContainer(LocalDateTime ldtDay) {

            // init the values
            this.localDateTime = ldtDay;
            this.dayName = ldtDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
            this.date = ldtDay.getDayOfMonth();
            this.month = ldtDay.getMonthValue();
            this.year = ldtDay.getYear();
            this.eventList = new EventList();


            // Set the element behaviour within the container
            setLayout(new GridBagLayout());
            GridBagConstraints dayContainerConstraints = new GridBagConstraints();
            dayContainerConstraints.fill = GridBagConstraints.BOTH;
            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 0;
            dayContainerConstraints.weighty = 1.0;
            dayContainerConstraints.weightx = 1.0;


            // Set up the borders and current day indicator
            setBorder(borderAndTitle());


            // set up the Eventlist
            eventList.setEventListListener(checkIfListEmpty);
            add(eventList, dayContainerConstraints);
            eventList.populateEventList(ldtDay);


            // set up the button area
            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 1;
            dayContainerConstraints.fill = GridBagConstraints.HORIZONTAL;
            dayContainerConstraints.anchor = GridBagConstraints.PAGE_END;
            dayContainerConstraints.weighty = 0.0;
            dayContainerConstraints.weightx = 0.0;

            //set up the buttons
            addEventButton = new JButton("<html><h3>Add note</h3></html>");
            addEventButton.addActionListener(addEventListener);
            add(addEventButton, dayContainerConstraints);


            dayContainerConstraints.gridy = 2;
            removeEventButton = new JButton("<html><h3>Delete note</h3></html>");
            removeEventButton.setEnabled(false);
            add(removeEventButton, dayContainerConstraints);
            removeEventButton.addActionListener(removeEventListener);
        }

        TitledBorder borderAndTitle() {
            Border blackline = BorderFactory.createLineBorder(Color.black);
            TitledBorder dayBorder;
            if (date == WeekCalendar.getNow().getDayOfMonth()) {
                dayBorder = BorderFactory.createTitledBorder(blackline, "<html><h2 style=\"color:red\">" + date + "/" + month + " - " + dayName + "</h2></html>");
            } else {
                dayBorder = BorderFactory.createTitledBorder(blackline, "<html><h2>" + date + "/" + month + " - " + dayName + "</h2></html>");
            }
            return dayBorder;
        }
    }
}