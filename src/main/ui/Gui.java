package ui;

import model.Event;
import model.EventLog;
import model.PetSitter;
import model.PetSitters;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Represents the graphical user interface for pet-sitter pool management app
public class Gui extends WindowAdapter implements ActionListener {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private static final String JSON_LOCATION = "./data/petSittersPool.json";
    private static final String[] sortOptions = {"User Id", "Full Name", "City", "Experience", "Hourly rate", "Rating"};
    private static final String[] psInfoItems = {
            "User Id: ",
            "First Name: ",
            "Last Name: ",
            "City: ",
            "Experience: ",
            "Hourly Rate: ",
            "Rating"
    };
    private PetSitters psPool;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private final JFrame mainWin;
    private JButton delBtn;
    private JTextField delInputField;
    private JPanel addPanel;
    private JButton addBtn;
    private JTextField[] addFields;
    private JPanel displayPanel;
    private JComboBox sortBy;
    private JScrollPane scrollDisplayPane;

    private final ImageIcon msgIcon = new ImageIcon("./data/pawprint.png"); // icons from www.flaticon.com

    public Gui() {
        mainWin = new JFrame("Pet-sitter Pool Management App");
        mainWin.setSize(WIDTH, HEIGHT);
        mainWin.setLayout(null);

        initializeFields();
        mainWin.addWindowListener(this);
        initializeDeletePanel();
        initializeAddPanel();

        mainWin.setVisible(true);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        psPool = new PetSitters();
        jsonReader = new JsonReader(JSON_LOCATION);
        jsonWriter = new JsonWriter(JSON_LOCATION);
    }

