package tasks;


import entertainment.Season;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User {
    private final String username;

    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    private final List<String> ratedVideos = new ArrayList<>();

    private final List<Season> ratedSeaons = new ArrayList<>();

    /**
     * getter pentru SezoaneCuRate
     */
    public List<Season> getRatedSeaons() {
        return ratedSeaons;
    }

    /**
     * metoda pentru adauga un sezon la rated
     */
    public void addRatedSeaons(final Season ratedSeaon) {
        this.ratedSeaons.add(ratedSeaon);
    }

    /**
     * getter pentru videourileC u Rating
     */
    public List<String> getRatedVideos() {
        return ratedVideos;
    }

    /**
     * metoda pentru a adauga videoruile la rated
     */
    public void addRatedVideos(final String titles) {
        ratedVideos.add(titles);
    }

    /**
     * setter pentru number de ratings
     */
    public void setNrOfRatings(final int nrOfRatings) {
        this.nrOfRatings = nrOfRatings;
    }

    private int nrOfRatings = 0;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    /**
     * getter pentru nume de utilizator
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter pentru istoric al unui utulizator
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     * getter pentru tipul subscriptiei unui utilizator
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * getter pentru lista de videouri favorite ale unui utilizator
     */
    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    /**
     * metoda pentru a adauga un video la favorite
     */
    public void addFavoriteMovies(final String movie) {
        this.favoriteMovies.add(movie);
    }

    /**
     * setter pentru history
     */
    public void setHistory(final String title, final int integer) {
        this.history.put(title, integer);
    }

    /**
     * getter pt nr de ratinguri
     */
    public int getNrOfRatings() {
        return nrOfRatings;
    }


    /**
     * Metoda pentru comanda de adauga favorite
     */
    public static String addFavorite(final List<User> users, final String title,
                                     final String username) {
        String message = null;
        boolean checkTitle = false;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                    if (entry.getKey().equals(title)) {
                        checkTitle = true;
                        break;
                    }
                }
                if (!checkTitle) {
                    message = "error -> " + title + " is not seen";
                } else {
                    if (!user.getFavoriteMovies().contains(title)) {
                        user.addFavoriteMovies(title);
                        for (Map.Entry<String, Integer> str : user.getHistory()
                                .entrySet()) {
                            if (str.getKey().equals(title)) {
                                message = "success -> " + str.getKey() + " was added as favourite";
                            }
                        }
                    } else {
                        message = "error -> " + title + " is already in favourite list";
                    }

                }
                return message;
            }
        }
        return "some error";
    }

    /**
     * Metoda pentru comanda de a adauga un video la videouri vazute
     */
    public static String addViewedVideo(final List<User> users, final String title,
                                        final String username) {
        String message = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getHistory().containsKey(title)) {
                    user.setHistory(title, user.getHistory().get(title) + 1);
                    message = "success -> " + title + " was viewed with total views of "
                            + (user.getHistory().get(title));
                    return message;
                } else {
                    user.getHistory().put(title, 1);
                    message = "success -> " + title + " was viewed with total views of " + 1;
                }
            }
        }
        return message;
    }

    /**
     * Metoda pentru comanda de adauga un rating unui video
     */
    public static String addRating(final List<Movie> movies, final List<Serial> serials,
                                   final List<User> users,
                                   final String username, final String title,
                                   final double grade, final int season) {
        String message = null;
        for (User user : users) {
            for (Movie movie : movies) {
                if (user.getUsername().equals(username)
                        && movie.getTitle().equals(title)) {
                    if (user.getHistory().containsKey(title)) {
                        if (user.getRatedVideos().contains(title)) {
                            message = "error -> " + title + " has been already rated";
                            break;
                        }
                        if (!user.getRatedVideos().contains(title)) {
                            user.addRatedVideos(title);
                        }
                        int contor = user.getNrOfRatings();
                        contor++;
                        user.setNrOfRatings(contor);
                        movie.setRate(List.of(grade));
                        message = "success -> " + movie.getTitle() + " was rated with "
                                + movie.getRate().get(movie.getRate().size() - 1)
                                + " by " + user.getUsername();
                    } else {
                        message = "error -> " + title + " is not seen";

                    }
                }
            }
            for (Serial serial : serials) {
                if (user.getUsername().equals(username)
                        && serial.getTitle().equals(title)) {
                    if (user.getHistory().containsKey(title)) {
                        if (user.getRatedSeaons()
                                .contains(serial.getSeasons().get(season - 1))) {
                            message = "error -> " + title + " has been already rated";
                            break;
                        }
                        user.addRatedSeaons(serial.getSeasons().get(season - 1));
                        int contor = user.getNrOfRatings();
                        contor++;
                        user.setNrOfRatings(contor);
                        serial.getSeasons().get(season - 1).setRatings(List.of(grade));
                        message = "success -> " + title + " was rated with "
                                + serial.getSeasons().get(season - 1).getRatings()
                                .get(serial.getSeasons().get(season - 1)
                                        .getRatings().size() - 1)
                                + " by " + user.getUsername();
                    } else {
                        message = "error -> " + title + " is not seen";
                    }
                    return message;

                }
            }
        }
        return message;
    }

    /**
     * Metoda pentru a rezolva taskul de la Query pentru useri
     */
    public static String userQuery(final List<User> users,
                                   final int number,
                                   final String sortType) {
        ArrayList<String> finalArray = new ArrayList<>();
        ArrayList<String> sortedAscUsernames = new ArrayList<>();
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ArrayList<String> sortedDescUsernames = new ArrayList<>();
        HashMap<String, Integer> mapOfUsernames = new HashMap<>();
        ArrayList<String> listWithValue0 = new ArrayList<>();
        for (User user : users) {
            mapOfUsernames.put(user.getUsername(), user.getNrOfRatings());
        }
        checkWhoHasValue0(sortedAscUsernames, mapOfUsernames, listWithValue0);
        for (int i = sortedAscUsernames.size() - 1; i >= 0; i--) {
            sortedDescUsernames.add(sortedAscUsernames.get(i));
        }

        for (String s : listWithValue0) {
            sortedAscUsernames.remove(s);
        }

        if (sortType.equals("asc")) {
            finalArray.addAll(sortedAscUsernames);
        }

        if (sortType.equals("desc")) {
            for (int i = sortedAscUsernames.size() - 1; i >= 0; i--) {
                finalArray.add(sortedAscUsernames.get(i));
            }
        }
        ArrayList<String> finalArray2 = new ArrayList<>();
        if (finalArray.size() > number) {
            for (int i = number; i < finalArray.size(); i++) {
                finalArray2.add(finalArray.get(i));
            }
        }
        finalArray.removeAll(finalArray2);

        return "Query result: " + finalArray;
    }

    /**
     * This method add into a list the keys with value 0 from a hasmap
     */
    public static void checkWhoHasValue0(final ArrayList<String> sortedAscUsernames,
                                         final HashMap<String, Integer> mapOfUsernames,
                                         final ArrayList<String> listWithValue0) {
        for (Map.Entry<String, Integer> entry : mapOfUsernames.entrySet()) {
            if (entry.getValue() == 0) {
                listWithValue0.add(entry.getKey());
            }
        }
        mapOfUsernames.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> sortedAscUsernames.add(e.getKey()));
    }

    // -----------------------> RECOMANDATIONS <-----------------------

    /**
     * Metoda pentru a rezolva recomandarea standard
     */
    public static String standardRec(final List<User> users, final List<Movie> movies,
                                     final List<Serial> serials,
                                     final String username) {
        List<String> finalArray = new ArrayList<>();
        if (movies.size() != 0) {
            for (Movie movie : movies) {
                for (User user : users) {
                    if (user.getUsername().equals(username)) {
                        if (!(user.getHistory().containsKey(movie.getTitle()))) {
                            finalArray.add(movie.getTitle());
                            break;
                        }

                    }
                }
            }
        }
        for (Serial serial : serials) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (!(user.getHistory().containsKey(serial.getTitle()))) {
                        finalArray.add(serial.getTitle());
                        break;
                    }

                }
            }
        }
        finalArray.add("notthere");
        if (finalArray.get(0).equals("notthere")) {
            return "StandardRecommendation cannot be applied!";
        }

        return "StandardRecommendation result: " + finalArray.get(0);
    }

    /**
     * metoda pentru a rezolva recomandarea cel mai bun nevazut video
     */
    public static String bestUnseenRec(final List<User> users,
                                       final List<Movie> movies,
                                       final List<Serial> serials,
                                       final String username) {
        List<String> finalArray = new ArrayList<>();
        List<String> sortedRateVideos = new ArrayList<>();
        HashMap<String, Double> mapOfMovies = new HashMap<>();
        for (Movie movie : movies) {
            mapOfMovies.put(movie.getTitle(), movie.getAverageOfRatings());
        }
        for (Serial serial : serials) {
            mapOfMovies.put(serial.getTitle(), serial.getAverageOfRatings());
        }
        List<String> listWith0 = new ArrayList<>();
        HashMap<String, Double> sortedMap = sortByValue(mapOfMovies);
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            sortedRateVideos.add(entry.getKey());
            if (entry.getValue() == 0) {
                listWith0.add(entry.getKey());
            }
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (int j = 0; j < sortedRateVideos.size(); j++) {
                    if (user.getHistory().containsKey(sortedRateVideos.get(j))) {
                        //noinspection SuspiciousListRemoveInLoop
                        sortedRateVideos.remove(j);
                    }
                }
            }
        }
        sortedRateVideos.removeAll(listWith0);
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (String sortedRateVideo : sortedRateVideos) {
                    for (Movie movie : movies) {
                        if (movie.getTitle().equals(sortedRateVideo)
                                && !user.getHistory()
                                .containsKey(sortedRateVideo)) {
                            finalArray.add(sortedRateVideo);
                            if (finalArray.size() != 0) {
                                return "BestRatedUnseenRecommendation result: " + finalArray.get(0);
                            }
                        }
                    }
                }
                for (Movie movie : movies) {
                    for (String s : listWith0) {
                        if (movie.getTitle().equals(s)
                                && !user.getHistory()
                                .containsKey(s)) {
                            finalArray.add(s);
                            if (finalArray.size() != 0) {
                                return "BestRatedUnseenRecommendation result: " + finalArray.get(0);
                            }
                        }
                    }
                }
                for (Serial serial : serials) {
                    for (String sortedRateVideo : sortedRateVideos) {
                        if (serial.getTitle().equals(sortedRateVideo)
                                && !user.getHistory()
                                .containsKey(sortedRateVideo)) {
                            finalArray.add(sortedRateVideo);
                            return "BestRatedUnseenRecommendation result: " + finalArray.get(0);
                        }
                    }
                }
            }
        }

        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Metoda pentru a rezolva recomandarea search
     */
    public static String searchRec(final List<User> users,
                                   final List<Movie> movies,
                                   final List<Serial> serials,
                                   final String username,
                                   final String genre) {
        for (User value : users) {
            if (value.getUsername().equals(username)) {
                if (value.getSubscriptionType().equals("BASIC")) {
                    return "SearchRecommendation cannot be applied!";
                }

            }
        }
        HashMap<String, Double> mapOfVideos = new HashMap<>();
        ArrayList<String> sortedVideos = new ArrayList<>();
        for (Movie movie : movies) {
            mapOfVideos.put(movie.getTitle(), movie.getAverageOfRatings());
        }
        for (Serial serial : serials) {
            mapOfVideos.put(serial.getTitle(), serial.getAverageOfRatings());
        }
        mapOfVideos.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> sortedVideos.add(e.getKey()));


        for (Movie movie : movies) {
            for (int k = 0; k < sortedVideos.size(); k++) {
                if (movie.getTitle().equals(sortedVideos.get(k))) {
                    if (!movie.getGenres().contains(genre)) {
                        //noinspection SuspiciousListRemoveInLoop
                        sortedVideos.remove(k);
                    }
                }
            }
        }
        for (Serial serial : serials) {
            for (int k = 0; k < sortedVideos.size(); k++) {
                if (serial.getTitle().equals(sortedVideos.get(k))) {
                    if (!serial.getGenres().contains(genre)) {
                        //noinspection SuspiciousListRemoveInLoop
                        sortedVideos.remove(k);
                    }
                }
            }
        }
        List<String> sortedVideos2 = new ArrayList<>();
        for (User user : users) {
            for (String sortedVideo : sortedVideos) {
                if (user.getUsername().equals(username)) {
                    if (user.getHistory().containsKey(sortedVideo)) {
                        sortedVideos2.add(sortedVideo);
                    }
                }
            }
        }
        sortedVideos.removeAll(sortedVideos2);
        ArrayList<String> finalArray = new ArrayList<>(sortedVideos);
        if (finalArray.size() == 0) {
            return "SearchRecommendation cannot be applied!";
        }

        Collections.sort(finalArray);

        return "SearchRecommendation result: " + finalArray;
    }

    /**
     * Metoda pentru a rezolva recomandarea pentru popular
     */
    public static String popularRec(final List<User> users,
                                    final List<Movie> movies,
                                    final List<Serial> serials,
                                    final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getSubscriptionType().equals("BASIC")) {
                    return "PopularRecommendation cannot be applied!";
                }
            }
        }
        List<String> unsortedList = new ArrayList<>();
        List<String> sortedList = new ArrayList<>();
        List<String> finalArray = new ArrayList<>();
        HashMap<String, Integer> mapList = new HashMap<>();
        for (Movie movie : movies) {
            for (int j = 0; j < movie.getGenres().size(); j++) {
                mapList.put((movie.getGenres()).get(j), 0);
            }
        }
        for (Serial serial : serials) {
            for (int j = 0; j < serial.getGenres().size(); j++) {
                mapList.put(serial.getGenres().get(j), 0);
            }

        }
        for (User user : users) {
            for (Movie movie : movies) {
                if (user.getHistory().containsKey(movie.getTitle())) {
                    for (int k = 0; k < movie.getGenres().size(); k++) {
                        mapList.put(movie.getGenres().get(k),
                                mapList.get(movie.getGenres().get(k))
                                        + user.getHistory().get(movie.getTitle()));
                    }
                }
            }
            for (Serial serial : serials) {
                if (user.getHistory().containsKey(serial.getTitle())) {
                    for (int k = 0; k < serial.getGenres().size(); k++) {
                        mapList.put(serial.getGenres().get(k),
                                mapList.get(serial.getGenres().get(k))
                                        + user.getHistory().get(serial.getTitle()));
                    }
                }
            }
        }
        HashMap<String, Integer> sortedMap = Movie.sortByValue(mapList);
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            unsortedList.add(entry.getKey());
        }
        for (int i = unsortedList.size() - 1; i >= 0; i--) {
            sortedList.add(unsortedList.get(i));
        }
        List<Show> arrayOfAllVideos = new ArrayList<>(movies);
        arrayOfAllVideos.addAll(serials);
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (String s : sortedList) {
                    for (Show arrayOfAllVideo : arrayOfAllVideos) {
                        if (arrayOfAllVideo.getGenres().contains(s)
                                && !user.getHistory()
                                .containsKey(arrayOfAllVideo.getTitle())) {
                            finalArray.add(arrayOfAllVideo.getTitle());
                            break;
                        }
                    }
                }
            }
        }
        finalArray.add("notthere");
        if (finalArray.get(0).equals("notthere")) {
            return "PopularRecommendation cannot be applied!";
        }

        return "PopularRecommendation result: " + finalArray.get(0);
    }

    /**
     * Metoda pentru a rezolva recomandarea favoirte
     */
    public static String favoriteRec(final List<User> users,
                                     final List<Movie> movies,
                                     final List<Serial> serials,
                                     final String username) {
        for (User value : users) {
            if (value.getUsername().equals(username)) {
                if (value.getSubscriptionType().equals("BASIC")) {
                    return "SearchRecommendation cannot be applied!";
                }

            }
        }
        List<String> finalArray = new ArrayList<>();
        List<String> sortedList = new ArrayList<>();
        List<String> arrayOfAllVideos = new ArrayList<>();
        HashMap<String, Integer> mapOfFavorites = new HashMap<>();
        for (User user : users) {
            for (String entry : user.getFavoriteMovies()) {
                if (mapOfFavorites.containsKey(entry)) {
                    mapOfFavorites.put(entry, mapOfFavorites.get(entry) + 1);
                } else {
                    mapOfFavorites.put(entry, 1);
                }
            }
        }
        mapOfFavorites.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> sortedList.add(e.getKey()));
        List<String> listToRemove = new ArrayList<>();
        for (String s : sortedList) {
            int counter = 0;
            for (int i = 0; i < users.size(); i++) {
                if (!users.get(i).getFavoriteMovies().contains(s)) {
                    counter++;
                }
                if (counter == users.size()) {
                    listToRemove.add(s);
                }
            }
        }
        for (User user : users) {
            for (String s : sortedList) {
                if (user.getUsername().equals(username)) {
                    if (user.getHistory().containsKey(s)) {
                        listToRemove.add(s);
                    }
                }
            }
        }
        sortedList.removeAll(listToRemove);

        for (Movie movie : movies) {
            arrayOfAllVideos.add(movie.getTitle());
        }
        for (Serial serial : serials) {
            arrayOfAllVideos.add(serial.getTitle());
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (String s : sortedList) {
                    for (String arrayOfAllVideo : arrayOfAllVideos) {
                        if (arrayOfAllVideo.equals(s)) {
                            finalArray.add(s);
                        }
                    }
                }
            }
        }
        if (finalArray.size() > 1) {
            for (String arrayOfAllVideo : arrayOfAllVideos) {
                for (int i = 0; i < finalArray.size(); i++) {
                    if (mapOfFavorites.get(finalArray.get(i))
                            .equals(mapOfFavorites.get(finalArray.get(i + 1)))) {
                        if (arrayOfAllVideo.equals(finalArray.get(i + 1))) {
                            finalArray.remove(i);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        finalArray.add("isnot");
        if (finalArray.get(0).equals("isnot")) {
            return "FavoriteRecommendation cannot be applied!";
        }
        return "FavoriteRecommendation result: " + finalArray.get(0);
    }

    /**
     * override la to string
     */
    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }

    /**
     * metoda pentru a sorta un hashmap de tip string, Double in functie de valori
     */
    public static HashMap<String, Double> sortByValue(final HashMap<String, Double> hm) {
        List<Map.Entry<String, Double>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
