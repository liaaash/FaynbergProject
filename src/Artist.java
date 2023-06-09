import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Artist {
    private String name;
    private JSONArray genres;
    private String id;
    private int popularity;
    private JSONObject img;

    private ArrayList<Artist> relatedArtists;

    public Artist(String name, JSONArray genres, String id, int popularity, JSONObject img) {
        this.name = name;
        this.genres = genres;
        this.id = id;
        this.popularity = popularity;
        this.img = img;
        relatedArtists = UndergroundAPI.relatedArtists;
    }

    public String getName(){ return name; }
    public String getID(){ return id; }
    public JSONObject getImg() { return img; }
    public JSONArray getGenres() { return genres; }

}
