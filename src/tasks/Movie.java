package tasks;


import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class Movie extends Show {
    private final int duration;

    private List<Double> rate = new ArrayList<>();

    /**
     * getter for rate
     */
    public List<Double> getRate() {
        return rate;
    }

    /**
     * setter for rate
     */

    public void setRate(final List<Double> rate) {
        for (int i = 0; i < rate.size(); i++) {
            this.rate.add(rate.get(i));
        }
    }

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    /**
     * getter for duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * override la to string
     */
    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }

    /**
     * FUNCTIE CARE CALCULEAZA MEDIA
     */
    @Override
    public Double getAverageOfRatings() {
        double medie = 0;
        int size = 1;
        if (rate != null) {
            for (int i = 0; i < rate.size(); i++) {
                medie += rate.get(i);
                size = rate.size();
            }
        } else {
            return medie;
        }
        return (medie / size);
    }


    // ---------------> QUERY <---------------
    // -------------------> VIDEO QUERY <-------------------

    /**
     * QUERY MOVIE RATING
     */
    public static String movieRating(final int number, final String sortType,
                                     final List<Movie> movies,
                                     final List<List<String>> filters) {
        ArrayList<String> finalArray = new ArrayList<>();
        String message = null;
        ArrayList<String> serialsName = new ArrayList<>();
        ArrayList<String> restOfNames = new ArrayList<>();
        Map<String, Double> sortedRating = new TreeMap<>();

        for (int i = 0; i < movies.size(); i++) {
            sortedRating.put(movies.get(i).getTitle(), movies.get(i).getAverageOfRatings());
        }
        List<String> sortedList = new ArrayList<>();
        sortedRating.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> sortedList.add(e.getKey()));
        List<String> finalList = new ArrayList<>();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            finalList.add(sortedList.get(i));
        }
        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < finalList.size(); j++) {
                if (movies.get(i).getTitle().equals(finalList.get(j))
                        && movies.get(i).getAverageOfRatings() == 0) {
                    finalList.remove(movies.get(i).getTitle());
                }
            }
        }
        if (sortType.equals("desc")) {
            for (int j = 0; j < finalList.size(); j++) {
                for (int i = 0; i < movies.size(); i++) {
                    if (finalList.contains(movies.get(i).getTitle())) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(movies.get(i).getYear()))))
                                    && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(movies.get(i).getTitle());
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(finalList.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(movies.get(i).getYear()))) {
                                finalArray.add(movies.get(i).getTitle());
                            }
                        }
                    }
                }
            }
        }
        if (sortType.equals("asc")) {
            for (int i = movies.size() - 1; i >= 0; i--) {
                if (finalList.contains(movies.get(i).getTitle())) {
                    if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                        if (filters.get(0).get(0)
                                .equals(((String.valueOf(movies.get(i).getYear()))))
                                && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                            finalArray.add(movies.get(i).getTitle());
                        }
                    }
                    if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                        if (movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                            finalArray.add(movies.get(i).getTitle());
                        }
                    }
                    if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                        if (filters.get(0).get(0).equals(String.valueOf(movies.get(i).getYear()))) {
                            finalArray.add(movies.get(i).getTitle());
                        }
                    }
                }
            }
        }
        List<String> finalArray2 = new ArrayList<>();
        if (finalArray.size() > number) {
            for (int i = 0; i < number; i++) {
                finalArray2.add(finalArray.get(i));
            }
        } else {
            return "Query result: " + finalArray;
        }
        message = "Query result: " + finalArray2;
        return message;
    }

    /**
     * QUERY MOVIE FAVORITE
     */

    public static String movieFavorite(final int number, final String sortType,
                                       final List<Movie> movies,
                                       final List<User> users,
                                       final List<List<String>> filters) {

        ArrayList<String> finalArray = new ArrayList<>();
        ArrayList<String> sortedAscMoviesName = new ArrayList<>(); // filmele sortate crescator
        ArrayList<String> sortedDescMoviesName = new ArrayList<>(); // filmele sortate descrasctor
        ArrayList<String> listWithMovies0 = new ArrayList<>();
        HashMap<String, Integer> mapWithMoviesAndNumber = new HashMap<>();

        for (int i = 0; i < movies.size(); i++) {
            mapWithMoviesAndNumber.put(movies.get(i).getTitle(), 0);
        }
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < movies.size(); j++) {
                if (users.get(i).getFavoriteMovies().contains(movies.get(j).getTitle())) {
                    mapWithMoviesAndNumber.put(movies.get(j).getTitle(),
                            mapWithMoviesAndNumber.get(movies.get(j).getTitle()) + 1);
                }
            }
        }
        User.checkWhoHasValue0(sortedAscMoviesName, mapWithMoviesAndNumber, listWithMovies0);

        sortedAscMoviesName.removeAll(listWithMovies0);

        for (int i = sortedAscMoviesName.size() - 1; i >= 0; i--) {
            sortedDescMoviesName.add(sortedAscMoviesName.get(i));
        }

        if (sortType.equals("desc")) {
            for (int j = 0; j < sortedDescMoviesName.size(); j++) {
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).getTitle().equals(sortedDescMoviesName.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(movies.get(i).getYear()))
                                    && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedDescMoviesName.get(j));
                            }
                        } else if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals((String.valueOf(movies.get(i).getYear())))) {
                                finalArray.add(sortedDescMoviesName.get(j));
                            }
                        } else if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (movies.get(i).getGenres().contains(filters.get(1))) {
                                finalArray.add(sortedDescMoviesName.get(j));
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) == null) {
                            finalArray.add(sortedDescMoviesName.get(j));
                        }
                    }
                }
            }
        }

        if (sortType.equals("asc")) {
            for (int i = 0; i < movies.size(); i++) {
                for (int j = 0; j < sortedAscMoviesName.size(); j++) {
                    if (movies.get(i).getTitle().equals(sortedAscMoviesName.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(movies.get(i).getYear()))
                                    && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedAscMoviesName.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals((String.valueOf(movies.get(i).getYear())))) {
                                finalArray.add(movies.get(i).getTitle());
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (movies.get(i).getGenres().contains(filters.get(1))) {
                                finalArray.add(movies.get(i).getTitle());
                            }
                        }
                    }
                }
            }
        }
        List<String> finalArray2 = new ArrayList<>();
        if (finalArray.size() < number) {
            return "Query result: " + finalArray;
        } else {
            for (int i = 0; i < number; i++) {
                finalArray2.add(finalArray.get(i));
            }
            return "Query result: " + finalArray2;
        }
    }

    /**
     * QUERY MOVIE LONGEST
     */
    public static String movieLongest(final int number, final String sortType,
                                      final List<Movie> movies,
                                      final List<List<String>> filters) {
        Map<String, Integer> sortedMapOfDuration = new TreeMap<>();
        ArrayList<String> sortedAscMovieNames = new ArrayList<>();
        ArrayList<String> sortedDescMovieNames = new ArrayList<>();
        ArrayList<String> finalArray = new ArrayList<>();
        ArrayList<String> notFinalArray = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            sortedMapOfDuration.put(movies.get(i).getTitle(), movies.get(i).getDuration());
        }
        sortedMapOfDuration.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .forEach(e -> sortedAscMovieNames.add(e.getKey()));

        for (int i = sortedAscMovieNames.size() - 1; i >= 0; i--) {
            sortedDescMovieNames.add(sortedAscMovieNames.get(i));
        }
        if (sortType.equals("asc")) {
            for (int i = 0; i < movies.size(); i++) {
                for (int j = 0; j < sortedAscMovieNames.size(); j++) {
                    if (movies.get(i).getTitle().equals(sortedAscMovieNames.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(movies.get(i).getYear()))))
                                    && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                notFinalArray.add(sortedAscMovieNames.get(j));
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (movies.get(i).getGenres().contains((filters.get(1).get(0)))) {
                                notFinalArray.add(sortedAscMovieNames.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(movies.get(i).getYear()))) {
                                notFinalArray.add(sortedAscMovieNames.get(j));
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) == null) {
                            notFinalArray.add(sortedAscMovieNames.get(j));
                        }

                    }
                }
            }
            for (int i = 0; i < sortedAscMovieNames.size(); i++) {
                for (int j = 0; j < notFinalArray.size(); j++) {
                    if (sortedAscMovieNames.get(i).equals(notFinalArray.get(j))) {
                        finalArray.add(sortedAscMovieNames.get(i));
                    }
                }
            }
        }
        if (sortType.equals("desc")) {
            for (int i = 0; i < movies.size(); i++) {
                for (int j = 0; j < sortedDescMovieNames.size(); j++) {
                    if (movies.get(i).getTitle().equals(sortedDescMovieNames.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(movies.get(i).getYear()))))
                                    && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                notFinalArray.add(sortedDescMovieNames.get(j));
                            }
                        }

                    }
                }
            }
            for (int i = 0; i < sortedDescMovieNames.size(); i++) {
                for (int j = 0; j < notFinalArray.size(); j++) {
                    if (sortedDescMovieNames.get(i).equals(notFinalArray.get(j))) {
                        finalArray.add(sortedDescMovieNames.get(i));
                    }
                }
            }
        }
        List<String> finalArray2 = new ArrayList<>();
        if (finalArray.size() > number) {
            for (int i = 0; i < number; i++) {
                finalArray2.add(finalArray.get(i));
            }
        } else {
            return "Query result: " + finalArray;
        }

        return "Query result: " + finalArray2;

    }

    /**
     * ViewedMovie Query
     */
    public static String mostViewedMovies(final int number,
                                          final String sortType,
                                          final List<Movie> movies,
                                          final List<User> users,
                                          final List<List<String>> filters) {
        List<String> finalArray = new LinkedList<>();
        ArrayList<String> sortedAscArray = new ArrayList<>();
        ArrayList<String> sortedDescArray = new ArrayList<>();
        Map<String, Integer> newMap = new HashMap<>();

        for (int i = 0; i < users.size(); i++) {
            for (Map.Entry<String, Integer> entry : users.get(i).getHistory().entrySet()) {
                if (newMap.get(entry.getKey()) == null) {
                    newMap.put(entry.getKey(), entry.getValue());
                } else {
                    newMap.put(entry.getKey(), newMap.get(entry.getKey()) + entry.getValue());
                }
            }
        }
        newMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> sortedAscArray.add(e.getKey()));


        for (int i = sortedAscArray.size() - 1; i >= 0; i--) {
            sortedDescArray.add(sortedAscArray.get(i));
        }
        if (sortType.equals("desc")) {
            for (int j = 0; j < sortedDescArray.size(); j++) {
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).getTitle().equals(sortedDescArray.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(movies.get(i).getYear()))))
                                    && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedDescArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedDescArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(movies.get(i).getYear()))) {
                                finalArray.add(sortedDescArray.get(j));
                            }
                        }
                    }
                }
            }
        }

        if (sortType.equals("asc")) {
            for (int j = 0; j < sortedAscArray.size(); j++) {
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).getTitle().equals(sortedAscArray.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(movies.get(i).getYear()))))
                                    && movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedAscArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (movies.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedAscArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(movies.get(i).getYear()))) {
                                finalArray.add(sortedAscArray.get(j));
                            }
                        }
                    }
                }
            }
        }
        List<String> finalArray2 = new ArrayList<>();
        if (finalArray.size() < number) {
            return "Query result: " + finalArray;
        } else {
            for (int i = 0; i < number; i++) {
                finalArray2.add(finalArray.get(i));
            }
        }
        return "Query result: " + finalArray2;

    }


    /**
     * sorting a map by value
     */
    public static HashMap<String, Integer> sortByValue(final HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(final Map.Entry<String, Integer> o1,
                               final Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}






