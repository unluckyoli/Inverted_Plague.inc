package models.upgrades;

import models.Country;
import models.GameModel;

public class GlobalLockdownUpgrade extends Upgrade {
    private double val;

    public GlobalLockdownUpgrade(double val, int cost) {
        super("Swiatowy Lockdown (-" + val + "% aktywnych przypadkow zakazenia)", "Zmniejsza aktywne zakazenia we wszystkich krajach o " + val + "%",
                cost);
        this.val = val;
    }

    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore() >= cost;
    }



    @Override
    public void applyUpgrade(GameModel model) {
        for (Country country : model.getCountries()) {

            int active = country.getActiveCases();
            if (active <= 0) continue;

            int toRemove = (int)(active *(val / 100.0));


            int newInfected = country.getInfected() - toRemove;
            if (newInfected < 0)
                newInfected = 0;

            country.setInfected(newInfected);


            int newRecovered = country.getRecovered() + toRemove;


            if (newRecovered > country.getInfected()) {
                newRecovered = country.getInfected();
            }


            country.setRecovered(newRecovered);
        }
    }
}
