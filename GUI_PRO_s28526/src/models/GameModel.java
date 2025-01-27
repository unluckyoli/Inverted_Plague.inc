package models;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {

    private List<Country> countries;
    private List<TransportConnection> connections;
    private Difficulty difficulty;



    private int timer = 0;
    private int points = 0;



    private boolean gameOver = false;
    private boolean gameWon = false;
    private String gameOverReason = "";


    private boolean didWarnAboutDeaths = false;


    private double globalVaccineProgress = 0.0;
    private double globalVaccineSpeed = 10;                                      //szybkosc szczepionki mniej wiecej bo nizej jest dzialanie
    private boolean globalVaccinationActive = false;
    private int vaccineInUseSecconds = 0;
    private long lastTotalVaccinated = 0;


    private double upgradeExtraPoints = 0.0;

    private double pointsForOneSaved = 0.0001;                                   //punktow za jedna uratowana osobe

    private List<TransportAnimation> animations = new ArrayList<>();






    public GameModel() {
        countries = new ArrayList<>();
        connections = new ArrayList<>();
        difficulty = Difficulty.EASY;

    }


    public void initGameData() {
        //System.out.println("JESTEM W INIT GAME W GAMEMODEL");
        countries.clear();
        connections.clear();
        animations.clear();

        gameOver = false;
        gameWon = false;
        gameOverReason = "";
        timer = 0;
        points = 0;
        lastTotalVaccinated = 0;




        Country chiny = new Country("CHINY-HUBEI", 59000000, 0.71f, 0.36f);
        chiny.setContinent("ASIA");
        chiny.setIsland(false);

        Country polska=new Country("POLSKA", 38000000, 0.47f, 0.21f);
        polska.setContinent("EUROPE");
        polska.setIsland(false);

        Country senegal=new Country("SENEGAL", 17000000, 0.37f, 0.45f);
        senegal.setContinent("AFRICA");
        senegal.setIsland(false);

        Country argentyna =new Country("ARGENTYNA", 45000000, 0.23f, 0.78f);
        argentyna.setContinent("SOUTH AMERICA");
        argentyna.setIsland(false);

        Country usa = new Country("USA", 330000000, 0.10f, 0.28f);
        usa.setContinent("NORTH AMERICA");
        usa.setIsland(false);

        Country rosja = new Country("ROSJA", 190000000, 0.67f, 0.09f);
        rosja.setContinent("ASIA");
        rosja.setIsland(false);

        Country australia =new Country("AUSTRALIA", 25000000, 0.82f, 0.77f);
        australia.setContinent("AUSTRALIA");
        australia.setIsland(false);

        Country japonia= new Country("JAPONIA", 125000000, 0.80f, 0.32f);
        japonia.setContinent("ASIA");
        japonia.setIsland(true);

        Country grenlandia =new Country("GRENLANDIA", 57000, 0.33f, 0.05f);
        grenlandia.setContinent("GRENLAND");
        grenlandia.setIsland(true);

        Country rpa =new Country("RPA", 25000000, 0.49f, 0.75f);
        rpa.setContinent("AFRICA");
        rpa.setIsland(false);



        chiny.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(25, 0.15));
        chiny.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(30, 0.25));
        chiny.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(30, 0.3));
        chiny.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(25, 0.1));

        polska.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(30, 0.05));
        polska.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(40, 0.10));
        polska.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(45, 0.08));
        polska.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(60, 0.15));

        senegal.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(15, 0.25));
        senegal.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(30, 0.15));
        senegal.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(25, 0.08));
        senegal.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(20, 0.20));

        argentyna.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(30, 0.05));
        argentyna.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(10, 0.10));
        argentyna.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(25, 0.08));
        argentyna.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(30, 0.15));

        usa.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(20, 0.25));
        usa.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(50, 0.15));
        usa.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(35, 0.10));
        usa.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(20, 0.25));

        rosja.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(40, 0.35));
        rosja.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(10, 0.20));
        rosja.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(25, 0.10));
        rosja.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(40, 0.25));

        australia.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(20, 0.10));
        australia.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(20, 0.15));
        australia.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(15, 0.20));
        australia.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(65, 0.25));

        japonia.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(40, 0.15));
        japonia.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(30, 0.15));
        japonia.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(25, 0.20));
        japonia.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(10, 0.10));

        grenlandia.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(20, 0.05));
        grenlandia.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(30, 0.15));
        grenlandia.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(15, 0.10));
        grenlandia.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(40, 0.25));

        rpa.getShutdownRules().put(TransportType.CAR,      new TransportShutdownRule(20, 0.35));
        rpa.getShutdownRules().put(TransportType.TRAIN,    new TransportShutdownRule(20, 0.30));
        rpa.getShutdownRules().put(TransportType.SHIP,     new TransportShutdownRule(35, 0.10));
        rpa.getShutdownRules().put(TransportType.AIRPLANE, new TransportShutdownRule(40, 0.25));









        countries.add(chiny);
        countries.add(polska);
        countries.add(senegal);
        countries.add(argentyna);
        countries.add(usa);
        countries.add(rosja);
        countries.add(australia);
        countries.add(japonia);
        countries.add(grenlandia);
        countries.add(rpa);


        //System.out.println("DODALISMY KRAJE OK");



        getCountryByName("CHINY-HUBEI").setInfected(100);

        createRandomConnections();



        for (TransportConnection tc : connections) {
            if (tc.isOpen()) {
                animations.add(new TransportAnimation(tc, 0.04));
            }
        }


    }


    public Country getCountryByName(String name) {

        for (Country c : countries) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }



    public void createRandomConnections() {
        Random rand = new Random();
        TransportType[] transportTypes = TransportType.values();

        for (Country country : countries) {
            int howManyConnectionns = rand.nextInt(3) + 2;
            int created = 0;
            int attempts = 0;


            while (created < howManyConnectionns && attempts < 20) {
                attempts++;

                if (countConnectionsFor(country) >= 4)
                    break;

                Country otherCountry = countries.get(rand.nextInt(countries.size()));
                if (otherCountry == country)
                    continue;
                if (countConnectionsFor(otherCountry) >= 4)
                    continue;
                if (connectionExists(country, otherCountry))
                    continue;


                TransportType transportType = transportTypes[rand.nextInt(transportTypes.length)];


                if (!canConnect(country, otherCountry, transportType)) {
                    continue;
                }



                connections.add(new TransportConnection(country, otherCountry, transportType));
                created++;


            }
        }
    }









    private boolean canConnect(Country a, Country b, TransportType type) {
        switch (type) {
            case AIRPLANE:
                return true;

            case SHIP:
                return true;

            case CAR:
            case TRAIN:
                if (!a.getContinent().equalsIgnoreCase(b.getContinent())) {
                    return false;
                }
                if (a.isIsland() || b.isIsland()) {
                    return false;
                }
                return true;
        }
        return true;
    }




    private int countConnectionsFor(Country country) {
        int count = 0;

        for (TransportConnection transportConnection : connections) {
            if (transportConnection.getFrom() == country || transportConnection.getTo() == country) {
                count++;
            }
        }

        return count;
    }




    private boolean connectionExists(Country a, Country b) {
        for (TransportConnection transportConnection : connections) {
            if ((transportConnection.getFrom() == a && transportConnection.getTo() == b)
                    || (transportConnection.getFrom() == b && transportConnection.getTo() == a)) {
                return true;
            }
        }
        return false;
    }






    public void tick() {
        if (!gameOver) {

            timer++;
            points += (int) (1 * difficulty.getPointMultiplier());
            points += (int) upgradeExtraPoints;


            if (globalVaccinationActive) {
                vaccineInUseSecconds++;
            }


            long currentTotalVaccinated = 0;
            for (Country country : countries) {
                currentTotalVaccinated += country.getVaccinated();
            }



            long newlyVaccinated = currentTotalVaccinated - lastTotalVaccinated;

            if (newlyVaccinated > 0) {
                points += (int) (newlyVaccinated * pointsForOneSaved);
            }



            lastTotalVaccinated = currentTotalVaccinated;


            if (!globalVaccinationActive) {
                double increment = globalVaccineSpeed * (1.0 / difficulty.getInfectionMultiplier());

                globalVaccineProgress += increment;
                if (globalVaccineProgress >= 100.0) {
                    globalVaccineProgress = 100.0;
                    startGlobalVaccination();
                }
            }

            updateInfectionsInCountry();
            if (globalVaccinationActive) {
                updateVaccinations();
            }

            for (TransportAnimation animation : animations) {
                animation.update();
            }

            updateGlobalInfectionFromCountryToCountry();
            checkGameOver();
        }
    }


