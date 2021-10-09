package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;


    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    /**
     * Aici se adauga ratingul in lista de ratinguri
     */
    public void setRatings(final List<Double> ratings) {
        for (int i = 0; i < ratings.size(); i++) {
            this.ratings.add(ratings.get(i));
        }
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + ", rating="
                + ratings
                + '}';
    }

    /**
     * Se obtine average pentru un sezon
     */

    public double getAverageOfASeason() {
        double medie = 0;
        int size = 1;
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i) == null) {
                medie = 0;
            }
            medie += ratings.get(i);
            size = ratings.size();
        }
        return (medie / size);
    }
}

