package views;

import models.GameModel;
import models.upgrades.Upgrade;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpgradesPanel extends JPanel {

    private JList<Upgrade> upgradesList;
    private JButton buyButton;
    private GameModel model;

    public UpgradesPanel(GameModel model, List<Upgrade> upgrades) {
        this.model = model;

        setLayout(new BorderLayout());


        Upgrade[] upgradesArray = upgrades.toArray(new Upgrade[0]);

        upgradesList = new JList<>(upgradesArray);



        buyButton = new JButton("Kup");


        buyButton.addActionListener(e -> {

            Upgrade selected = upgradesList.getSelectedValue();

            if (selected != null) {
                if (selected.canPurchase(model)) {
                    model.setScore(model.getScore() - selected.getCost());
                    selected.purchase(model);


                    JOptionPane.showMessageDialog(
                            this,
                            "Kupiono: " + selected.getName()
                    );


                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Nie spelniasz warunkow/ za malo punktow",
                            "Nie mozesz kupic",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });


        add(new JScrollPane(upgradesList), BorderLayout.CENTER);
        add(buyButton, BorderLayout.SOUTH);
    }
}
