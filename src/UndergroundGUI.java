import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.json.JSONArray;
import org.json.JSONObject;


public class UndergroundGUI extends JFrame implements ActionListener, ItemListener, ChangeListener {
    private JTextField artistField;
    public UndergroundGUI() {
        super("#SoUnderground");
        init();
    }

    private void init() {
        // setting up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocation(300, 50);

        // create the MenuBar and menu component

        // add "File" and "Help" menus to the MenuBar

        // create the big text area located in the middle
        JTextArea textArea = new JTextArea();

        // create welcome label
        JLabel welcomeLabel = new JLabel("#SoUnderground!");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        JLabel explanationLabel = new JLabel("GET AHEAD OF THE WAVE!! Enter the name of an artist you like below, and find out 10 related ones who you may not know!");
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(explanationLabel);

        // create the components at the bottom
        JLabel label = new JLabel("Enter Artist");
        artistField = new JTextField();
        JButton sendButton = new JButton("Send");

        // create a panel for organizing the components at the bottom
        JPanel panel = new JPanel(); // a "panel" is not visible

        // add bottom components to the panel, in left-to-right order
        panel.add(label);
        panel.add(artistField);
        panel.add(sendButton);

        // creating a third panel to place slider and bottom panels vertically
        // (allows two rows of UI elements to be displayed)
        JPanel combinedPanels = new JPanel();
        combinedPanels.setLayout(new GridLayout(2, 1));
        combinedPanels.add(welcomePanel, BorderLayout.NORTH);
        combinedPanels.add(panel, BorderLayout.SOUTH);

        // add the menu bar to the TOP of the frame, the big white text area
        // to the MIDDLE of the frame, and the "combinedPanels" (which has
        // the label, slider, text box, buttons, and checkboxes) at the BOTTOM
        add(textArea, BorderLayout.CENTER);
        add(combinedPanels, BorderLayout.SOUTH);

        // --- SETTING UP EVENT HANDLING ----
        //setting up buttons to use ActionListener interface and actionPerformed method
        sendButton.addActionListener(this);

        // display the frame!
        setVisible(true);
    }

    public String getInitialArtistID(String name) {
        String n = "";
        if (name.indexOf(" ") == -1) {
            String[] arr = name.split(" ");
            for (String x: arr) {
                n += x + "+";
            }
            n = n.substring(0, n.length()-2);
        }
        // https://api.spotify.com/v1/search?q=Taylor+Swift&type=artist&limit=1&offset=0
        String url = "https://api.spotify.com/v1/search?q=" + n + "&type=artist&limit=1&offset=0";
        String urlResponse = "";
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONArray artistList = jsonObj.getJSONArray("artists");
        JSONObject artist = artistList.getJSONObject(0);
        String id = artist.getString("id");
        return id;
    }

    public ArrayList<Artistq>

    // ActionListener interface method, called when a button is clicked
    public void actionPerformed(ActionEvent ae) {
        // cast ae to a JButton object since we want to call the getText method on it;
        // casting is needed since getSource() returns Object type, NOT a JButton
        Object source = ae.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            String text = button.getText();

            if (text.equals("Send")) {
                String artistName = artistField.getText();
            }
    }

    // ItemListener interface method, called when EITHER check box is toggled!
        /*  public void itemStateChanged(ItemEvent e) {
        // cast e to a JCheckBox object since we want to call the getText method on it;
        // casting is needed since getSource() returns Object type, NOT a JCheckBox
        Object source = e.getSource();
        JCheckBox cb = (JCheckBox) source;
        String cbText = cb.getText();

        int checkBoxOnOrOff = e.getStateChange(); // 1 for selected, 2 for deselected
        if (checkBoxOnOrOff == 1) {
            welcomeLabel.setText(cbText + " box SELECTED!");
        } else if (checkBoxOnOrOff == 2) {
            welcomeLabel.setText(cbText + " box DESELECTED!");
        }

        // we don't "print" with GUI based apps, but printing
        // can still be helpful for testing and debugging!
        System.out.println("Current state: yes = " + checkBox1.isSelected() + ", no = " + checkBox2.isSelected());
    }

  /*  public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        JSlider slider = (JSlider) source;
        int value = slider.getValue();
        textArea.setText("" + value);
    } */

}