//wirus w kraju
    private void updateInfectionsInCountry() {
        double globalInfectionRate = getTotalInfected()/(double)getTotalPopulation();

        if (!didWarnAboutDeaths && globalInfectionRate >= 0.15) {
            didWarnAboutDeaths = true;


            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(

                        null,
                        "Uwaga! Wirus zaczal zabijac!!",
                        "nowy covid alert",
                        JOptionPane.WARNING_MESSAGE
                );
            });
        }



        for (Country country : countries) {

            country.updateInfection(
                    difficulty.getInfectionMultiplier(),
                    difficulty.getBaseDeathChance(),
                    globalInfectionRate,
                    0.02,
                    this
            );
        }
    }

    //przenoszenie wirusa z kraju do kraju
    private void updateGlobalInfectionFromCountryToCountry() {
        for (TransportConnection tc : connections) {
            if (!tc.isOpen()) continue;

            Country from = tc.getFrom();
            Country to = tc.getTo();

            double ratio = from.getInfectedPercentage() / 100.0;
            double transportMultiplier = getTransportMultiplier(tc.getTransportType());
            int canBeInfected = to.getNonVaccinatedLiving() - to.getInfected();
            int potentialInfections = (int) (from.getActiveCases()
                    * ratio
                    * transportMultiplier
                    * difficulty.getInfectionMultiplier()
                    * 0.4);



            int actualNew = Math.min(potentialInfections, Math.max(0, canBeInfected));
            if (actualNew > 0) {
                to.setInfected(to.getInfected() + actualNew);
            }
        }
    }



    private void startGlobalVaccination() {
        if (globalVaccinationActive) return;

        globalVaccinationActive = true;

        vaccineInUseSecconds = 0;

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    null,
                    "Globalna szczepionka zostala skonczona. Rozpoczento szczepienia na calym swuecie",
                    "szczepienia alert!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });


        for (Country country : countries) {
            int newlyVaccinated = (int)(country.getPopulation() * 0.01);

            if (newlyVaccinated > country.getNonVaccinatedLiving()) {
                newlyVaccinated = country.getNonVaccinatedLiving();
            }

            country.setVaccinated(country.getVaccinated() + newlyVaccinated);
        }
    }


    private void updateVaccinations() {
        for (Country c : countries) {
            c.updateVaccination(this);
        }
    }





    private double getTransportMultiplier(TransportType transportType) {
        return switch (transportType) {
            case AIRPLANE -> 1.5;
            case TRAIN -> 1.2;
            case CAR -> 1.0;
            case SHIP -> 0.8;
        };
    }

    private void checkGameOver() {
        long totalPop = getTotalPopulation();
        long totalInf = getTotalInfected();
        long totalDead = getTotalDead();


        if (totalInf + totalDead >= totalPop) {
            gameOver = true;
            gameWon = false;
            gameOverReason = "Przegrana! Wszyscy zostali zarazeni lub zmarli";
            return;
        }


        if (totalInf == 0&& totalPop > 0) {

            gameOver = true;
            gameWon = true;
            gameOverReason = "Wygrana! Zakazenie zostalo calkowicie wyeliminowane";
        }
    }




    public List<TransportAnimation> getAnimations() {
        return animations;
    }
    public long getLastTotalVaccinated() {
        return lastTotalVaccinated;
    }
    public void setLastTotalVaccinated(long val) {
        lastTotalVaccinated = val;
    }

    public double getPointsForOneSaved() {
        return pointsForOneSaved;
    }
    public void setPointsForOneSaved(double val) {
        pointsForOneSaved = val;
    }

    public double getSponsorExtraPoints() {
        return upgradeExtraPoints;
    }
    public void setSponsorExtraPoints(double val) {
        upgradeExtraPoints = val;
    }

    public int getVaccineCampaignSeconds() {
        return vaccineInUseSecconds;
    }

    public double getGlobalVaccineProgress() {
        return globalVaccineProgress;
    }
    public double getGlobalVaccineSpeed() { return globalVaccineSpeed; }
    public void setGlobalVaccineSpeed(double speed) { this.globalVaccineSpeed = speed; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }

    public int getSecondsElapsed() { return timer; }
    public int getScore() { return points; }
    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public void setScore(int score){
        this.points = score;
    }

    public List<Country> getCountries() { return countries; }
    public List<TransportConnection> getConnections() { return connections; }

    public long getTotalPopulation() {
        long pop = 0;
        for (Country c : countries) pop += c.getPopulation();
        return pop;
    }

    public long getTotalInfected() {
        long inf = 0;
        for (Country c : countries) inf += c.getInfected();
        return inf;
    }

    public long getTotalDead() {
        long d = 0;
        for (Country c : countries) d += c.getDead();
        return d;
    }

    public long getTotalLiving() {
        return getTotalPopulation() - getTotalDead();
    }
}
