import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.awt.BorderLayout;
import java.awt.GridLayout;


import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Type; // This is the correct Type!
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Paths;



public class NoteApp extends JFrame {

    private JTextField headingField;
    private JTextArea textArea;
    private JButton addButton, publishButton;

    // To store notes before publishing
    private List<Note> bufferNotes = new ArrayList<>();

    private static final String JSON_FILE = "data.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public NoteApp() {
        setTitle("Note Adder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        // UI Layout
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 5, 5));

        headingField = new JTextField();
        textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);

        addButton = new JButton("Add");
        publishButton = new JButton("Publish");

        inputPanel.add(new JLabel("Heading:"));
        inputPanel.add(headingField);
        inputPanel.add(new JLabel("Text:"));
        inputPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(publishButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Button Actions
        addButton.addActionListener(this::onAdd);
        publishButton.addActionListener(this::onPublish);
    }

    private void onAdd(ActionEvent e) {
        String heading = headingField.getText().trim();
        String text = textArea.getText().trim();

        if (heading.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both heading and text are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        bufferNotes.add(new Note(heading, date, text));

        headingField.setText("");
        textArea.setText("");
        JOptionPane.showMessageDialog(this, "Note added to buffer. Click 'Publish' to save to JSON.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onPublish(ActionEvent e) {
        if (bufferNotes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No notes to publish.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            List<Note> allNotes = readExistingNotes();
            allNotes.addAll(bufferNotes);

            try (Writer writer = new FileWriter(JSON_FILE)) {
                gson.toJson(allNotes, writer);
            }

            bufferNotes.clear();
            JOptionPane.showMessageDialog(this, "Notes published to data.json!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to write to JSON file.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private List<Note> readExistingNotes() {
        try {
            if (!Files.exists(Paths.get(JSON_FILE))) {
                return new ArrayList<>();
            }
            Reader reader = new FileReader(JSON_FILE);
            java.lang.reflect.Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
            List<Note> notes = gson.fromJson(reader, listType);
            reader.close();
            return notes != null ? notes : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        // Ensure GSON is available
        SwingUtilities.invokeLater(() -> new NoteApp().setVisible(true));
    }

    // Note class for JSON mapping
    static class Note {
        String heading;
        String editedDate;
        String editedText;

        public Note(String heading, String editedDate, String editedText) {
            this.heading = heading;
            this.editedDate = editedDate;
            this.editedText = editedText;
        }
    }
}
