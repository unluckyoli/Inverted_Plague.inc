package models.upgrades;

import models.Country;
import models.GameModel;

public class GlobalInfectionReductionUpgrade extends Upgrade {
    private double val;


    public GlobalInfectionReductionUpgrade(double val, int cost) {
        super("Łaska Boza -"+val+"%", "Zmniejsza liczbe zakazonych we wszystkich krajach o " + val + "%", cost);
        this.val = val;
    }




    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore()>= cost;
    }



    @Override
    public void applyUpgrade(GameModel model) {
        for (Country country : model.getCountries()) {
            int infected = country.getInfected();


            int newInfected = (int) (infected * (1.0 - (val / 100.0)));
            if (newInfected < 0) newInfected = 0;
            country.setInfected(newInfected);

            if (country.getRecovered() > newInfected) {
                country.setRecovered(newInfected);
            }
        }
    }
}

