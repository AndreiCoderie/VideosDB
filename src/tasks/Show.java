package tasks;

import java.util.ArrayList;

public abstract class Show {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    /**
     * metoda pentru a calcula media de ratinguri pentru film / serial
     */
    public abstract Double getAverageOfRatings();


    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    /**
     * getter pentru titlu
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter pentru an
     */
    public int getYear() {
        return year;
    }

    /**
     * getter pentru cast
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * getter pentru genuri
     */
    public ArrayList<String> getGenres() {
        return genres;
    }


}
