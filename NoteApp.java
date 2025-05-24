import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoteApp extends JFrame {
    private JTextField headingField;
    private JTextArea textArea;
    private JButton addButton, publishButton;

    public NoteApp() {
        setTitle("NoteApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        headingField = new JTextField();
        textArea = new JTextArea(8, 30);
        addButton = new JButton("Add");
        publishButton = new JButton("Publish");

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Heading:"), BorderLayout.NORTH);
        panel.add(headingField, BorderLayout.CENTER);

        JPanel textPanel = new JPanel(new BorderLayout(5, 5));
        textPanel.add(new JLabel("Text:"), BorderLayout.NORTH);
        textPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(publishButton);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Add button action
        addButton.addActionListener(e -> addNote());

        // Publish button action
        publishButton.addActionListener(e -> publishToGit());
    }

    private void addNote() {
        String heading = headingField.getText().trim();
        String text = textArea.getText().trim();
        if (heading.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both heading and text are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Note note = new Note(heading, getCurrentDate(), text);

        java.util.List<Note> notes = readNotesFromFile();
        notes.add(note);
        writeNotesToFile(notes);

        JOptionPane.showMessageDialog(this, "Note added to data.json!", "Success", JOptionPane.INFORMATION_MESSAGE);
        headingField.setText("");
        textArea.setText("");
    }

    private void publishToGit() {
        try {
            // Stage the data.json file
            runCommand("git add data.json");
            // Commit with a message
            runCommand("git commit -m \"adding a new entry\"");
            // Push to remote
            runCommand("git push");
            JOptionPane.showMessageDialog(this, "Changes committed and pushed to GitHub!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Git operation failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runCommand(String command) throws IOException, InterruptedException {
        String[] cmd;
        if (isWindows()) {
            cmd = new String[] { "cmd.exe", "/c", command };
        } else {
            cmd = new String[] { "/bin/sh", "-c", command };
        }
        Process process = Runtime.getRuntime().exec(cmd);
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String error = new String(process.getErrorStream().readAllBytes());
            throw new IOException("Command failed: " + command + "\n" + error);
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private java.util.List<Note> readNotesFromFile() {
    File file = new File("data.json");
    if (!file.exists()) return new java.util.ArrayList<>();
    try (Reader reader = new FileReader(file)) {
        Gson gson = new Gson();
        java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<java.util.List<Note>>() {}.getType();
        java.util.List<Note> notes = gson.fromJson(reader, listType);
        return notes != null ? notes : new java.util.ArrayList<>();
    } catch (Exception e) {
        e.printStackTrace();
        return new java.util.ArrayList<>();
    }
}


    private void writeNotesToFile(java.util.List<Note> notes) {
        try (Writer writer = new FileWriter("data.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(notes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NoteApp().setVisible(true));
    }

    // Note class
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
