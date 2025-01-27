package views;

import models.*;
import models.upgrades.CloseTransportUpgrade;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CountryButton extends JButton {

    private Country country;
    private GameModel model;




    public CountryButton(Country country, GameModel model) {
        this.country = country;
        this.model = model;

        setOpaque(true);

        setContentAreaFilled(true);

        setFocusPainted(false);

        setBorderPainted(false);


        updateText();
        updateButtonColor();

        addActionListener(e -> showCountryInfo());
    }

    private void updateText() {
        double infeectionPercentage = country.getInfectedPercentage();

        setText(String.format("%s (%.1f%%)", country.getName(), infeectionPercentage));
    }




    private void updateButtonColor() {
        if (country.getInfected() > 0) {
            setBackground(new Color(255, 100, 100));
        } else {
            setBackground(new Color(180, 255, 180));
        }
    }




    private void showCountryInfo() {


        String info = String.format(
                "Populacja: %d\nZarazeni: %d\nZaszczepieni: %d\nZmarli: %d\nZdrowi: %d",
                country.getPopulation(),
                country.getInfected(),
                country.getVaccinated(),
                country.getDead(),
                country.getHealthyNotVaccinated()
        );



        int choice = JOptionPane.showOptionDialog(
                this,

                info + "\n\n\nCzy chcesz kupic lokalne ulepszenia dla tego kraju?",

                country.getName(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, null, null
        );



        if (choice == JOptionPane.YES_OPTION) {
            openLocalUpgradesDialog();
        }
    }

    private void openLocalUpgradesDialog() {
        List<TransportConnection> actualConnections = new ArrayList<>();
        for (TransportConnection transportConnection : model.getConnections()) {
            if (transportConnection.getFrom() == country || transportConnection.getTo() == country) {
                actualConnections.add(transportConnection);
            }
        }


        Set<TransportType> existingTransports = new HashSet<>();
        for (TransportConnection tc : actualConnections) {
            existingTransports.add(tc.getTransportType());
        }


        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Wybierz transport do zamkniecia w " + country.getName() + ":"));


        for (TransportType transportType : existingTransports) {
            TransportShutdownRule rule = country.getShutdownRules().get(transportType);
            if (rule == null) {
                continue;
            }


            String buttonText = String.format(
                    "Zamknij %s (koszt=%d, min. %.1f%% inf.)",
                    transportType.name(), rule.getCost(), rule.getThreshold()*100
            );
            JButton button = new JButton(buttonText);


            button.addActionListener(ev -> tryPurchaseUpgrade(transportType, rule));

            panel.add(button);
        }


        if (existingTransports.isEmpty()) {
            panel.add(new JLabel("Brak polaczen transportowych do zamkniecia:)"));
        }


        JOptionPane.showMessageDialog(
                this,
                panel,
                "zamykanie granic - " + country.getName(),
                JOptionPane.PLAIN_MESSAGE
        );
    }


    private void tryPurchaseUpgrade(TransportType type, TransportShutdownRule rule) {
        int cost = rule.getCost();

        double threshold = rule.getThreshold();



        CloseTransportUpgrade upgrade = new CloseTransportUpgrade(
                country.getName(),
                type,
                cost,
                threshold
        );




        if (!upgrade.canPurchase(model)) {
            JOptionPane.showMessageDialog(

                    this,
                    String.format(
                            "Nie spelniasz warunkow zakupu:\n- za malo punktów (>= %d)\n- lub ponizej %.1f%% zainfekowania panstwa",
                            cost, threshold*100
                    ),
                    "blad zakupu",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }


        model.setScore(model.getScore() - cost);
        upgrade.purchase(model);

        JOptionPane.showMessageDialog(
                this,
                "Ulepszenie kupione! Zamykanie " + type + " w " + country.getName(),
                "brawo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        if (country.getInfected() > 0) {
            g2d.setColor(new Color(255, 150, 150, 180));
        } else {
            g2d.setColor(new Color(255, 255, 200, 180));
        }
        g2d.fillRoundRect(0, 0, w, h, 20, 20);

        super.paintComponent(g2d);

        g2d.dispose();
    }

    public void refreshButton() {
        updateText();
        updateButtonColor();
        repaint();
    }




}
