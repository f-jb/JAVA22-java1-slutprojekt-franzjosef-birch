import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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
    private static final Settings settings = new Settings();

    private static void createExportWindow(JFrame frame) {
        final JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            WeekCalendar.exportCalendar(fileChooser.getSelectedFile());
        }
    }

    private static JMenuBar createMenu(JFrame frame) {
        JMenu menu = new JMenu("File");

        JMenuItem exportCalendar = new JMenuItem("Export Calendar");
        exportCalendar.addActionListener(e -> createExportWindow(frame));

        ButtonGroup timeSelectionGroup = new ButtonGroup();
        JRadioButtonMenuItem spinners = new JRadioButtonMenuItem("Spinners");
        spinners.setSelected(true);
        spinners.addActionListener(e -> settings.setSpinner(true));

        JRadioButtonMenuItem comboBox = new JRadioButtonMenuItem("Combo Box");
        comboBox.addActionListener(e -> settings.setSpinner(false));

        timeSelectionGroup.add(spinners);
        timeSelectionGroup.add(comboBox);


        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(e -> System.exit(0));

        menu.add("Import Calendar(TBD)");
        menu.add(exportCalendar);
        menu.addSeparator();
        menu.add(spinners);
        menu.add(comboBox);
        menu.addSeparator();
        menu.add(quitMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        return menuBar;
    }

    private static JPanel createWeekNumber() {
        JPanel weekNumberContainer = new JPanel();
        JLabel weekNumber = new JLabel("<html><h1>Week " + WeekCalendar.getWeek() + "</h1></html>");
        weekNumberContainer.add(weekNumber);
        return weekNumberContainer;
    }

    private static JPanel createWeekView() {
        JPanel weekViewContainer = new JPanel(new GridBagLayout());
        GridBagConstraints weekConstraints = new GridBagConstraints();
        weekConstraints.fill = GridBagConstraints.BOTH;
        weekViewContainer.setBackground(Color.black);
        weekConstraints.weightx = 1;
        weekConstraints.weighty = 1;
        for (LocalDateTime days : WeekCalendar.getDays()) {
            weekViewContainer.add(new DayContainer(days), weekConstraints);
        }
        return weekViewContainer;
    }

    static void createMainWindow() {
        JFrame frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));

        JMenuBar menuBar = createMenu(frame);
        frame.setJMenuBar(menuBar);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        container.add(createWeekNumber());
        container.add(createWeekView());

        frame.add(container);
        frame.pack();
        frame.setVisible(true);
    }

    private static class Settings {
        private boolean spinner = true;

        private boolean isSpinner() {
            return spinner;
        }

        private void setSpinner(boolean spinner) {
            this.spinner = spinner;
        }
    }

    private static class CreateEventWindow extends JFrame {
        private final JFrame frame;
        private TextArea summaryArea;
        private int hours = 12;
        private int minutes = 0;
        private JSpinner hoursSpinner;
        private JSpinner minutesSpinner;


        CreateEventWindow(LocalDateTime localDateTime) {
            frame = new JFrame("Create Event");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setPreferredSize(new Dimension(600, 400));
            JPanel container = new JPanel();
            container.setLayout(new GridBagLayout());

            GridBagConstraints eventCreationConstraints = new GridBagConstraints();
            eventCreationConstraints.fill = GridBagConstraints.NONE;
            eventCreationConstraints.gridx = 0;
            eventCreationConstraints.gridy = 0;
            eventCreationConstraints.weightx = 0;
            eventCreationConstraints.weighty = 0;
            if (settings.isSpinner()) {
                container.add(createSpinners(), eventCreationConstraints);
            } else {
                container.add(createComboBox(), eventCreationConstraints);
            }

            eventCreationConstraints.fill = GridBagConstraints.BOTH;
            eventCreationConstraints.gridx = 1;
            eventCreationConstraints.gridy = 0;
            eventCreationConstraints.weightx = 1.0;
            eventCreationConstraints.weighty = 1.0;
            container.add(createSummaryArea(), eventCreationConstraints);

            eventCreationConstraints.fill = GridBagConstraints.NONE;
            eventCreationConstraints.gridx = 1;
            eventCreationConstraints.gridy = 2;
            eventCreationConstraints.weightx = 0;
            eventCreationConstraints.weighty = 0;
            container.add(createButtons(localDateTime), eventCreationConstraints);

            frame.add(container);
            frame.pack();
            frame.setVisible(true);
        }

        private JPanel createComboBox() {
            JPanel hoursContainer = new JPanel();
            hoursContainer.setLayout(new BoxLayout(hoursContainer, BoxLayout.LINE_AXIS));
            JLabel hoursLabel = new JLabel("<html><h2>Hours</h2></html>");
            Integer[] hoursArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
            JComboBox<Integer> hoursComboBox = new JComboBox<>(hoursArr);
            hoursComboBox.setSelectedIndex(12);
            hoursComboBox.addActionListener(e -> setHours((Integer) hoursComboBox.getSelectedItem()));

            hoursContainer.add(hoursLabel);
            hoursContainer.add(hoursComboBox);

            JPanel minutesContainer = new JPanel();
            minutesContainer.setLayout(new BoxLayout(minutesContainer, BoxLayout.LINE_AXIS));
            JLabel minutesLabel = new JLabel("<html><h2>Minutes</h2></html>");
            Integer[] minutesArr = {0, 15, 30, 45};
            JComboBox<Integer> minutesComboBox = new JComboBox<>(minutesArr);
            minutesComboBox.setSelectedIndex(0);
            minutesComboBox.addActionListener(e -> setMinutes((Integer) minutesComboBox.getSelectedItem()));
            minutesContainer.add(minutesLabel);
            minutesContainer.add(minutesComboBox);

            JPanel comboBoxContainer = new JPanel();

            comboBoxContainer.setLayout(new BoxLayout(comboBoxContainer, BoxLayout.PAGE_AXIS));
            comboBoxContainer.add(hoursContainer);
            comboBoxContainer.add(minutesContainer);
            return comboBoxContainer;
        }

        private JPanel createSpinners() {
            JPanel hoursContainer = new JPanel();
            hoursContainer.setLayout(new BoxLayout(hoursContainer, BoxLayout.PAGE_AXIS));
            JLabel hoursLabel = new JLabel("<html><h2>Hours</h2></html>");
            hoursContainer.add(hoursLabel);
            SpinnerNumberModel hours = new SpinnerNumberModel(12, 0, 23, 1);
            hoursSpinner = new JSpinner(hours);
            hoursContainer.add(hoursSpinner);

            JPanel minutesContainer = new JPanel();
            minutesContainer.setLayout(new BoxLayout(minutesContainer, BoxLayout.PAGE_AXIS));
            JLabel minutesLabel = new JLabel("<html><h2>Minutes</h2></html>");
            minutesContainer.add(minutesLabel);
            SpinnerNumberModel minutes = new SpinnerNumberModel(0, 0, 59, 1);
            minutesSpinner = new JSpinner(minutes);
            minutesContainer.add(minutesSpinner);

            JPanel timeContainer = new JPanel();
            timeContainer.setLayout(new BoxLayout(timeContainer, BoxLayout.PAGE_AXIS));
            timeContainer.add(hoursContainer);
            timeContainer.add(minutesContainer);
            return timeContainer;
        }

        private JPanel createSummaryArea() {
            JPanel summaryContainer = new JPanel();
            summaryContainer.setLayout(new GridBagLayout());
            GridBagConstraints summaryContainerConstraints = new GridBagConstraints();
            JLabel summaryLabel = new JLabel("<html><h2>Summary</h2></html>");
            summaryArea = new TextArea(1, 10);


            summaryContainerConstraints.gridx = 0;
            summaryContainerConstraints.gridy = 0;
            summaryContainer.add(summaryLabel, summaryContainerConstraints);

            summaryContainerConstraints.fill = GridBagConstraints.BOTH;
            summaryContainerConstraints.gridx = 0;
            summaryContainerConstraints.gridy = 1;
            summaryContainerConstraints.weightx = 1.0;
            summaryContainerConstraints.weighty = 1.0;
            summaryContainer.add(summaryArea, summaryContainerConstraints);

            return summaryContainer;

        }

        private JPanel createButtons(LocalDateTime localDateTime) {
            JPanel buttonContainer = new JPanel();
            buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.LINE_AXIS));

            JButton cancelButton = new JButton("Cancel");
            ActionListener cancelButtonListener = e -> frame.dispose();
            cancelButton.addActionListener(cancelButtonListener);

            JButton createButton = new JButton("Create");
            ActionListener createButtonListener = e -> {
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
            };
            createButton.addActionListener(createButtonListener);

            buttonContainer.add(cancelButton);
            buttonContainer.add(createButton);
            return buttonContainer;
        }

        private JFrame getFrame() {
            return this.frame;
        }

        private int getHours() {
            if (settings.isSpinner()) {
                return (int) hoursSpinner.getValue();
            } else {
                return hours;
            }
        }

        private void setHours(int hours) {
            this.hours = hours;

        }

        private int getMinutes() {
            if (settings.isSpinner()) {
                return (int) minutesSpinner.getValue();
            } else {
                return minutes;
            }
        }

        private void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        private String getSummary() {
            return summaryArea.getText();
        }

    }

    static class DayContainer extends JPanel {
        private final LocalDateTime localDateTime;
        private final String dayName;
        private final int date;
        private final int month;
        private final EventList eventList;
        private JButton removeEventButton;

        DayContainer(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            this.dayName = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
            this.date = localDateTime.getDayOfMonth();
            this.month = localDateTime.getMonthValue();
            this.eventList = createEventList();

            this.setLayout(new GridBagLayout());
            this.setBorder(createBorderAndTitle());
            GridBagConstraints dayContainerConstraints = new GridBagConstraints();


            dayContainerConstraints.fill = GridBagConstraints.BOTH;
            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 0;
            dayContainerConstraints.weighty = 1.0;
            dayContainerConstraints.weightx = 1.0;
            this.add(eventList, dayContainerConstraints);


            dayContainerConstraints.gridx = 0;
            dayContainerConstraints.gridy = 1;
            dayContainerConstraints.fill = GridBagConstraints.HORIZONTAL;
            dayContainerConstraints.anchor = GridBagConstraints.PAGE_END;
            dayContainerConstraints.weighty = 0.0;
            dayContainerConstraints.weightx = 0.0;
            this.add(createButtons(), dayContainerConstraints);
        }

        private EventList createEventList() {
            // TODO: Move the Listener to EventList.
            // The Listener should be in EventList. Unfortunately removeEventButton is out of scope there.
            EventList list = new EventList();
            ListSelectionListener checkIfListEmpty = e -> {
                if (!e.getValueIsAdjusting()) {
                    removeEventButton.setEnabled(list.getSelectedIndex() != -1);
                }
            };
            list.setEventListListener(checkIfListEmpty);
            list.populateEventList(localDateTime);
            return list;


        }

        private JPanel createButtons() {
            JButton addEventButton = new JButton("<html><h3>Add note</h3></html>");
            ActionListener addEventListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateEventWindow askEvent = new CreateEventWindow(DayContainer.this.localDateTime);
                    askEvent.getFrame().addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            eventList.clear();
                            eventList.populateEventList(DayContainer.this.localDateTime);
                            super.windowClosed(e);
                        }

                    });

                }
            };
            addEventButton.addActionListener(addEventListener);


            removeEventButton = new JButton("<html><h3>Delete note</h3></html>");
            removeEventButton.setEnabled(false);
            ActionListener removeEventListener = e -> {
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
                eventList.populateEventList(this.localDateTime);
            };
            removeEventButton.addActionListener(removeEventListener);

            JPanel buttonContainer = new JPanel();
            buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.PAGE_AXIS));
            buttonContainer.add(addEventButton);
            buttonContainer.add(removeEventButton);
            return buttonContainer;

        }

        private TitledBorder createBorderAndTitle() {
            Border blackLineBorder = BorderFactory.createLineBorder(Color.black);
            TitledBorder dayBorder;
            if (date == WeekCalendar.getNow().getDayOfMonth()) {
                dayBorder = BorderFactory.createTitledBorder(blackLineBorder, "<html><h2 style=\"color:red\">" + date + "/" + month + " - " + dayName + "</h2></html>");
            } else {
                dayBorder = BorderFactory.createTitledBorder(blackLineBorder, "<html><h2>" + date + "/" + month + " - " + dayName + "</h2></html>");
            }
            return dayBorder;
        }
    }
}