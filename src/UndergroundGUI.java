import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;



public class UndergroundGUI extends JFrame implements ActionListener {
    private String accessToken;
    private JTextField artistField;
    private String id;

    private ArrayList<Artist> relatedArtists;

    public UndergroundGUI() {
        super("#SoUnderground");
        init();
    }

    private void init() {
        // setting up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocation(300, 400);

        // create the MenuBar and menu component

        // add "File" and "Help" menus to the MenuBar

        // create the big text area located in the middle

        // create welcome label
        JLabel welcomeLabel = new JLabel("#SoUnderground!");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        //JLabel explanationLabel = new JLabel("GET AHEAD OF THE WAVE!! Enter the name of an artist you like below, and find out 10 related ones who you may not know!");
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(2, 1));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        //welcomePanel.add(explanationLabel);

        // create the components at the bottom
        JLabel label = new JLabel("Enter Artist");
        artistField = new JTextField(50);
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
        add(combinedPanels, BorderLayout.NORTH);

        // --- SETTING UP EVENT HANDLING ----
        //setting up buttons to use ActionListener interface and actionPerformed method
        sendButton.addActionListener(this);

        // display the frame!
        setVisible(true);

        setAccessToken();
    }

    private void setAccessToken() {
        String url = "https://accounts.spotify.com/api/token";
        String clientId = "55449f06d4c542d2a4b556321f3e4de0";
        String clientSecret = "51e230c6aca640d0bc937cb5195fcc70";
        String access = "";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret))
                .build();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject token = new JSONObject(response.body());
            access = token.getString("access_token");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        accessToken = access;

//        String url2 = "https://api.spotify.com/v1/search?q=taylor+swift&type=artist";
//        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create(url2))
//                .header("Authorization", "Bearer " + accessToken).build();
//        try {
//            HttpClient client = HttpClient.newHttpClient();
//            HttpResponse<String> response = client.send(request2, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.body());
//        } catch (Exception e) {
//            System.out.print(e.getMessage());
//        }
    }

    public void setInitialArtistID(String name) {

        String n = "";
        if (name.indexOf(" ") != -1) {
            String[] arr = name.split(" ");
            for (String x : arr) {
                n += x + "+";
            }
            n = n.substring(0, n.length() - 1);
        } else {
            n = name;
        }
        System.out.println(n);

        String url = "https://api.spotify.com/v1/search?q=" + n + "&type=artist&limit=1&offset=0";
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken).build();
        HttpResponse<String> response = null;
        String urlResponse = "";
        try {
            HttpClient client = HttpClient.newHttpClient();
            response = client.send(request2, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
            System.out.println(urlResponse);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONObject artistList = jsonObj.getJSONObject("artists");
        JSONArray items = artistList.getJSONArray("items");
        JSONObject artist = items.getJSONObject(0);
        String id1 = artist.getString("id");
        System.out.println(id1);
        id = id1;
    }
    public void setRelatedArtists() {
        String url = "https://api.spotify.com/v1/artists/" + id + "/related-artists";
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken).build();
        HttpResponse<String> response = null;
        String urlResponse = "";
        try {
            HttpClient client = HttpClient.newHttpClient();
            response = client.send(request2, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
            System.out.println(urlResponse);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

//        JSONArray relatedList = new JSONArray(urlResponse);
//        for (int i = 0; i < 10; i++) {
//            JSONObject artist = relatedList.getJSONObject(i);
//            String name = artist.getString("name");
//            System.out.println(name);
//            String id = artist.getString("id");
//            int popularity = artist.getInt("popularity");
//            JSONObject f = artist.getJSONObject("followers");
//            int followers = f.getInt("total");
//            Artist y = new Artist(name, followers, id, popularity);
//            relatedArtists.add(y);
//        }
    }

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
                setInitialArtistID(artistName);
                System.out.println(id);
                setRelatedArtists();
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
}
