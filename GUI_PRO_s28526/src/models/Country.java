package models;

import java.util.HashMap;
import java.util.Map;

public class Country {

    private String name;
    private int population;
    private int infected;
    private int recovered;
    private int dead;
    private int vaccinated;




    private String continent;
    private boolean isIsland;




    private double infectionRate;
    private double recoveryRate;
    private double deathRate;

    private double vaccinationRate = 0.0;




    private float relX;
    private float relY;




    private Map<TransportType, TransportShutdownRule> shutdownRules = new HashMap<>();







    public Country(String name, int population, float relX, float relY) {
        this.name = name;
        this.population = population;
        this.infected = 0;

        this.recovered = 0;

        this.dead = 0;
        this.vaccinated = 0;

        this.continent = "ASIA";
        this.isIsland = false;

        this.infectionRate = 0.02;
        this.deathRate = 0.001;

        this.relX = relX;
        this.relY = relY;
    }

    public int getActiveCases() {
        return infected - recovered - dead;
    }

    public double getInfectedPercentage() {
        if (population <= 0) return 0.0;
        return (double) infected / (double) population * 100.0;
    }

    public int getNonVaccinatedLiving() {
        int living = population - dead;
        int nonVaccinated = living - vaccinated;
        return Math.max(0, nonVaccinated);
    }

    public Map<TransportType, TransportShutdownRule> getShutdownRules() {
        return shutdownRules;
    }

    public void updateInfection(
            double difficultyMultiplier,
            double baseDeathChance,
            double globalInfRatio,
            double cureRateThreshold,
            GameModel model
    ) {
        int active = getActiveCases();
        if (active <= 0) return;

        double localRatio = getInfectedPercentage()/100.0;
        double dynamicRate = (infectionRate + localRatio*0.03) * difficultyMultiplier;

        int canBeInfected = getNonVaccinatedLiving() - infected;
        int newInfections = (int) (active * dynamicRate);
        newInfections = Math.min(newInfections, Math.max(canBeInfected, 0));


        int newVaccinated = 0;

        double effectiveDeathChance = 0.0;
        if (globalInfRatio >= 0.15) {
            effectiveDeathChance = baseDeathChance + localRatio*0.01;
        }
        int potentialDeaths = (int)(active * effectiveDeathChance);
        int newDeaths = Math.min(potentialDeaths, active);

        infected  += newInfections;
        //recovered += newRecoveries;
        dead      += newDeaths;

        vaccinated += newVaccinated;

        if (vaccinated > (population - dead)) {
            vaccinated = population - dead;
        }

        if (infected > population)  infected = population;
        if (recovered > infected)   recovered = infected;
        if (dead > population)      dead = population;
    }





    public void updateVaccination(GameModel model) {
        int notVaccinated = getNonVaccinatedLiving();
        if (notVaccinated <= 0)
            return;


        int time = model.getVaccineCampaignSeconds();
        double base = 1000;
        double growthFactor = 0.35;
        double effectiveBase = base + vaccinationRate;



        int newVaccinations = (int)(effectiveBase * Math.exp(growthFactor * time));

        if (newVaccinations > notVaccinated) {
            newVaccinations = notVaccinated;
        }



        int infectedNotVaccined = Math.min(infected, notVaccinated);


        double infectedRate = 0.0;
        if (notVaccinated > 0) {
            infectedRate = (double) infectedNotVaccined / (double) notVaccinated;
        }

        int vaccinatedToInfected = (int) Math.round(newVaccinations * infectedRate);




        if (vaccinatedToInfected > infected) {
            vaccinatedToInfected = infected;
        }


        infected -= vaccinatedToInfected;
        vaccinated += newVaccinations;


        if (vaccinated > (population - dead)) {
            vaccinated = population - dead;
        }
    }



    public int getHealthyNotVaccinated() {
        int living = population - dead;
        int healthy = living - getActiveCases() - recovered;
        int healthyNotVaccinated = healthy - vaccinated;
        return Math.max(0, healthyNotVaccinated);
    }






    public double getVaccinationRate() {
        return vaccinationRate;
    }
    public void setVaccinationRate(double val) {
        vaccinationRate = val;
    }

    public String getName() { return name; }
    public int getPopulation() { return population; }
    public int getInfected() { return infected; }
    public void setInfected(int infected) { this.infected = infected; }
    public int getRecovered() { return recovered; }
    public void setRecovered(int recovered) { this.recovered = recovered; }
    public int getDead() { return dead; }
    public void setDead(int dead) { this.dead = dead; }
    public int getVaccinated() { return vaccinated; }
    public void setVaccinated(int vaccinated) { this.vaccinated = vaccinated; }

    public float getRelX() { return relX; }
    public float getRelY() { return relY; }

    public double getInfectionRate() { return infectionRate; }
    public void setInfectionRate(double infectionRate) { this.infectionRate = infectionRate; }

    public double getDeathRate() { return deathRate; }
    public void setDeathRate(double deathRate) { this.deathRate = deathRate; }

    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }

    public boolean isIsland() { return isIsland; }
    public void setIsland(boolean island) { isIsland = island; }


}
