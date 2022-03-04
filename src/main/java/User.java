import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import Error.*;

public class User {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthDate;
    private List<Integer> WatchList = new ArrayList<>();

    @ConstructorProperties({"email","password","nickname","name","birthDate"})
    @JsonCreator
    public User(@JsonProperty(value = "email", required = true) String email,
                @JsonProperty(value = "password", required = true) String password,
                @JsonProperty(value = "nickname", required = true) String nickname,
                @JsonProperty(value = "name", required = true) String name,
                @JsonProperty(value = "birthDate", required = true) String birthDate)
    {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
    }

    public String getEmail() { return this.email; }
    public String getName() { return this.name; }
    public String getBirthDate() {return this.birthDate;}
    public String getNickname() {return this.nickname;}
    public List<Integer> getWatch() {return this.WatchList;}

    private int calculateUserAge() {
        LocalDate birth = LocalDate.parse(this.birthDate);
        LocalDate curDate = LocalDate.now();
        return curDate.getYear() - birth.getYear();
    }

    public ObjectNode addToWatchList(int movieId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        Movie movie = MovieHandler.returnMovieObjectGivenId(movieId);
        if(movie != null) {
            if(calculateUserAge() >= movie.getAgeLimit()) {
                if(WatchList.contains(movie.getId())) {
                    MovieAlreadyExists err = new MovieAlreadyExists();
                    response.put("success", false);
                    response.put("error", err.getMessage());
                    return response;
                }
                WatchList.add(movie.getId());
                response.put("success", true);
                response.put("data", "movie added to watchlist successfully");
                return response;
            }
            AgeLimitError err = new AgeLimitError();
            response.put("success", false);
            response.put("data", err.getMessage());
            return response;
        }

        return MovieHandler.MovieNotFound();
    }

    public ObjectNode removeFromWatchList(int movieId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        if (WatchList.contains(movieId)) {
            WatchList.remove((Integer) movieId);
            response.put("success", true);
            response.put("data", "movie removed from watchlist successfully");
            return response;
        }
        return MovieHandler.MovieNotFound();
    }

    public ObjectNode getWatchList() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for(Integer movie : WatchList) {
            Movie m = MovieHandler.returnMovieObjectGivenId(movie);
            ObjectNode mov = objectMapper.createObjectNode();
            mov.put("movieId", m.getId());
            mov.put("name", m.getName());
            mov.put("director", m.getDirector());
            ArrayNode jsonArray = objectMapper.createArrayNode();
            for(String g : m.getGenres()) {
                jsonArray.add(g);
            }
            mov.putArray("genres").addAll(jsonArray);
            mov.put("rating", m.getRating());
            arrayNode.add(mov);
        }
        root.putArray("WatchList").addAll(arrayNode);
        response.put("success", true);
        response.set("data", root);
        return response;
    }
}
