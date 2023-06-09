import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResultPanel extends JPanel {
    public ResultPanel() {
        JPanel z = new JPanel();
        z.setLayout(new GridLayout(5, 1));
        for (int i = 0; i<5; i++) {
            JPanel p = new JPanel();
            JLabel name = new JLabel(UndergroundAPI.getRelatedArtists().get(i).getName());
            name.setFont(new Font("Helvetica", Font.BOLD, 16));
//            JLabel genres = new JLabel();
//            String gs = " ";
//            for (int m = 0; m < a.get(i).getGenres().length(); m++) {
//                JSONArray g2 = a.get(i).getGenres();
//                String genre = g2.getString(i);
//
//                gs += genre;
//                if (m != a.size()-1) {
//                    gs+= ", ";
//                }
//            }
//            genres.setText("GENRES: " + gs) ;
            p.add(name);
            z.add(p);
//            p.add(genres);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon0 = new ImageIcon("src/bg2.png");
        Image iconImg = icon0.getImage();
        g.drawImage(iconImg, 0, 0, null);



    }
}
