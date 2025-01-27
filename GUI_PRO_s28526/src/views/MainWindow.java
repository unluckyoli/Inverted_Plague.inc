package views;

import controllers.GameController;
import models.GameModel;
import models.Difficulty;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardsPanel;

    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    private GameModel model;
    private GameController controller;

    public MainWindow() {

        super("cni eugalP :)");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);


        model = new GameModel();
        gamePanel = new GamePanel(model, null);
        menuPanel = new MenuPanel(this);



        cardsPanel.add(menuPanel, "MENU");
        cardsPanel.add(gamePanel, "GAME");
        add(cardsPanel);



        setVisible(true);
        showMenu();
    }

    public void showMenu() {

        if (controller != null) {
            controller.stopGame();
        }

        cardLayout.show(cardsPanel, "MENU");
    }

    public void showGame(Difficulty difficulty) {
        model.initGameData();
        //System.out.println("PRZESZLISMY PRZEZ INITGAME");


        controller = new GameController(model, null);
        gamePanel = new GamePanel(model, controller);
        controller.setGamePanel(gamePanel);

        cardsPanel.add(gamePanel, "GAME");

        controller.startGame(difficulty);

        cardLayout.show(cardsPanel, "GAME");
    }

    
}