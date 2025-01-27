package models;

public class TransportShutdownRule {
    private int cost;
    private double threshold;

    public TransportShutdownRule(int cost, double threshold) {
        this.cost = cost;
        this.threshold = threshold;


    }



    public int getCost() { return cost; }
    public double getThreshold() { return threshold; }
}
