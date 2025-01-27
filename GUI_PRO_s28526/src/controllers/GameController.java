package controllers;

import models.*;
import views.GamePanel;
import views.MainWindow;

import javax.swing.*;
import java.io.IOException;

public class GameController {
    private GameModel model;
    private GamePanel gamePanel;
    private Thread gameThread;
    private boolean running = false;

    public GameController(GameModel model, GamePanel gamePanel) {
        this.model = model;
        this.gamePanel = gamePanel;
    }

    public void startGame(Difficulty difficulty) {
        if (running) {
            stopGame();
        }

        model.setDifficulty(difficulty);
        model.initGameData();
        running = true;

        gameThread = new Thread(() -> {
            try {
                gameLoop();
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        });



        gameThread.start();
    }

    private void gameLoop() {

        while (running) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                break;
            }


            model.tick();


            if (model.isGameOver()) {
                SwingUtilities.invokeLater(() -> {
                    handleGameOver();
                });

                break;
            }



            SwingUtilities.invokeLater(() -> {
                if (gamePanel != null) {
                    gamePanel.updateView();
                }
            });
        }
    }




    private void handleGameOver() {

            showGameSummary();

            String playerName = JOptionPane.showInputDialog(
                    gamePanel,
                    "Podaj nick do rankingu:",
                    "Koniec gry",
                    JOptionPane.QUESTION_MESSAGE
            );
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "NONAME:(((";
            }


            boolean won = model.isGameWon();
            String result = won ? "WYGRANA" : "PRZEGRANA";

            RankingEntry entry = new RankingEntry(
                    playerName,
                    model.getSecondsElapsed(),
                    model.getScore(),
                    model.getDifficulty(),
                    result
            );


            try {
                RankingManager.saveScore(entry);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(gamePanel,
                        "blad zapisu rankingu: " + ex.getMessage(),
                        "blad zapisu rankingu", JOptionPane.ERROR_MESSAGE);
            }



            stopGame();



            if (SwingUtilities.getWindowAncestor(gamePanel) instanceof MainWindow) {
                ((MainWindow) SwingUtilities.getWindowAncestor(gamePanel)).showMenu();
            }
    }




    private void showGameSummary() {
        String bigTitle = model.isGameWon() ? "WYGRANA" : "PRZEGRANA";

        long totalPop = model.getTotalPopulation();
        long totalInf = model.getTotalInfected();
        long totalDead = model.getTotalDead();
        long totalVac = 0;
        for (Country c : model.getCountries()) {
            totalVac += c.getVaccinated();
        }

        String stats = String.format(
                "========= %s =========\n" +
                        "Populacja swiata: %d\n" +
                        "Zakazeni: %d\n" +
                        "Zaszczepieni: %d\n" +
                        "Zmarli: %d",
                bigTitle, totalPop, totalInf, totalVac, totalDead
        );


        //JLabel label = new JLabel(stats, SwingConstants.CENTER);

        JOptionPane.showMessageDialog(
                gamePanel,
                stats,
                "Koniec gry",
                JOptionPane.INFORMATION_MESSAGE
        );
    };


    public void stopGame() {
        running = false;
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}
