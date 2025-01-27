package models.upgrades;


import models.GameModel;

public class NationalSponsorUpgrade extends Upgrade {
    private double val;

    public NationalSponsorUpgrade(double val, int cost) {
        super("Wielki patriotyzm, jedna skarbonka", "Dodatkowe wsparcie finansowe zapewnia +" + val + " punktów na sekunde", cost);
        this.val = val;
    }

    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore() >= cost;
    }

    @Override
    public void applyUpgrade(GameModel model) {
        model.setSponsorExtraPoints(

                model.getSponsorExtraPoints() + val
        );
    }
}

