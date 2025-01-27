package models.upgrades;

import models.GameModel;

public class SpeedUpGlobalVaccineResearchUpgrade extends Upgrade {
    private double val;

    public SpeedUpGlobalVaccineResearchUpgrade(double val, int cost) {
        super("Nowy Einstein (+" + (int)(val * 100) + "% szybkosc)",
                "Przyspiesza globalne badania nad szczepionka zwiekszajac tempo jej rozwoju o " + (val * 100) + "%", cost);

        this.val = val;
    }


    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore() >= cost;
    }


    @Override
    public void applyUpgrade(GameModel model) {
        model.setGlobalVaccineSpeed(model.getGlobalVaccineSpeed() + val);

    }
}
