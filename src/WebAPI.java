import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class WebAPI {
    public static void getNowPlaying() {
        Scanner scan = new Scanner(System.in);
        String APIkey = "2035469c18fb15df308afe5383002a59"; // your personal API key on TheMovieDatabase
        String queryParameters = "?api_key=" + APIkey;
        String endpoint = "https://api.themoviedb.org/3/account/lidagoat/favorite/movies";
        String url = endpoint + queryParameters;
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

        // when determining HOW to parse the returned JSON data,
        // first, print out the urlResponse, then copy/paste the output
        // into the online JSON parser: https://jsonformatter.org/json-parser
        // use the visual model to help you determine how to parse the data!
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONArray movieList = jsonObj.getJSONArray("results");
        for (int i = 0; i < movieList.length(); i++) {
            JSONObject movieObj = movieList.getJSONObject(i);
            String movieTitle = movieObj.getString("title");
            int movieID = movieObj.getInt("id");
            String posterPath = movieObj.getString("poster_path");
            String fullPosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
            System.out.println(movieID + " " + movieTitle + " " + fullPosterPath);
        }



        System.out.print("enter movie ID: ");
        String id = scan.nextLine();
        String endpoint2 = "https://api.themoviedb.org/3/movie/" + id;
        String url2 = endpoint2 + queryParameters;
        try {
            URI myUri = URI.create(url2); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            url2 = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JSONObject jsonObj2 = new JSONObject(url2);
        String title = jsonObj2.getString("title");
        String page = jsonObj2.getString("homepage");
        String overview = jsonObj2.getString("overview");
        String release = jsonObj2.getString("release_date");
        int runtime = jsonObj2.getInt("runtime");
        int revenue = jsonObj2.getInt("revenue");
        System.out.println("title: " + title);
        System.out.println("homepage: " + page);
        System.out.println("overview: " + overview);
        System.out.println("released: " + release);
        System.out.println("runtime: " + runtime + " minutes");
        System.out.println("revenue: $" + revenue);

        // challenge: getting out the genres
        System.out.println("genres:");
        JSONArray genres = jsonObj2.getJSONArray("genres");
        for (int i = 0; i < genres.length(); i++) {
            JSONObject obj = genres.getJSONObject(i);
            String genre = obj.getString("name");
            System.out.println(genre);
        }




    }

}
