//This project is made by Ricky Ye, chosen clone out of uncountable clone projects I made that works.
//Cram Buddy 1.2 Beta!
//Voice Control! I hate myself and the online voice APIs for making things so hard so I will bridge Python and Java in here
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
public class CramMain {
    public static JFrame home;
    public static JFrame frame;
    public static String theTitle = "";
    public static String theContent = "";
    public static JFileChooser noteDirectory = new JFileChooser();
    private static File[] files;
    public static String[] fileNames;
    public static File dir;
    //public static int noteChosen = noteDirectory.showOpenDialog(null);

    public static void main(String[] args) { //"/usr/local/bin/python3.11" the directory to the python command execution
        // initialize the files array with an empty array
        files = new File[0];
        //the title screen code
        //frame for the title screen
        JFrame programScreen = new JFrame("Cram Buddy 1.2a");
        programScreen.setSize(500, 400);
        programScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        programScreen.setLocationRelativeTo(null);
        programScreen.setResizable(true);
        //programScreen.getContentPane().setBackground(Color.DARK_GRAY);

        JLayeredPane layers = new JLayeredPane();
        layers.setBounds(0, 0, 500, 400);

        //label text for the title screen
        JLabel introLabel = new JLabel("Welcome to Cram Buddy!");
        introLabel.setBounds(0, 0, 500, 50);
        introLabel.setHorizontalAlignment(JLabel.CENTER);
        introLabel.setVerticalAlignment(JLabel.CENTER);
        introLabel.setFont(new Font("Wawati TC", Font.BOLD, 35));
        //introLabel.setForeground(Color.LIGHT_GRAY);

        //instructional label text for the title screen
        JLabel instruction = new JLabel("Press Enter to proceed");
        instruction.setBounds(0, 100, 500, 50);
        instruction.setHorizontalAlignment(JLabel.CENTER);
        instruction.setVerticalAlignment(JLabel.CENTER);
        instruction.setFont(new Font("Wawati TC", Font.BOLD, 30));
        //instruction.setForeground(Color.LIGHT_GRAY);

        //putting all the pieces onto the layers, and then layer onto the programScreen to make them all visible
        layers.add(introLabel);
        layers.add(instruction);
        programScreen.add(layers);
        programScreen.setVisible(true);

        //if pressed Enter Key, then call confirmationDialogue while dispose of the title screen
        programScreen.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    getNoteDirectory();
                    // Jumps to confirmation dialogue
                    confirmationDialogue();
                    programScreen.setVisible(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Do nothing
            }
        });

    }

    static void confirmationDialogue() {
        //asks whether want to create a brand new note by popping a dialogue, if presses ok, goes to creation page. If pressed anything else, goes to Home page.
        int stashCreation = JOptionPane.showConfirmDialog(null, "Do you want to create a stash note?");
        if (stashCreation == JOptionPane.OK_OPTION) {
            CreationPage();
        } else {
            HomePage();
        }
    }

    static String getNoteDirectory(){
        // get a list of all the .txt files in the directory
        noteDirectory.setDialogTitle("Select the Output folder in the application folder");
        noteDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (noteDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = noteDirectory.getSelectedFile();
                if (selectedFile.exists() && selectedFile.isDirectory()) {
                    files = selectedFile.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".txt");
                        }
                    });
                    // create an array of file names to display in the JList
                    fileNames = new String[files.length + 1];
                    fileNames[0] = "Create a new note";
                    if (files != null) {
                        if (fileNames.length > 1)
                            for (int i = 0; i < files.length; i++) {
                                fileNames[i + 1] = files[i].getName();
                            }
                        } else {
                        return fileNames[0];
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selected file is not a valid directory");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error occurred while accessing file: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    static void HomePage() {
        //the code for home page
        // Start the Python script as a separate process
        try {
            ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/python3.11", "picovoice/sphinx.py");
            Process process = pb.start();

            InputStream inputStream = process.getInputStream();
            PrintWriter outputStream = new PrintWriter(process.getOutputStream());

            // Send wake word to the Python process
            outputStream.println("Google");
            outputStream.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Create a separate thread to continuously read the output from the Python process
            Thread outputReader = new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Process the received wake word event or signal
                        JOptionPane.showMessageDialog(null, "How can I help today?");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            outputReader.start();

            // Wait for the Python process to finish
            process.waitFor();

            // Close the input/output streams
            inputStream.close();
            outputStream.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //general frame for home page
        home = new JFrame("Cram Buddy 1.2a");
        home.setSize(700, 500);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setLocationRelativeTo(null);
        home.setResizable(true);

        // layers for home page
        JLayeredPane homeLayers = new JLayeredPane();
        homeLayers.setBounds(0, 0, 700, 500);

        // welcoming text for home screen
        JLabel homeTitle = new JLabel("Welcome to the Home Page!");
        homeTitle.setBounds(0, 0, 700, 50);
        homeTitle.setHorizontalAlignment(JLabel.CENTER);
        homeTitle.setVerticalAlignment(JLabel.CENTER);
        homeTitle.setFont(new Font("Wawati TC", Font.BOLD, 35));

        // label for the home screen pre much
        JLabel homeLabel1 = new JLabel("Created Notes: ");
        homeLabel1.setBounds(0, 60, 700, 50);
        homeLabel1.setFont(new Font("Wawati TC", Font.BOLD, 20));

        // create a JScrollPane to hold the list of files
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 110, 700, 310);
        homeLayers.add(scrollPane);

        // create a JList to display the list of files
        JList<String> fileList = new JList<String>();
        fileList.setFont(new Font("Wawati TC", Font.PLAIN, 18));
        scrollPane.setViewportView(fileList);

        //the usual visual settings, and action listener yada yada
        JButton createButton = new JButton();
        createButton.setText("Create Note");
        createButton.setIcon(new ImageIcon("com/example/crambuddy/buttonIcon.jpeg"));
        createButton.setFont(new Font("Wawati TC", Font.BOLD, 25));
        createButton.setBackground(Color.LIGHT_GRAY);
        createButton.setForeground(Color.BLACK);
        createButton.setBounds(550, 420, 150, 50);
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreationPage();
            }
        });

        File selectedFile = noteDirectory.getSelectedFile(); //make it able to use it in if condition
        if (selectedFile.isDirectory()) {
            // Update fileNames array with the correct length
            fileNames = new String[files.length + 1];
            fileNames[0] = "Create a new note using Create Button below!"; //By default, non editable, more of an instruction
            if (files.length > 0) { //if the array isn't empty, then it will get all the names for however much of files it can detect in there using for loop
                for (int i = 0; i < files.length; i++) {
                    fileNames[i + 1] = files[i].getName();
                }
            } else {
                //IN CASE if there are no files detected, then it will force user to create something
                JOptionPane.showMessageDialog(null, "Create a note!");
                CreationPage();
            }
            fileList.setListData(fileNames); //make sure the JList lists all the files within the array
        }

        // double-click listener for editing
        fileList.addMouseListener(new MouseAdapter() { //allowing you to directly edit from the files listed on the JList
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { //invokes upon double click, using click count of 2
                    int selectedIndex = fileList.locationToIndex(evt.getPoint());
                    if (selectedIndex > 0) { //basically just ensures you are selecting a file, not something else
                        //not that I know the entire full logic behind this, its more of an algorithm sorta thing
                        //Had to change this based on the countless failed experiments
                        File selectedFile = files[selectedIndex - 1];
                        EditPage(selectedFile); //send you into EditPage, while passing the selected file to the class so it doesn't get confused what u wanna edit
                    }
                }
            }
        });

            //add them all into the layers and frame
            homeLayers.add(homeTitle);
            homeLayers.add(homeLabel1);
            homeLayers.add(createButton);
            home.add(homeLayers);
            home.setVisible(true);
        }


        static void CreationPage() {
            //Creation Page Code
            //Frame for the Creation Page, Exit entire program upon close button pressed
            frame = new JFrame("Note Creation Wizard");
            frame.setSize(700, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true); // Make the frame resizable

            //layers for the Creation Page, ran out of the variable name ideas
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setBounds(0, 0, 700, 500);

            //Label text for the Creation Page
            JLabel StashLabel = new JLabel("Stash Note Creation Page");
            StashLabel.setBounds(0, 0, 700, 50);
            StashLabel.setHorizontalAlignment(JLabel.CENTER);
            StashLabel.setVerticalAlignment(JLabel.CENTER);
            StashLabel.setFont(new Font("Wawati TC", Font.BOLD, 35));

            //Text field for the user to name a title for the note
            JTextArea title = new JTextArea("Untitled");
            title.setBounds(0, 80, 300, 50);

            //Text field for the user to write whatever the content that the user wanna write in
            JTextArea content = new JTextArea("Content");
            content.setBounds(0, 160, 500, 500);

            //save button, creates a new file within the project folder, input the user's written title and the contents into a separate text file.
            JButton saveButton = new JButton();
            saveButton.setText("Save");
            saveButton.setIcon(new ImageIcon("com/example/crambuddy/buttonIcon.jpeg"));
            saveButton.setFont(new Font("Wawati TC", Font.BOLD, 30));
            saveButton.setBackground(Color.LIGHT_GRAY);
            saveButton.setForeground(Color.BLACK);
            saveButton.setBounds(550, 420, 150, 50);
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // get the title and content from the text fields
                    String theTitle = title.getText();
                    String theContent = content.getText();
                    try {
                        // create a FileWriter to write to the file
                        dir = noteDirectory.getSelectedFile();
                        String directory1 = dir.getPath();
                        FileWriter writer = new FileWriter(directory1 + "/" + theTitle + ".txt");

                        // write the content to the file
                        writer.write(theContent);

                        // close the FileWriter
                        writer.close();

                        // display a message dialog to show that the file was saved
                        JOptionPane.showMessageDialog(null, "File saved successfully!");

                        int secondNoteOption = JOptionPane.showConfirmDialog(null, "Do you want to create another note now?");
                        switch (secondNoteOption){
                            case JOptionPane.YES_OPTION:
                                // clear the text fields
                                title.setText("");
                                content.setText("");
                                break;
                            case JOptionPane.NO_OPTION:
                                HomePage();
                                break;
                            case JOptionPane.CANCEL_OPTION:
                                int exitOption = JOptionPane.showConfirmDialog(null, "Do you want to exit the program?");
                                if (exitOption == JOptionPane.YES_OPTION){
                                    System.exit(0);
                                } else {
                                    int askAgain = JOptionPane.showConfirmDialog(null, "Do you want to create another note?");
                                    if (askAgain == JOptionPane.YES_OPTION){
                                        // clear the text fields
                                        title.setText("");
                                        content.setText("");
                                    } else {
                                        HomePage();
                                    }
                                }
                                break;
                            case JOptionPane.CLOSED_OPTION:
                                HomePage();
                                break;
                            default:
                                HomePage();
                                break;
                        }
                    } catch (IOException ex) {
                        // display an error message if there was an error writing to the file
                        //JOptionPane.showMessageDialog(null, "Error saving file.");
                        ex.printStackTrace(); // Prints the error message to the console
                        //JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Displays the error message in a dialog box
                    }
                }
            });

            //Fail safe button to return to HomePage
            JButton homePage = new JButton("Home Page");
            homePage.setFont(new Font("Wawati TC", Font.BOLD, 30));
            homePage.setBackground(Color.LIGHT_GRAY);
            homePage.setForeground(Color.BLACK);
            homePage.setBounds(0, 420, 150, 50);
            homePage.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    HomePage();
                }
            });

            //unless the save button pressed, everything has to be visible, add up with the set bound size.
            layeredPane.add(StashLabel);
            layeredPane.add(title);
            layeredPane.add(content);
            layeredPane.add(saveButton);
            frame.add(layeredPane);
            frame.setBounds(0, 0, 700, 500);
            frame.setVisible(true);
        }
    static void EditPage(File file) {
        // Where the edit page comes into the play, but better?
        // Frame for the Edit Page
        JFrame frame = new JFrame("Note Editing");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true); // Make the frame resizable

        // Layers for the Edit Page
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 700, 500);

        // Label text for the Edit Page, the usual settings
        JLabel editLabel = new JLabel("Edit Note");
        editLabel.setBounds(0, 0, 700, 50);
        editLabel.setHorizontalAlignment(JLabel.CENTER);
        editLabel.setVerticalAlignment(JLabel.CENTER);
        editLabel.setFont(new Font("Wawati TC", Font.BOLD, 35));

        // Text field for editing the note content
        JTextArea content = new JTextArea();
        content.setBounds(0, 60, 700, 350);
        content.setFont(new Font("Wawati TC", Font.PLAIN, 18));

        // Read the content from the file and set it to the text area using BufferedReader
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file)); //creates and initializes a buffered reader
            StringBuilder sbuilder = new StringBuilder(); //creates a new StringBuilder that will add contents
            String line; //a string to be used
            while ((line = reader.readLine()) != null) { //a while loop that basically read the entire file until it can no longer find any more lines to read
                sbuilder.append(line);
                sbuilder.append("\n");
            }
            reader.close();
            content.setText(sbuilder.toString()); //saves and set the edited content into the file as a whole
        } catch (IOException e) {
            e.printStackTrace(); //catch in case any error, don't want the program to freeze instead with errors all over the IDE
            JOptionPane.showMessageDialog(null, "Error occurred while reading the file: " + e.getMessage()); //shows a prompt telling user the error
        }

        // Save button for updating the note content, basically copied the CreationPage one XD
        JButton saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setIcon(new ImageIcon("com/example/crambuddy/buttonIcon.jpeg"));
        saveButton.setFont(new Font("Wawati TC", Font.BOLD, 30));
        saveButton.setBackground(Color.LIGHT_GRAY);
        saveButton.setForeground(Color.BLACK);
        saveButton.setBounds(550, 420, 150, 50);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the updated content from the text area
                String updatedContent = content.getText();
                // Write the updated content back to the file
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(updatedContent);
                    writer.close();
                    JOptionPane.showMessageDialog(null, "File saved successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while saving the file: " + ex.getMessage());
                }
            }
        });

        // Make em all visible
        layeredPane.add(editLabel);
        layeredPane.add(content);
        layeredPane.add(saveButton);
        frame.add(layeredPane);
        frame.setVisible(true);
    }

}

//The end for this version for now!