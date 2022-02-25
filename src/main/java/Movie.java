import java.util.List;

public class Movie {
    private int id;
    private String name;
    private String summary;
    private String releaseDate;
    private String director;
    private String[] writers;
    private String[] genres;
    private int[] cast;
    private double imdbRate;
    private long duration;
    private int ageLimit;
    private double rating;
    private double ratingCount;
    private List<Comment> comments;

    public Movie()
    {

    }

    public Movie(int _id, String _name, String _summary, String _releaseDate,
                 String _director, String[] _writers, String[] _genres, int[] _cast,
                 double _imdbRate, long _duration, int _ageLimit)
    {
        id = _id;
        name = _name;
        summary = _summary;
        releaseDate = _releaseDate;
        director = _director;
        writers = _writers;
        genres = _genres;
        cast = _cast;
        imdbRate = _imdbRate;
        duration = _duration;
        ageLimit = _ageLimit;
    }

    public String getName()
    {
        return name;
    }
    public int getId()
    {
        return id;
    }
    public int[] getCast()
    {
        return cast;
    }
}