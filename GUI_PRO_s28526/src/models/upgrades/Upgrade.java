package models.upgrades;

import models.GameModel;

public abstract class Upgrade {
    protected String name;
    protected String description;
    protected int cost;
    protected boolean purchased = false;




    public Upgrade(String name, String desc, int cost) {
        this.name = name;
        this.description = desc;
        this.cost = cost;
    }



    public String getName() { return name; }


    public int getCost() { return cost; }

    public boolean isPurchased() { return purchased; }

    public abstract boolean canPurchase(GameModel model);

    public abstract void applyUpgrade(GameModel model);

    public void purchase(GameModel model) {
        purchased = true;
        applyUpgrade(model);
    }


    @Override
    public String toString() {
        return String.format("%s - %s (koszt: %d)", name, description, cost);
    }



}
