package models.upgrades;

import models.*;

public class CloseTransportUpgrade extends Upgrade {
    private TransportType type;
    private String countryName;


    private double threshold;


    public CloseTransportUpgrade(String countryName, TransportType type, int cost, double threshold) {

        super("Zamknij " + type + " w " + countryName, "Zamyka wszystkie polaczenia " + type + " do kraju: " + countryName, cost);


        this.countryName = countryName;
        this.type = type;
        this.threshold = threshold;
    }

    @Override
    public boolean canPurchase(GameModel model) {


        if (model.getScore() < cost) return false;

        Country c = model.getCountryByName(countryName);

        if (c == null) return false;
        double localRatio = c.getInfectedPercentage() / 100.0;


        return localRatio >= threshold;
    }



    @Override
    public void applyUpgrade(GameModel model) {
        Country c = model.getCountryByName(countryName);

        if (c == null) return;

        for (TransportConnection tc : model.getConnections()) {
            if (tc.getTransportType() == type) {
                if (tc.getFrom() == c || tc.getTo() == c)
                    tc.setOpen(false);
            }
        }
    }
}
