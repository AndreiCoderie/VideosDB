package tasks;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.LinkedList;

public class Serial extends Show {

    /**
     * Number of seasons
     */
    private int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Season> seasons;

    /**
     * setter pentru sezoane
     */
    public void setSeasons(final ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    /**
     * getter pentru numarul sezonului
     */
    public int getNumberSeason() {
        return numberOfSeasons;
    }

    /**
     * getter pentru sezoane
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * OVERRIDE LA TOSTRING
     */
    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }

    /**
     * Metoda pentru a adauga un rating la un sezon
     */
    public void addSeasonRating(final ArrayList<Double> grade, final int nrOfSeason) {
        List<Season> seasons = getSeasons();
        seasons.get(nrOfSeason).setRatings(grade);
    }

    /**
     * Metoda pentru a seta media de rating
     */
    @Override
    public Double getAverageOfRatings() {
        int size = 1;
        double medie = 0;
        for (int i = 0; i < numberOfSeasons; i++) {
            medie += seasons.get(i).getAverageOfASeason();
            size = numberOfSeasons;
        }
        return (medie / size);
    }

    /**
     * Metoda pentru a calcula suma totala a duratiei a unui serial
     */

    public int getDurationSum() {
        int sum = 0;
        for (int i = 0; i < seasons.size(); i++) {
            sum += seasons.get(i).getDuration();
        }
        return sum;
    }

