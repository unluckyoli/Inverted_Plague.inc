package models;

public enum Difficulty {

    EASY(7.0, 30.0, 0.02),
    MEDIUM(15, 3.5, 0.02),
    HARD(18, 2.0, 0.1);



    private double infectionMultiplier;
    private double pointMultiplier;
    private double deathChance;

    Difficulty(double infectionMultiplier, double pointMultiplier, double deathChance) {
        this.infectionMultiplier = infectionMultiplier;
        this.pointMultiplier = pointMultiplier;
        this.deathChance = deathChance;
    }

    public double getInfectionMultiplier() {
        return infectionMultiplier;
    }

    public double getPointMultiplier() {
        return pointMultiplier;
    }

    public double getBaseDeathChance() {
        return deathChance;
    }
}
