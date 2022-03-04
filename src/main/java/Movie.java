import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.beans.ConstructorProperties;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Error.*;

public class Movie {
    private int id;
    private String name;
    private String summary;
    private String releaseDate;
    private String director;
    List<String> writers = new ArrayList<>();
    List<String> genres = new ArrayList<>();
    List<Integer> cast = new ArrayList<>();
    private double imdbRate;
    private long duration;
    private int ageLimit;
    private double rating = 0;
    private double ratingCount = 0;
    private List<Comment> comments = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();

    @ConstructorProperties({"id","name","summary", "releaseDate", "director", "writers", "genres", "cast", "imdbRate", "duration", "ageLimit"})
    @JsonCreator
    public Movie(@JsonProperty(value = "id", required = true) int id, @JsonProperty(value = "name", required = true) String name,
                 @JsonProperty(value = "summary", required = true) String summary, @JsonProperty(value = "releaseDate", required = true) String releaseDate,
                 @JsonProperty(value = "director", required = true) String director, @JsonProperty(value = "writers", required = true) ArrayNode writers,
                 @JsonProperty(value = "genres", required = true) ArrayNode genres, @JsonProperty(value = "cast", required = true) ArrayNode cast,
                 @JsonProperty(value = "imdbRate", required = true) double imdbRate, @JsonProperty(value = "duration", required = true) long duration,
                 @JsonProperty(value = "ageLimit", required = true) int ageLimit) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
        for(int i = 0; i < writers.size(); i++){
            this.writers.add(writers.get(i).asText());
        }
        for(int i = 0; i < genres.size(); i++){
            this.genres.add(genres.get(i).asText());
        }
        for(int i = 0; i < cast.size(); i++){
            this.cast.add(cast.get(i).intValue());
            IEMDBController.actorHandler.actors.get(cast.get(i).intValue()).addMovie(this);
        }
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getSummary() {return this.summary; }
    public String getReleaseDate() {return this.releaseDate; }
    public String getDirector() {return this.director; }
    public List<String> getWriters() {return this.writers; }
    public List<String> getGenres() {return this.genres; }
    public List<Integer> getCast() { return this.cast; }
    public double getImdbRate() {return this.imdbRate;}
    public long getDuration() {return this.duration;}
    public int getAgeLimit() {return this.ageLimit;}

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public ObjectNode rateMovie(String userEmail, int score) throws InvalidRateScore {
        if (score > 10 || score <= 0) {
            throw new InvalidRateScore();
        }
        if(map.containsKey(userEmail)) {
            if (ratingCount == 1)
                rating = 0;
            else
                rating = (rating*ratingCount - map.get(userEmail))/(ratingCount - 1);
            ratingCount--;
        }
        map.put(userEmail, score);
        ratingCount += 1;
        rating = (rating*(ratingCount-1) + score)/ratingCount;
        rating = Double.parseDouble(new DecimalFormat("##.#").format(rating));
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        response.put("success", true);
        response.put("data", "movie rated successfully");
        return response;
    }
    public double getRating() {return rating;}
    public double getRatingCount() {return ratingCount;}
    public List<Comment> getComments() {return comments;}
}