    /**
     * metoda pentru query pe seriale despre rating
     */
    public static String serialRating(final int number, final String sortType,
                                      final List<Serial> serials,
                                      final List<List<String>> filters) {
        ArrayList<String> finalArray = new ArrayList<>();
        String message = null;
        ArrayList<String> serialsName = new ArrayList<>();
        ArrayList<String> restOfNames = new ArrayList<>();
        Map<String, Double> sortedRating = new TreeMap<>();
        for (int i = 0; i < serials.size(); i++) {
            restOfNames.add(serials.get(i).getTitle());
            if (serials.get(i).getAverageOfRatings() == 0.0) {
                serialsName.add(serials.get(i).getTitle());
            }
        }
        for (int i = 0; i < serialsName.size(); i++) {
            restOfNames.remove(serialsName.get(i));
        }
        for (int i = 0; i < serials.size(); i++) {
            sortedRating.put(serials.get(i).getTitle(), serials.get(i).getAverageOfRatings());
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
        for (int i = 0; i < serials.size(); i++) {
            if (serials.get(i).getAverageOfRatings() == 0
                    && finalList.contains(serials.get(i).getTitle())) {
                finalList.remove(serials.get(i).getTitle());
            }
        }
        if (sortType.equals("desc")) {
            for (int j = 0; j < finalList.size(); j++) {
                for (int i = 0; i < serials.size(); i++) {
                    if (serials.get(i).getTitle().equals(finalList.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(serials.get(i).getYear()))))
                                    && serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(serials.get(i).getTitle());
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(finalList.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(serials.get(i).getYear()))) {
                                finalArray.add(serials.get(i).getTitle());
                            }
                        }
                    }
                }
            }
        }
        if (sortType.equals("asc")) {
            for (int i = serials.size() - 1; i >= 0; i--) {
                if (finalList.contains(serials.get(i).getTitle())) {
                    if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                        if (filters.get(0).get(0)
                                .equals(((String.valueOf(serials.get(i).getYear()))))
                                && serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                            finalArray.add(serials.get(i).getTitle());
                        }
                    }
                    if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                        if (serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                            finalArray.add(serials.get(i).getTitle());
                        }
                    }
                    if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                        if (filters.get(0).get(0)
                                .equals(String.valueOf(serials.get(i).getYear()))) {
                            finalArray.add(serials.get(i).getTitle());
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
     * Query pentru seriale favs
     */
    public static String serialFavorite(final int number,
                                        final String sortType,
                                        final List<Serial> serials, final List<User> users,
                                        final List<List<String>> filters) {
        ArrayList<String> finalArray = new ArrayList<>();
        ArrayList<String> serialsWithRating0 = new ArrayList<>();
        ArrayList<String> sortedAscSerialsName = new ArrayList<>(); // seriale sortate crescator
        ArrayList<String> sortedDescSerialsName = new ArrayList<>(); // seriale sortate descrasctor
        HashMap<String, Integer> mapWithMoviesAndNumber = new HashMap<>();

        for (int i = 0; i < serials.size(); i++) {
            mapWithMoviesAndNumber.put(serials.get(i).getTitle(), 0);
        }
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < serials.size(); j++) {
                if (users.get(i).getFavoriteMovies().contains(serials.get(j).getTitle())) {
                    mapWithMoviesAndNumber.put(serials.get(j).getTitle(),
                            mapWithMoviesAndNumber.get(serials.get(j).getTitle()) + 1);
                }
            }
        }
        User.checkWhoHasValue0(sortedAscSerialsName, mapWithMoviesAndNumber, serialsWithRating0);
        sortedAscSerialsName.removeAll(serialsWithRating0);
        for (int i = sortedAscSerialsName.size() - 1; i >= 0; i--) {
            sortedDescSerialsName.add(sortedAscSerialsName.get(i));
        }
        if (sortType.equals("desc")) {
            for (int j = 0; j < sortedDescSerialsName.size(); j++) {
                for (int i = 0; i < serials.size(); i++) {
                    if (serials.get(i).getTitle().equals(sortedDescSerialsName.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(serials.get(i).getYear()))
                                    && (serials.get(i).getGenres()
                                    .contains(filters.get(1).get(0)))) {
                                finalArray.add(sortedDescSerialsName.get(j));
                            }
                        } else if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(serials.get(i).getYear()))) {
                                finalArray.add(sortedDescSerialsName.get(j));
                            }
                        } else if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedDescSerialsName.get(j));
                            }
                        }
                    }
                }
            }
        }
        if (sortType.equals("asc")) {
            for (int i = 0; i < serials.size(); i++) {
                for (int j = 0; j < sortedAscSerialsName.size(); j++) {
                    if (serials.get(i).getTitle().equals(sortedAscSerialsName.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(serials.get(i).getYear()))
                                    && serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedAscSerialsName.get(j));
                            }
                        } else if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0).equals((serials.get(i).getYear()))) {
                                finalArray.add(serials.get(i).getTitle());
                            }
                        } else if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(serials.get(i).getTitle());
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
     * Metoda pentru query pe seriale privind lungimea
     */
    public static String serialLongest(final int number, final String sortType,
                                       final List<Serial> serials,
                                       final List<List<String>> filters) {
        Map<Integer, String> sortedMapOfDuration = new TreeMap<>();
        ArrayList<String> sortedAscMovieNames = new ArrayList<>();
        ArrayList<String> sortedDescMovieNames = new ArrayList<>();
        ArrayList<String> finalArray = new ArrayList<>();
        ArrayList<String> notFinalArray = new ArrayList<>();
        for (int i = 0; i < serials.size(); i++) {
            sortedMapOfDuration.put(serials.get(i).getDurationSum(), serials.get(i).getTitle());
        }
        for (Map.Entry<Integer, String> entry : sortedMapOfDuration.entrySet()) {
            sortedAscMovieNames.add(entry.getValue());
        }
        for (int i = sortedAscMovieNames.size() - 1; i >= 0; i--) {
            sortedDescMovieNames.add(sortedAscMovieNames.get(i));
        }
        if (sortType.equals("asc")) {
            for (int i = 0; i < serials.size(); i++) {
                for (int j = 0; j < sortedAscMovieNames.size(); j++) {
                    if (serials.get(i).getTitle().equals(sortedAscMovieNames.get(j))) {
                        if (filters.get(0).get(0) != null
                                && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(serials.get(i).getYear()))))
                                    && serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                notFinalArray.add(sortedAscMovieNames.get(j));
                            }
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
            for (int i = 0; i < serials.size(); i++) {
                for (int j = 0; j < sortedDescMovieNames.size(); j++) {
                    if (serials.get(i).getTitle().equals(sortedDescMovieNames.get(j))) {
                        if (filters.get(0).get(0) != null
                                && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(serials.get(i).getYear()))))
                                    && serials.get(i).getGenres()
                                    .contains(filters.get(1).get(0))) {
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


        return "Query result: " + finalArray;
    }

    /**
     * Metoda pentru query despre seriale prind vizionarile
     */
    public static String mostViewedSerial(final int number, final String sortType,
                                          final List<Serial> serials,
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
                for (int i = 0; i < serials.size(); i++) {
                    if (serials.get(i).getTitle().equals(sortedDescArray.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(serials.get(i).getYear()))))
                                    && serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedDescArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedDescArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(serials.get(i).getYear()))) {
                                finalArray.add(sortedDescArray.get(j));
                            }
                        }
                    }
                }
            }
        }
        if (sortType.equals("asc")) {
            for (int j = 0; j < sortedAscArray.size(); j++) {
                for (int i = 0; i < serials.size(); i++) {
                    if (serials.get(i).getTitle().equals(sortedAscArray.get(j))) {
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) != null) {
                            if (filters.get(0).get(0)
                                    .equals(((String.valueOf(serials.get(i).getYear()))))
                                    && serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedAscArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) == null && filters.get(1).get(0) != null) {
                            if (serials.get(i).getGenres().contains(filters.get(1).get(0))) {
                                finalArray.add(sortedAscArray.get(j));
                            }
                        }
                        if (filters.get(0).get(0) != null && filters.get(1).get(0) == null) {
                            if (filters.get(0).get(0)
                                    .equals(String.valueOf(serials.get(i).getYear()))) {
                                finalArray.add(sortedAscArray.get(j));
                            }
                        }
                    }
                }
            }
        }
        return "Query result: " + finalArray;
    }
}
