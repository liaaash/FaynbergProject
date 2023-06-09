import javax.swing.*;
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



public class UndergroundGUI extends JFrame {

    private SimpleJPanel jp;
    public static JPanel mainPanel;
    private ResultPanel rp;
    public static CardLayout cardLayout;
    public static UndergroundAPI x;

    public UndergroundGUI() {
        super("#SoUnderground");
        init();
    }



    private void init() {
        // setting up the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 670);
//        cardLayout = new CardLayout();
//        mainPanel = new JPanel(cardLayout);
//        add(mainPanel);
        x = new UndergroundAPI();
        jp = new SimpleJPanel();
        add(jp);
//        rp = new ResultPanel();
//        mainPanel.add("panel1", jp);
//        mainPanel.add("panel2", rp);
//        cardLayout.show(mainPanel, "panel1");
//        x = new
//        x.setAccessToken();
//        x.setInitialArtistID("sza");
//        x.setRelatedArtists();
        setVisible(true);
    }




    // ActionListener interface method, called when a button is click
}
