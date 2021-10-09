package tasks;

import actor.ActorsAwards;
import common.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;

    /**
     * setter for awards
     */
    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    /**
     * FUNCTION TO GET NUMBER OF TOTAL AWARDS
     */
    int getTotalOfAwards(final Map<actor.ActorsAwards, Integer> awrads) {
        int sum = 0;
        for (Map.Entry<actor.ActorsAwards, Integer> entry : awrads.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }


    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    /**
     * getter for name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * getter for lista de filme
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }

    /**
     * setter for lista de filme
     */
    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    /**
     * getter pentru mapa de awards
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * getter pentru descrierea carieriei
     */
    public String getCareerDescription() {
        return careerDescription;
    }

    /**
     * setter pentru descrierea carieri
     */
    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    /**
     * ToString override
     */
    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }

    // -----------------> QUERY <-----------------

    /**
     * metoda pentru a calculca average-ul unui actor
     */
    public Double getAverageForActors(final List<Movie> movies) {
        int count = 0;
        Double medie = 0.0;
        for (int i = 0; i < movies.size(); i++) {
            if (this.getFilmography().contains(movies.get(i).getTitle())) {
                if (movies.get(i).getAverageOfRatings() != 0) {
                    count++;
                    medie += movies.get(i).getAverageOfRatings();
                }
            }
        }
        medie = medie / count;
        return medie;
    }

    /**
     * QUERY ACTOR AVERAGE
     */
    public static String average(final int n, final List<Actor> actors, final List<Movie> movies,
                                 final List<Serial> serials,
                                 final String sortType) {
        List<String> finalArrayList = new ArrayList<>();
        List<String> actorList = new ArrayList<>();
        List<String> sortedAscActors = new ArrayList<>();
        List<String> sortedDescActors = new ArrayList<>();
        Map<String, Double> mapOfActors = new TreeMap<>();
        for (Actor actor : actors) {
            mapOfActors.put(actor.getName(), 0.0);
        }
        for (Actor actor : actors) {
            int count = 0;
            for (int j = 0; j
                    < movies.size(); j++) {
                if (actor.getFilmography().contains(movies.get(j).getTitle())) {
                    if (mapOfActors.containsKey(actor.getName())) {
                        if (mapOfActors.get(actor.getName())
                                < movies.get(j).getAverageOfRatings()) {
                            mapOfActors.put(actor.getName(),
                                    movies.get(j).getAverageOfRatings());
                        }
                        count++;
                    }
                    if (count > 1) {
                        for (int k = 0; k < movies.size(); k++) {
                            if (actor.getFilmography().contains(movies.get(k).getTitle())) {
                                if (/*mapOfActors.get(actor.getName()) != 0*/
                                        movies.get(k).getAverageOfRatings() != 0) {
                                    mapOfActors.put(actor.getName(),
                                            (actor.getAverageForActors(movies)));
                                }
                            }
                        }
                    }
                }

            }
        }
        for (Actor actor : actors) {
            for (Serial serial : serials) {
                if (serial.getCast().contains(actor.getName())) {
                    mapOfActors.put(actor.getName(), serial.getAverageOfRatings());
                }
            }
        }
        mapOfActors.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(e -> sortedAscActors.add(e.getKey()));

        for (Map.Entry<String, Double> entry : mapOfActors.entrySet()) {
            if (entry.getValue() == 0) {
                actorList.add(entry.getKey());
            }
        }
        sortedAscActors.removeAll(actorList);


        for (int i = sortedAscActors.size() - 1; i >= 0; i--) {
            sortedDescActors.add(sortedAscActors.get(i));
        }
        if (sortType.equals("asc")) {
            if (sortedDescActors.size() < n) {
                for (int i = 0; i < sortedDescActors.size(); i++) {
                    finalArrayList.add(sortedAscActors.get(i));
                }
            } else {
                for (int i = 0; i < n; i++) {
                    finalArrayList.add(sortedAscActors.get(i));
                }
            }
        }
        if (sortType.equals("desc")) {
            if (sortedDescActors.size() < n) {
                finalArrayList.addAll(sortedDescActors);
            } else {
                for (int i = 0; i < n; i++) {
                    finalArrayList.add(sortedDescActors.get(i)); // ad daca da
                }
            }
        }
        return "Query result: " + finalArrayList;
    }

    /**
     * QUERY ACTOR AWARDS
     */

    public static String awards(final List<Actor> actors, final List<List<String>> filters,
                                final String sortType) {
        List<String> actorsName = new ArrayList<>();
        List<String> finalArray = new ArrayList<>();
        List<String> sortedAscArray = new ArrayList<>();
        List<String> sortedDescArray = new ArrayList<>();
        HashMap<String, Integer> mapOfActors = new HashMap<>();
        for (Actor actor : actors) {
            if (actor.getAwards().size() >= filters.get(Constants.THREE).size()) {
                {
                    actorsName.add(actor.getName());
                }
            }
        }
        for (Actor actor : actors) {
            for (String s : actorsName) {
                if (actor.getName().equals(s)) {
                    mapOfActors.put(s,
                            actor.getTotalOfAwards(actor.getAwards()));
                }
            }
        }
        mapOfActors.values().removeIf(f -> f == 0);
        mapOfActors.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> sortedAscArray.add(e.getKey()));
        ArrayList<String> emptyList = new ArrayList<>();
        for (Actor actor : actors) {
            List<String> awardsList = new ArrayList<>();
            for (Map.Entry<ActorsAwards, Integer> entry : actor.getAwards().entrySet()) {
                awardsList.add(entry.getKey().toString());
            }
            for (String s : awardsList) {
                if (filters.get(Constants.THREE).contains(s)) {
                    emptyList.add(actor.getName());
                }
            }
        }
        sortedAscArray.removeIf(e -> !emptyList.contains(e));
        for (Actor actor : actors) {
            if (sortedAscArray.contains(actor.getName())) {
                if (actor.getAwards().size() < filters.get(Constants.THREE).size()) {
                    sortedAscArray.remove(actor.getName());
                }
                List<String> awardsList = new ArrayList<>();
                for (Map.Entry<ActorsAwards, Integer> entry : actor.getAwards()
                        .entrySet()) {
                    awardsList.add(entry.getKey().toString());
                }
                for (int j = 0; j < filters.get(Constants.THREE).size(); j++) {
                    if (!awardsList.contains(filters.get(Constants.THREE).get(j))) {
                        sortedAscArray.remove(actor.getName());
                    }
                }

            }
        }

        for (int i = sortedAscArray.size() - 1; i >= 0; i--) {
            sortedDescArray.add(sortedAscArray.get(i));
        }
        if (sortType.equals("asc")) {
            finalArray.addAll(sortedAscArray);
        }
        if (sortType.equals("desc")) {
            finalArray.addAll(sortedDescArray);
        }
        return "Query result: " + finalArray;
    }

    /**
     * FILTER DESCRIPTION QURRY
     */
    public static String filterDescription(final List<Actor> actors,
                                           final List<List<String>> filters,
                                           final String sortType) {
        List<String> finalArray = new ArrayList<>();
        List<String> namesWithConditionOk = new ArrayList<>();
        List<String> sortedDescActors = new ArrayList<>();
        for (Actor actor : actors) {
            boolean checker = true;
            String newString = actor.getCareerDescription().toLowerCase()
                    .replaceAll("award", "ok123456789");
            String newString2 = newString.replaceAll("ok123456789s", "bad123456789");
            if (filters.get(2).contains("award")) {
                filters.get(2).remove("award");
                filters.get(2).add("ok123456789");
            }
            for (int j = 0; j < filters.get(2).size(); j++) {
                if (!newString2.contains(filters.get(2).get(j))) {
                    checker = false;
                    break;
                }
            }
            if (checker) {
                namesWithConditionOk.add(actor.getName());
            }
        }
        Collections.sort(namesWithConditionOk);
        List<String> sortedAscActors = new ArrayList<>(namesWithConditionOk);
        for (int i = sortedAscActors.size() - 1; i >= 0; i--) {
            sortedDescActors.add(sortedAscActors.get(i));
        }
        if (sortType.equals("asc")) {
            finalArray.addAll(sortedAscActors);
        } else {
            finalArray.addAll(sortedDescActors);
        }
        return "Query result: " + finalArray;
    }


    // ---------------------------------------------

    /**
     * SORTING FUNCTION BY VALUE OF A MAP
     */
    public static HashMap<String, Double> sortByValue(final HashMap<String, Double> hm) {
        List<Map.Entry<String, Double>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


}


