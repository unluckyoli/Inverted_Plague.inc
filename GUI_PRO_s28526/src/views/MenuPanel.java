package views;

import models.Difficulty;
import models.RankingEntry;
import models.RankingManager;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private MainWindow mainWindow;




    public MenuPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridy = 0;

        JButton newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(e -> {

            Difficulty diff = askForDifficulty();
            mainWindow.showGame(diff);
        });

        JButton highScoresBtn = new JButton("High Scores");
        highScoresBtn.addActionListener(e -> {
            List<RankingEntry> list = RankingManager.loadScores();
            DefaultListModel<RankingEntry> model = new DefaultListModel<>();
            for (RankingEntry rankingEntry : list) {
                model.addElement(rankingEntry);
            }


            JList<RankingEntry> jList = new JList<>(model);
            jList.setVisibleRowCount(10);
            JScrollPane scroll = new JScrollPane(jList);

            JOptionPane.showMessageDialog(
                    mainWindow,
                    scroll,
                    "Ranking",
                    JOptionPane.PLAIN_MESSAGE
            );
        });



        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));

        add(newGameBtn, gridBagConstraints);
        gridBagConstraints.gridy++;
        add(highScoresBtn, gridBagConstraints);
        gridBagConstraints.gridy++;
        add(exitBtn, gridBagConstraints);
    }



    private Difficulty askForDifficulty() {
        Object[] options = { "EASY", "MEDIUM", "HARD" };
        int choice = JOptionPane.showOptionDialog(
                mainWindow,
                "Wybierz poziom trudnosci...",
                "Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );



        return switch (choice) {
            case 0 -> Difficulty.EASY;
            case 1 -> Difficulty.MEDIUM;
            case 2 -> Difficulty.HARD;
            default -> Difficulty.EASY;
        };
    }
}
