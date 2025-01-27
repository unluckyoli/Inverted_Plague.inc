package models.upgrades;

import models.GameModel;
import models.TransportConnection;
import models.TransportType;

public class CloseAllAirportsUpgrade extends Upgrade {


    public CloseAllAirportsUpgrade(int cost) {
        super("Zamkniecie lotnisk", "Zamyka wszystkie loty na calym swiecie", cost);
    }

    @Override
    public boolean canPurchase(GameModel model) {
        return model.getScore() >= cost;
    }


    @Override
    public void applyUpgrade(GameModel model) {
        for (TransportConnection tc : model.getConnections()) {

            if (tc.getTransportType() == TransportType.AIRPLANE) {
                tc.setOpen(false);
            }
        }
    }
}
