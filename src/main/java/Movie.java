import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie {
    private int id;
    private String name;
    private String summary;
    private String releaseDate;
    private String director;
    List<String> writers = new ArrayList<String>();
    List<String> genres = new ArrayList<String>();
    List<Integer> cast = new ArrayList<Integer>();
    private double imdbRate;
    private long duration;
    private int ageLimit;
    private double rating = 0;
    private double ratingCount = 0;
    private List<Comment> comments = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<String, Integer>();

    public Movie()
    {

    }

    public Movie(int _id, String _name, String _summary, String _releaseDate,
                 String _director, JSONArray _writers, JSONArray _genres, JSONArray _cast,
                 double _imdbRate, long _duration, int _ageLimit) throws JSONException {
        id = _id;
        name = _name;
        summary = _summary;
        releaseDate = _releaseDate;
        director = _director;
        for(int i = 0; i < _writers.length(); i++){
            writers.add(_writers.getString(i));
        }
        for(int i = 0; i < _genres.length(); i++){
            genres.add(_genres.getString(i));
        }
        for(int i = 0; i < _cast.length(); i++){
            cast.add(_cast.getInt(i));
        }
        imdbRate = _imdbRate;
        duration = _duration;
        ageLimit = _ageLimit;
    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getSummary() {return summary;}
    public String getReleaseDate() {return releaseDate;}
    public String getDirector() {return director;}
    public List<String> getWriters() {return writers;}
    public List<String> getGenres() {return genres;}
    public List<Integer> getCast()
    {
        return cast;
    }
    public double getImdbRate() {return imdbRate;}
    public long getDuration() {return duration;}
    public int getAgeLimit() {return ageLimit;}
    public void addComment(Comment comment) {
        comments.add(comment);
    }
    public JSONObject rateMovie(String userEmail, int score) throws JSONException {
        if(map.containsKey(userEmail)) {
            rating = (rating*ratingCount - map.get(userEmail))/(ratingCount - 1);
        }
        map.put(userEmail, score);
        ratingCount += 1;
        rating = (rating*(ratingCount-1) + score)/ratingCount;
        rating = Double.parseDouble(new DecimalFormat("##.#").format(rating));
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data", "movie rated successfully");
        return response;
    }
    public double getRating() {return rating;}
    public double getRatingCount() {return ratingCount;}
    public List<Comment> getComments() {return comments;}
}