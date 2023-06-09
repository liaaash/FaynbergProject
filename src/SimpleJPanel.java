import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimpleJPanel extends JPanel implements ActionListener {
    private String accessToken;
    private String id;
    private ArrayList<String> genres = new ArrayList<>();
    private ArrayList<Artist> relatedArtists = new ArrayList<>();
    private JTextField artistField;
    private JButton sendButton;
    private boolean change = false;
    public JPanel y;

    private JPanel z;
    //welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));

    public SimpleJPanel() {
        setSize(700, 670);
        sendButton = new JButton("Enter Artist!");
        sendButton.addActionListener(this);
        artistField = new JTextField();
        artistField.setColumns(10);
        add(artistField);
        add(sendButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon0 = new ImageIcon("src/bg.png");
        Image iconImg = icon0.getImage();
        g.drawImage(iconImg, 0, 0, null);
        artistField.setLocation(200, 395);
        artistField.setSize(200, 40);
        artistField.setFont(new Font("Helvetica", Font.BOLD, 16));
        sendButton.setSize(95, 60);
        sendButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        sendButton.setLocation(410, 385);
        if (change == true) {
            z.setLocation(250, 350);
        }


    }

    public void switchPanel() {
        change = true;
        sendButton.setVisible(false);
        artistField.setVisible(false);
        setSize(700, 670);
        z = new JPanel();
        z.setSize(700, 670);
        z.setLayout(new GridLayout(5, 1));
        for (int i = 0; i<5; i++) {
            y = new JPanel();
            y.setBackground(new Color(255, 111, 111));
            JLabel nameLabel = new JLabel(relatedArtists.get(i).getName());
            nameLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
            y.add(nameLabel);
            z.add(y);

        }
        add(z);
    }



    public void actionPerformed(ActionEvent ae) {
        // cast ae to a JButton object since we want to call the getText method on it;
        // casting is needed since getSource() returns Object type, NOT a JButton
        Object source = ae.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            String text = button.getText();

            if (text.equals("Enter Artist!")) {
                String artistName = artistField.getText();
                UndergroundGUI.x.setAccessToken();
                UndergroundGUI.x.setInitialArtistID(artistName);
                System.out.println(id);
                relatedArtists= UndergroundGUI.x.setRelatedArtists();
                switchPanel();
                change = true;
//                UndergroundGUI.cardLayout.show(UndergroundGUI.mainPanel, "panel2");
            }
        }
    }


}