    // MODIFIES: this
    // EFFECTS: provide options in pop-up to load existing pet-sitter list when open the app
    public void windowOpened(WindowEvent e) {
        int a = JOptionPane.showConfirmDialog(mainWin, "Load existing pet-sitters from database?",
                "Data Loading...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, msgIcon);

        if (a == JOptionPane.NO_OPTION) {
            mainWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } else if (a == JOptionPane.YES_OPTION) {
            loadDatabase();
            mainWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
        initializeDisplayPanel();
        mainWin.setVisible(true);
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: psPool
    // EFFECTS: load existing pet-sitters from file at the start of the application
    public void loadDatabase() {
        try {
            psPool = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainWin, "Data did not load successfully.",
                    "Failed Loading...", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: provide options in pop-up to save most recent pet-sitter list to file,
    //          and print to the console all the logged events when quit the app
    public void windowClosing(WindowEvent e) {
        int a = JOptionPane.showConfirmDialog(mainWin, "Save updated list of pet-sitters? ",
                "Data Saving...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, msgIcon);

        if (a == JOptionPane.CANCEL_OPTION) {
            mainWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } else if (a == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(mainWin, "Any update to the pet-sitters pool is abandoned.",
                    "Data Not Saved...", JOptionPane.WARNING_MESSAGE);
            mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // print to the console all the events that have been logged
            printLogToConsole(EventLog.getInstance());

        } else if (a == JOptionPane.YES_OPTION) {
            saveDataToFile();
            mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // print to the console all the events that have been logged
            printLogToConsole(EventLog.getInstance());
        }
    }

    // Referred and modeled from AlarmSystem https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: print all the logged events to the console when quit the app
    private void printLogToConsole(EventLog el) {
        for (Event next: el) {
            System.out.println(next.toString() + "\n");
        }
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: save updated list of pet-sitters to file before exit the application
    public void saveDataToFile() {
        ImageIcon successIcon = new ImageIcon("./data/catSmile.png"); // icon from www.cleanpng.com
        try {
            jsonWriter.open();
            jsonWriter.write(psPool);
            jsonWriter.close();

            JOptionPane.showMessageDialog(mainWin, "Pet-sitters pool updated and saved to " + JSON_LOCATION,
                    "Saved Successfully!", JOptionPane.INFORMATION_MESSAGE, successIcon);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(mainWin, "Unable to write to file: " + JSON_LOCATION,
                    "Failed Saving...", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the menu panel for the app
    private void initializeDeletePanel() {
        JPanel delPanel = new JPanel();
        delPanel.setLayout(null);
        delPanel.setBounds(5, 5, 340, 50);
        delPanel.setBackground(Color.LIGHT_GRAY);
        delInputField = new JTextField("Id of Pet-sitter to be deleted:");
        delInputField.setFont(new Font("monospace", Font.ITALIC, 12));
        delInputField.setBounds(10, 5, 220, 40);

        Icon delIcon = new ImageIcon("./data/delete.png");
        delBtn = new JButton(delIcon);  // icons from www.flaticon.com
        delBtn.setText("DELETE");
        delBtn.addActionListener(this);
        delBtn.setBounds(230, 5, 100, 40);

        delPanel.add(delInputField, BorderLayout.WEST);
        delPanel.add(delBtn, BorderLayout.EAST);
        mainWin.add(delPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates edit panel under the tool panel
    private void initializeAddPanel() {
        addPanel = new JPanel(new GridLayout(8, 1));
        addPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        addPanel.setBounds(5, 60, 340, 395);
        addPanel.setBackground(Color.LIGHT_GRAY);
        mainWin.add(createEntryFields(), BorderLayout.WEST);

        Icon addIcon = new ImageIcon("./data/add.png");  // icons from www.flaticon.com
        addBtn = new JButton(addIcon);
        addBtn.setText("ADD");
        addBtn.addActionListener(this);
        addPanel.add(addBtn);
    }

    // referred and modeled from https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextInputDemoProject/src/components/TextInputDemo.java
    // MODIFIES: this
    // EFFECTS: create text fields for add pet-sitter panel
    private JComponent createEntryFields() {
        JLabel[] labels = new JLabel[psInfoItems.length];
        addFields = new JTextField[psInfoItems.length];
        for (int i = 0; i < labels.length; i++) {
            addFields[i] = new JTextField();
            addFields[i].setColumns(15);
            labels[i] = new JLabel(psInfoItems[i]);
            labels[i].setLabelFor(addFields[i]);
            addPanel.add(labels[i]);
            addPanel.add(addFields[i]);
        }
        return addPanel;
    }

    // MODIFIES: this, displayPanel, sortBy
    // EFFECTS: creates a display panel to show all the pet-sitters in the pool
    private void initializeDisplayPanel() {
        List<PetSitter> petSitters = psPool.getPetSitters();

        displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBounds(350, 5, 650, 450);

        JPanel sortPanel = new JPanel();
        sortPanel.add(new JLabel("Choose an option to sort: "));
        sortBy = new JComboBox<>(sortOptions);
        sortBy.addActionListener(this);
        sortPanel.add(sortBy);
        sortPanel.setBackground(Color.LIGHT_GRAY);
        displayPanel.add(sortPanel, BorderLayout.NORTH);
        displayPanel.add(createPSDisplayPanel(petSitters), BorderLayout.CENTER);
        mainWin.add(displayPanel, BorderLayout.EAST);
    }

    // REQUIRES: List<PetSitter> is not null
    // MODIFIES: this
    // EFFECTS: create a display scroll panel for pet-sitters
    private JScrollPane createPSDisplayPanel(List<PetSitter> petSitters) {
        String[][] psInfoArr = new String[petSitters.size()][sortOptions.length];

        for (int i = 0; i < petSitters.size(); i++) {
            PetSitter ps = petSitters.get(i);
            psInfoArr[i][0] = ps.getUserId();
            psInfoArr[i][1] = ps.getFullName();
            psInfoArr[i][2] = ps.getCity();
            psInfoArr[i][3] = String.valueOf(ps.getExperience());
            psInfoArr[i][4] = String.valueOf(ps.getHrRate());
            psInfoArr[i][5] = String.valueOf(ps.getRating());
        }

        // CITATION: Referred and modeled from https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
        DefaultTableModel tableModel = new DefaultTableModel(psInfoArr, sortOptions) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable psTable = new JTable();
        psTable.setModel(tableModel);
        psTable.setBounds(350, 50, 650, 400);

        scrollDisplayPane = new JScrollPane(psTable);
        scrollDisplayPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollDisplayPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollDisplayPane;
    }

    // EFFECTS: react to different source and perform task accordingly
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delBtn) {
            removeAPetSitterFromPool();
        }
        if (e.getSource() == addBtn) {
            addAPetSitterToPool();
        }
        if (e.getSource() == sortBy) {
            sortDisplay();
        }
    }

    // MODIFIES: this, psPool, scrollDisplayPane, displayPanel
    // EFFECTS: sort psPool and repaint the display table
    private void sortDisplay() {
        String sortOptionSelected = (String) sortBy.getSelectedItem();
        psPool.sortPetSitters(sortOptionSelected);
        repaintDisplayTable();
    }

    // MODIFIES: this, psPool, scrollDisplayPane, displayPanel
    // EFFECTS: add a pet-sitter to the pool and show in the display panel instantly
    //          show alert pop-up if the userId exists
    private void addAPetSitterToPool() {
        try {
            String psUserName = addFields[0].getText();
            String firstName = addFields[1].getText();
            String lastName = addFields[2].getText();
            String city = addFields[3].getText();
            int experience = Integer.parseInt(addFields[4].getText());
            double hrRate = Double.parseDouble(addFields[5].getText());
            int rating = Integer.parseInt(addFields[6].getText());
            PetSitter userPS = new PetSitter(psUserName, firstName, lastName, city, experience, hrRate);

            if (psPool.addPetSitter(userPS)) {
                userPS.setRating(rating);
                repaintDisplayTable();
            } else {
                JOptionPane.showMessageDialog(mainWin, "This pet-sitter already exist!",
                        "Fail to Add", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainWin, "Some info entered is not in valid form!",
                    "Invalid Input!", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this, psPool, scrollDisplayPane, displayPanel
    // EFFECTS: removes a pet-sitter from the pool and remove from display panel instantly
    private void removeAPetSitterFromPool() {
        String psUserIdEntered = delInputField.getText();
        boolean isRemoved = psPool.removePetSitter(psPool.getPetSitter(psUserIdEntered));
        if (isRemoved) {
            repaintDisplayTable();
        } else {
            JOptionPane.showMessageDialog(mainWin, "This pet-sitter does not exist!",
                    "No Such Pet-sitter!", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this,scrollDisplayPane, displayPanel
    // EFFECTS: repaint pet-sitter display table
    private void repaintDisplayTable() {
        displayPanel.remove(scrollDisplayPane);
        displayPanel.add(createPSDisplayPanel(psPool.getPetSitters()), BorderLayout.CENTER);
        displayPanel.repaint();
        mainWin.setVisible(true);
    }
}
