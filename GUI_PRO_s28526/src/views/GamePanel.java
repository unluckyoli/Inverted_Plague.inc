package views;

import controllers.GameController;
import models.*;
import models.upgrades.*;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private final GameModel model;
    private final GameController controller;


    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JLabel infectedLabel;



    private WorldMapPanel mapPanel;

    private JButton upgradesButton;
    private JLabel vaccineLabel;



    public GamePanel(GameModel model, GameController controller) {

        this.model = model;
        this.controller = controller;


        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new GridLayout(1, 7));

        timeLabel = new JLabel("Time: 00:00");
        pointsLabel = new JLabel("Points: 0");
        infectedLabel = new JLabel("Infected: 0 / 0");
        vaccineLabel = new JLabel("Vaccine progress: 0%");



        upgradesButton = new JButton("Upgrades");

        upgradesButton.addActionListener(e -> {
            List<Upgrade> upgrades = availableUpgrades();
            UpgradesPanel upgradesPanel = new UpgradesPanel(model, upgrades);

            JOptionPane.showMessageDialog(this, upgradesPanel, "Upgrade Menu", JOptionPane.PLAIN_MESSAGE);
        });


        topPanel.add(timeLabel);
        topPanel.add(pointsLabel);
        topPanel.add(infectedLabel);
        topPanel.add(vaccineLabel);
        topPanel.add(upgradesButton);

        add(topPanel, BorderLayout.NORTH);




        mapPanel = new WorldMapPanel(model, model.getCountries(), model.getConnections());

        add(mapPanel, BorderLayout.CENTER);

        registerKeyBinding();
    }

    private List<Upgrade> availableUpgrades() {
        List<Upgrade> upgrades = new ArrayList<>();

        upgrades.add(new GlobalInfectionReductionUpgrade(5.0, 30));
        upgrades.add(new BoostVaccinationUpgrade(10.0, 40));
        upgrades.add(new GlobalVaccinationPowerUpgrade(50.0, 20));
        upgrades.add(new GlobalDeathRateDecreaseUpgrade(0.002, 35));
        upgrades.add(new CloseAllAirportsUpgrade(50));
        upgrades.add(new GlobalInfectionRateDecreaseUpgrade(0.01, 25));
        upgrades.add(new GlobalLockdownUpgrade(30.0, 60));
        upgrades.add(new SpeedUpGlobalVaccineResearchUpgrade(0.5, 50));
        upgrades.add(new NationalSponsorUpgrade(2.0, 40));

        return upgrades;
    }




    public void updateView() {
        int totalSec = model.getSecondsElapsed();
        int mm = totalSec / 60;
        int ss = totalSec % 60;
        String timeStr = String.format("%02d:%02d", mm, ss);
        timeLabel.setText("Time: " + timeStr);


        pointsLabel.setText("Points: " + model.getScore());

        long totalPopulation = model.getTotalPopulation();
        long totalInfected = model.getTotalInfected();
        long totalDead = model.getTotalDead();
        long livingPopulation = model.getTotalLiving();

        double infPct = (100.0 * totalInfected) / livingPopulation;
        infectedLabel.setText(String.format("Infected: %d / %d (%.2f%%)", totalInfected, livingPopulation, infPct));

        int progress = (int) model.getGlobalVaccineProgress();
        vaccineLabel.setText("vaccine: " + progress + "%");



        mapPanel.repaint();
    }





    private void registerKeyBinding() {
        setFocusable(true);
        requestFocusInWindow();

        KeyStroke ks = KeyStroke.getKeyStroke("ctrl shift Q");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(ks, "quitGame");

        getActionMap().put("quitGame", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {


                if (controller != null) {
                    controller.stopGame();
                }

                if (SwingUtilities.getWindowAncestor(GamePanel.this) instanceof MainWindow) {
                    ((MainWindow) SwingUtilities.getWindowAncestor(GamePanel.this)).showMenu();
                }
            }
        });
    }
}
