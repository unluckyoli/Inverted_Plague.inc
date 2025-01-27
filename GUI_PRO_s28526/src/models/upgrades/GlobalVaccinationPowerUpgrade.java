package models.upgrades;

import models.Country;
import models.GameModel;

public class GlobalVaccinationPowerUpgrade extends Upgrade {
    private double val;

    public GlobalVaccinationPowerUpgrade(double val, int cost) {
        super("Nowa szcepionka!!! (+" + val + ")", "Zwieksza sile szczepionki, co przyspiesza tempo szczepien w kazdym kraju o " + val,
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
            double oldVal = country.getVaccinationRate();
            country.setVaccinationRate(oldVal + val);
        }
    }
}
