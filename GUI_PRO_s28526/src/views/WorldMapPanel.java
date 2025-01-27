package views;

import models.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;


    public class WorldMapPanel extends JPanel {
        private Image mapka;
        private List<Country> countries;
        private List<TransportConnection> connections;
        private GameModel model;

        private Image carIcon, planeIcon, trainIcon, shipIcon;



        public WorldMapPanel(GameModel model, List<Country> countries, List<TransportConnection> connections) {
            this.model = model;
            this.countries = countries;
            this.connections = connections;

            carIcon   = loadIcon("/images/autoo.png");
            planeIcon = loadIcon("/images/samolot.png");
            trainIcon = loadIcon("/images/pociag.png");
            shipIcon  = loadIcon("/images/statek.png");

            URL url = getClass().getResource("/images/swiat.jpg");


            if (url != null) {
                mapka = new ImageIcon(url).getImage();
            }


            setLayout(null);
        }





        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            if (mapka != null) {
                g.drawImage(mapka, 0, 0, getWidth(), getHeight(), this);
            }




            Graphics2D g2d = (Graphics2D) g.create();

            if (connections != null) {

                for (TransportConnection tc : connections) {
                    if (tc.isOpen())
                        drawConnection(g2d, tc);
                }
            }




            for (TransportAnimation animation : model.getAnimations()) {
                drawMovingTransportIcon(g2d, animation);
            }

            g2d.dispose();




            removeAll();
            for (Country c : countries) {

                CountryButton button = new CountryButton(c, model);
                int width  = 140;
                int height = 25;

                int x = (int) (c.getRelX() * getWidth()  - width / 2);
                int y = (int) (c.getRelY() * getHeight() - height / 2);
                button.setBounds(x, y, width, height);
                add(button);
            }


            revalidate();
        }

    private Image loadIcon(String path) {
        URL url = getClass().getResource(path);

        if (url == null) {
            return null;
        }

        return new ImageIcon(url).getImage();
    }


    private void drawConnection(Graphics2D g2d, TransportConnection tc) {

        Country from = tc.getFrom();
        Country to   = tc.getTo();

        int x1 = (int) (from.getRelX() * getWidth());
        int y1 = (int) (from.getRelY() * getHeight());
        int x2 = (int) (to.getRelX()   * getWidth());
        int y2 = (int) (to.getRelY()   * getHeight());




        Color lineColor;
        float fatnessxD;

        switch (tc.getTransportType()) {
            case AIRPLANE:
                lineColor   = Color.ORANGE;
                fatnessxD = 2.0f;
                break;
            case TRAIN:
                lineColor   = Color.GREEN;
                fatnessxD = 2.5f;
                break;
            case SHIP:
                lineColor   = Color.BLUE;
                fatnessxD = 2.5f;
                break;
            case CAR:
            default:
                lineColor   = Color.GRAY;
                fatnessxD = 2.0f;
                break;
        }

        g2d.setColor(lineColor);

        g2d.setStroke(new BasicStroke(fatnessxD));
        g2d.drawLine(x1, y1, x2, y2);
    }


    private void drawMovingTransportIcon(Graphics2D g2d, TransportAnimation animation) {
        TransportConnection tc = animation.getConnection();
        double progress = animation.getProgress();

        Country from = tc.getFrom();
        Country to = tc.getTo();

        int x1 = (int) (from.getRelX() * getWidth());
        int y1 = (int) (from.getRelY() * getHeight());
        int x2 = (int) (to.getRelX()   * getWidth());
        int y2 = (int) (to.getRelY()   * getHeight());

        double x = x1 + progress * (x2 - x1);
        double y = y1 + progress * (y2 - y1);




        Image icon = switch (tc.getTransportType()) {
            case CAR -> carIcon;
            case AIRPLANE -> planeIcon;
            case TRAIN -> trainIcon;
            case SHIP -> shipIcon;
        };


        if (icon != null) {
            int iconWidth = 20;
            int iconHeight= 20;
            int drawX = (int) (x - iconWidth / 2.0);
            int drawY = (int) (y - iconHeight/ 2.0);
            g2d.drawImage(icon, drawX, drawY, iconWidth, iconHeight, this);

        }
    }
}
