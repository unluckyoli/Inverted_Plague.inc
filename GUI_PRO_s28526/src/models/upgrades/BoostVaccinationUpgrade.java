package models.upgrades;

import models.Country;
import models.GameModel;

public class BoostVaccinationUpgrade extends Upgrade {
    private double val;




    public BoostVaccinationUpgrade(double val, int cost) {
        super("Szczepionki z samolotu (" + val + "%)", "Zaszczepienie ludzi o  " + val + "% na calym swiecie",
                cost);
        this.val = val;
    }



    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore() >= cost;
    }

    @Override
    public void applyUpgrade(GameModel model) {
        for (Country c : model.getCountries()) {
            int newlyVaccinated = (int) (c.getNonVaccinatedLiving() * (val / 100.0));
            c.setVaccinated(c.getVaccinated() + newlyVaccinated);
        }
    }
}
