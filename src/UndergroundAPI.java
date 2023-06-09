import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class UndergroundAPI {

    private String accessToken;
    private JTextField artistField;
    private String id;
    private JSONArray genres = new JSONArray();
    public static ArrayList<Artist> relatedArtists = new ArrayList<>();
    public UndergroundAPI() {}

    public JSONArray getGenres() {return genres; }

    public void setAccessToken() {
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
    }

    public static ArrayList<Artist> getRelatedArtists() {
        return relatedArtists;
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

    public ArrayList<Artist> setRelatedArtists() {
        String url = "https://api.spotify.com/v1/artists/" + id + "/related-artists";
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken).build();
        HttpResponse<String> response = null;
        String urlResponse = "";

        try {
            HttpClient client = HttpClient.newHttpClient();
            response = client.send(request2, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        JSONObject related = new JSONObject(urlResponse);
        JSONArray relatedList = related.getJSONArray("artists");
        JSONArray genres = new JSONArray();
        for (int i = 0; i < relatedList.length(); i++) {
            JSONObject artist = relatedList.getJSONObject(i);
            String name = artist.getString("name");
            String id = artist.getString("id");
            genres = artist.getJSONArray("genres");
            int popularity = artist.getInt("popularity");
            JSONObject f = artist.getJSONObject("followers");
            int followers = f.getInt("total");
            JSONArray ps = artist.getJSONArray("images");
            JSONObject p = ps.getJSONObject(0);
            genres = artist.getJSONArray("genres");
            Artist y = new Artist(name, genres, id, popularity, p);
            if (popularity <= 50) {
                relatedArtists.add(y);
                System.out.println(name);
            }
        }
        System.out.println(genres);
// https://api.spotify.com/v1/search?q=genre%3A%22pop+punk%22+genre%3Ascreamo&type=artist&limit=50&offset=0
        String n = "";
        if (genres.length() >= 2) {
            for (int i = 0; i < 1; i++) {
                String g = genres.getString(i);
                if (g.indexOf("&") != 0) {
                    g = g.replace("&", "%26");
                }
                n += "genre%3A";
                if (g.indexOf(" ") != -1) {
                    n+= "%22";
                    String[] arr = g.split(" ");
                    for (String x : arr) {
                        n += x + "+";
                    }
                    n = n.substring(0, n.length() - 1);
                    n+="%22";
                } else {
                    n += g;
                }

            }
        } else {
            for (int i = 0; i < genres.length(); i++) {
                String g = genres.getString(i);
                if (g.indexOf("&") != 0) {
                    g = g.replace("&", "%26");
                }
                n += "genre%3A";
                if (g.indexOf(" ") != -1) {
                    n+= "%22";
                    String[] arr = g.split(" ");
                    for (String x : arr) {
                        n += x + "+";
                    }
                    n = n.substring(0, n.length() - 1);
                    n+="%22";
                } else {
                    n += g;
                }

            }
        }

        System.out.println(n);
        int offset = 0;
        while (relatedArtists.size() < 5) {
            String url1 = "https://api.spotify.com/v1/search?q=" + n + "&type=artist&limit=10&offset=" + offset + "";
            HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create(url1))
                    .header("Authorization", "Bearer " + accessToken).build();
            HttpResponse<String> response1 = null;
            String urlResponse1 = "";
            try {
                HttpClient client = HttpClient.newHttpClient();
                response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
                urlResponse1 = response1.body();
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            JSONObject jsonObj2 = new JSONObject(urlResponse1);
            JSONObject artistList1 = jsonObj2.getJSONObject("artists");
            JSONArray items2 = artistList1.getJSONArray("items");
            for (int i = 0; i < artistList1.length(); i++) {
                JSONObject a = items2.getJSONObject(i);
                int popularity = a.getInt("popularity");
                if (popularity < 50) {
                    String name = a.getString("name");
                    String id = a.getString("id");
                    genres = a.getJSONArray("genres");
                    JSONObject f = a.getJSONObject("followers");
                    int followers = f.getInt("total");
                    JSONArray ps = a.getJSONArray("images");
                    JSONObject p = ps.getJSONObject(0);
                    Artist y = new Artist(name, genres, id, popularity, p);
                    relatedArtists.add(y);
                    System.out.println(name);
                }
            }
            offset+=10;

        }
        return relatedArtists;

    }

}
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