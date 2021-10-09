package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tasks.Actor;
import tasks.Movie;
import tasks.Serial;
import tasks.User;
import tasks.Show;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();
        //TODO add here the entry point to your implementation
        Show show = null;
        Actor actor = null;
        Movie movie = null;
        Serial serial = null;
        User user = null;
        List<Actor> actors = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        List<Serial> serials = new ArrayList<>();
        List<User> users = new ArrayList<>();
        if (input.getActors().size() != 0) {
            for (int i = 0; i < input.getActors().size(); i++) {
                actor = new Actor(input.getActors().get(i).getName(),
                        input.getActors().get(i).getCareerDescription(),
                        input.getActors().get(i).getFilmography(),
                        input.getActors().get(i).getAwards());
                actors.add(actor);
            }
        }
        if (input.getMovies().size() != 0) {
            for (int i = 0; i < input.getMovies().size(); i++) {
                show = new Movie(input.getMovies().get(i).getTitle(),
                        input.getMovies().get(i).getCast(),
                        input.getMovies().get(i).getGenres(), input.getMovies().get(i).getYear(),
                        input.getMovies().get(i).getDuration());
                movies.add((Movie) show);
            }
        }
        if (input.getSerials().size() != 0) {
            for (int i = 0; i < input.getSerials().size(); i++) {
                show = new Serial(input.getSerials().get(i).getTitle(),
                        input.getSerials().get(i).getCast(),
                        input.getSerials().get(i).getGenres(),
                        input.getSerials().get(i).getNumberSeason(),
                        input.getSerials().get(i).getSeasons(),
                        input.getSerials().get(i).getYear());
                serial = (Serial) show;
                serials.add(serial);
            }
        }
        if (input.getUsers().size() != 0) {
            for (int i = 0; i < input.getUsers().size(); i++) {
                user = new User(input.getUsers().get(i).getUsername(),
                        input.getUsers().get(i).getSubscriptionType(),
                        input.getUsers().get(i).getHistory(),
                        input.getUsers().get(i).getFavoriteMovies());
                users.add(user);
            }
        }
        for (int i = 0; i < input.getCommands().size(); i++) {
            if (input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {
                if (input.getCommands().get(i).getType().equals("favorite")) {

                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.addFavorite(users, input.getCommands().get(i).getTitle(),
                                            input.getCommands().get(i).getUsername()));


                    arrayResult.add(object);
                }
                if (input.getCommands().get(i).getType().equals("view")) {
                    // user.addViewedVideo(movie);
                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.addViewedVideo(users,
                                            input.getCommands().get(i).getTitle(),
                                            input.getCommands().get(i).getUsername()));

                    arrayResult.add(object);

                }
                if (input.getCommands().get(i).getType().equals("rating")) {
                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.addRating(movies, serials, users,
                                            input.getCommands().get(i).getUsername(),
                                            input.getCommands().get(i).getTitle(),
                                            input.getCommands().get(i).getGrade(),
                                            input.getCommands().get(i).getSeasonNumber()));

                    arrayResult.add(object);
                }
            } else if (input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {
                if (input.getCommands().get(i).getObjectType().equals(Constants.ACTORS)) {
                    if (input.getCommands().get(i).getCriteria().equals("average")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Actor.average(input.getCommands().get(i).getNumber(),
                                                actors,
                                                movies, serials,
                                                input.getCommands().get(i).getSortType()));

                        arrayResult.add(object);
                    }
                    if (input.getCommands().get(i).getCriteria().equals(Constants.AWARDS)) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        actor.awards(actors,
                                                input.getCommands().get(i).getFilters(),
                                                input.getCommands().get(i).getSortType()));

                        arrayResult.add(object);
                    }
                    if (input.getCommands().get(i).getCriteria()
                            .equals(Constants.FILTER_DESCRIPTIONS)) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Actor.filterDescription(actors,
                                                input.getCommands().get(i).getFilters(),
                                                input.getCommands().get(i).getSortType()));

                        arrayResult.add(object);
                    }

                }
                if (input.getCommands().get(i).getObjectType().equals(Constants.MOVIES)) {
                    if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Movie.movieRating(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                movies,
                                                input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);
                    }
                    if (input.getCommands().get(i).getCriteria().equals("favorite")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Movie.movieFavorite(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                movies, users,
                                                input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);

                    }
                    if (input.getCommands().get(i).getCriteria().equals("longest")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Movie.movieLongest(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                movies, input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);

                    }
                    if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Movie.mostViewedMovies(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                movies, users,
                                                input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);

                    }
                }
                if (input.getCommands().get(i).getObjectType().equals(Constants.SHOWS)) {
                    if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Serial.serialRating(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                serials, input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);
                    }
                    if (input.getCommands().get(i).getCriteria().equals("favorite")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Serial.serialFavorite(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                serials, users,
                                                input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);

                    }
                    if (input.getCommands().get(i).getCriteria().equals("longest")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Serial.serialLongest(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                serials, input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);

                    }
                    if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        Serial.mostViewedSerial(
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType(),
                                                serials, users,
                                                input.getCommands().get(i).getFilters()));
                        arrayResult.add(object);

                    }
                }
                if (input.getCommands().get(i).getObjectType().equals(Constants.USERS)) {
                    if (input.getCommands().get(i).getCriteria().equals("num_ratings")) {
                        JSONObject object =
                                fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                        input.getCommands().get(i).getCriteria(),
                                        User.userQuery(users,
                                                input.getCommands().get(i).getNumber(),
                                                input.getCommands().get(i).getSortType()));
                        arrayResult.add(object);
                    }
                }
            } else if (input.getCommands().get(i).getActionType()
                    .equals(Constants.RECOMMENDATION)) {
                if (input.getCommands().get(i).getType().equals("standard")) {

                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.standardRec(users, movies, serials,
                                            input.getCommands().get(i).getUsername()));


                    arrayResult.add(object);
                }
                if (input.getCommands().get(i).getType().equals("best_unseen")) {

                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.bestUnseenRec(users, movies, serials,
                                            input.getCommands().get(i).getUsername()));


                    arrayResult.add(object);
                }
                if (input.getCommands().get(i).getType().equals("search")) {

                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.searchRec(users, movies, serials,
                                            input.getCommands().get(i).getUsername(),
                                            input.getCommands().get(i).getGenre()));


                    arrayResult.add(object);
                }
                if (input.getCommands().get(i).getType().equals("popular")) {

                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.popularRec(users, movies, serials,
                                            input.getCommands().get(i).getUsername()));


                    arrayResult.add(object);
                }
                if (input.getCommands().get(i).getType().equals("favorite")) {

                    JSONObject object =
                            fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                    input.getCommands().get(i).getCriteria(),
                                    User.favoriteRec(users, movies, serials,
                                            input.getCommands().get(i).getUsername()));


                    arrayResult.add(object);
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}

