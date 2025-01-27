package models.upgrades;

import models.Country;
import models.GameModel;

public class GlobalInfectionRateDecreaseUpgrade extends Upgrade {
    private double val;

    public GlobalInfectionRateDecreaseUpgrade(double val, int cost) {
        super("Oslabienie wirusa!!", "Zmniejsza predkosc rozprzestrzeniania sie wirusaa w każdym kraju o "
                + val, cost);


        this.val = val;
    }

    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore() >= cost;
    }


    @Override
    public void applyUpgrade(GameModel model) {
        for (Country c : model.getCountries()) {

            double newRate = c.getInfectionRate() - val;

            if (newRate < 0) newRate = 0;
            c.setInfectionRate(newRate);
        }
    }
}

