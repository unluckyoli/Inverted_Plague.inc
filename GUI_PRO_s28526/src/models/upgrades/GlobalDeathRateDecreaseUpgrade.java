package models.upgrades;

import models.Country;
import models.GameModel;

public class GlobalDeathRateDecreaseUpgrade extends Upgrade {
    private double val;

    public GlobalDeathRateDecreaseUpgrade(double val, int cost) {
        super("Zwiekszenie opieki medycznejj", "Zmniejsza globalnie wspolczynnik smiertelności o " + val, cost);
        this.val = val;
    }

    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore() >= cost;
    }

    @Override
    public void applyUpgrade(GameModel model) {
        for (Country c : model.getCountries()) {
            double newDeathRate = c.getDeathRate() - val;
            if (newDeathRate < 0) newDeathRate = 0;
            c.setDeathRate(newDeathRate);
        }
    }
}
