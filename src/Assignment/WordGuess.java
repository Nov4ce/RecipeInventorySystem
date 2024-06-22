package Assignment;
import java.awt.EventQueue;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class WordGuess {

    private JFrame frame;
    private List<List<String>> listOfWords = new ArrayList<>();
    private int level = 1;
    private String pickedWord = "";
    private String completeAnswer = "";
    private String displayedWord = "";
    private int mistakes = 0;
    private int givenLetterIndex1 = 0;
    private int givenLetterIndex2 = 0;
    JLabel lblLblword;
    private JPanel panel, panel_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WordGuess window = new WordGuess();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public WordGuess() {
        initialize();

        panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_1.setBounds(125, 36, 565, 391);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);
        panel_1.setVisible(false);

        JLabel lblWordguess = new JLabel("WordGuess 1.0");
        lblWordguess.setForeground(Color.ORANGE);
        lblWordguess.setFont(new Font("Comic Sans MS", Font.BOLD, 37));
        lblWordguess.setBounds(10, 11, 301, 52);
        panel_1.add(lblWordguess);

        JLabel lblAuthorFFF = new JLabel("Author: FFF");
        lblAuthorFFF.setBounds(20, 58, 150, 14);
        panel_1.add(lblAuthorFFF);

        JLabel label = new JLabel("2024");
        label.setBounds(20, 74, 46, 14);
        panel_1.add(label);

        JTextPane txtpnTextareasettextthisIsMy = new JTextPane();
        txtpnTextareasettextthisIsMy.setRequestFocusEnabled(false);
        txtpnTextareasettextthisIsMy.setEditable(false);
        txtpnTextareasettextthisIsMy.setText("This is my version of Hangman. Guess the word using the keyboard. There are no limitation on how many tries you can have. There are three levels and you can choose the level you want after you guessed the word correctly. Have fun!");
        txtpnTextareasettextthisIsMy.setBounds(20, 104, 521, 211);
        panel_1.add(txtpnTextareasettextthisIsMy);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(251, 326, 89, 23);
        panel_1.add(btnOk);
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel_1.setVisible(false);
                panel.setEnabled(true);
                setKeyboardVisibility(true);
            }
        });

        lblLblword = new JLabel(pickWord());
        lblLblword.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        lblLblword.setHorizontalAlignment(SwingConstants.CENTER);
        lblLblword.setBounds(10, 23, 764, 102);
        lblLblword.setFocusable(true);
        frame.getContentPane().add(lblLblword);

        panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Keyboard", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        panel.setBounds(125, 220, 565, 190);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        createKeyboardButtons();

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenuItem mntmNewMenuItem1 = new JMenuItem("About");
        mntmNewMenuItem1.setSize(50, 50);
        mntmNewMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel_1.setVisible(true);
                panel.setEnabled(false);
                setKeyboardVisibility(false);
            }
        });
        menuBar.add(mntmNewMenuItem1);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(0, 0, 800, 500);
        frame.setTitle("WordGuess 1.0");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Load words from CSV file
        loadWordsFromCSV("words.csv");
    }

    /**
     * Create keyboard buttons and add action listeners to them.
     */
    private void createKeyboardButtons() {
        // *************layer 1 ***************
        JButton btnQ = new JButton("Q");
        btnQ.setBounds(10, 15, 50, 50);
        panel.add(btnQ);
        btnQ.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnQ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('Q');
            }
        });

        JButton btnW = new JButton("W");
        btnW.setBounds(65, 15, 50, 50);
        panel.add(btnW);
        btnW.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        btnW.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('W');
            }
        });

        JButton btnE = new JButton("E");
        btnE.setBounds(120, 15, 50, 50);
        panel.add(btnE);
        btnE.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('E');
            }
        });

        JButton btnR = new JButton("R");
        btnR.setBounds(175, 15, 50, 50);
        panel.add(btnR);
        btnR.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('R');
            }
        });

        JButton btnT = new JButton("T");
        btnT.setBounds(230, 15, 50, 50);
        panel.add(btnT);
        btnT.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('T');
            }
        });

        JButton btnY = new JButton("Y");
        btnY.setBounds(285, 15, 50, 50);
        panel.add(btnY);
        btnY.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnY.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('Y');
            }
        });

        JButton btnU = new JButton("U");
        btnU.setBounds(340, 15, 50, 50);
        panel.add(btnU);
        btnU.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnU.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('U');
            }
        });

        JButton btnI = new JButton("I");
        btnI.setBounds(395, 15, 50, 50);
        panel.add(btnI);
        btnI.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('I');
            }
        });

        JButton btnO = new JButton("O");
        btnO.setBounds(450, 15, 50, 50);
        panel.add(btnO);
        btnO.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('O');
            }
        });

        JButton btnP = new JButton("P");
        btnP.setBounds(505, 15, 50, 50);
        panel.add(btnP);
        btnP.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('P');
            }
        });

        // *************layer 2 ***************
        JButton btnA = new JButton("A");
        btnA.setBounds(30, 76, 50, 50);
        panel.add(btnA);
        btnA.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('A');
            }
        });

        JButton btnS = new JButton("S");
        btnS.setBounds(85, 76, 50, 50);
        panel.add(btnS);
        btnS.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('S');
            }
        });

        JButton btnD = new JButton("D");
        btnD.setBounds(140, 76, 50, 50);
        panel.add(btnD);
        btnD.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('D');
            }
        });

        JButton btnF = new JButton("F");
        btnF.setBounds(195, 76, 50, 50);
        panel.add(btnF);
        btnF.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('F');
            }
        });

        JButton btnG = new JButton("G");
        btnG.setBounds(250, 76, 50, 50);
        panel.add(btnG);
        btnG.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('G');
            }
        });

        JButton btnH = new JButton("H");
        btnH.setBounds(305, 76, 50, 50);
        panel.add(btnH);
        btnH.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('H');
            }
        });

        JButton btnJ = new JButton("J");
        btnJ.setBounds(360, 76, 50, 50);
        panel.add(btnJ);
        btnJ.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnJ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('J');
            }
        });

        JButton btnK = new JButton("K");
        btnK.setBounds(415, 76, 50, 50);
        panel.add(btnK);
        btnK.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('K');
            }
        });

        JButton btnL = new JButton("L");
        btnL.setBounds(470, 76, 50, 50);
        panel.add(btnL);
        btnL.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('L');
            }
        });

        // *************layer 3 ***************
        JButton btnZ = new JButton("Z");
        btnZ.setBounds(85, 132, 50, 50);
        panel.add(btnZ);
        btnZ.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnZ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('Z');
            }
        });

        JButton btnX = new JButton("X");
        btnX.setBounds(140, 132, 50, 50);
        panel.add(btnX);
        btnX.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnX.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('X');
            }
        });

        JButton btnC = new JButton("C");
        btnC.setBounds(195, 132, 50, 50);
        panel.add(btnC);
        btnC.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('C');
            }
        });

        JButton btnV = new JButton("V");
        btnV.setBounds(250, 132, 50, 50);
        panel.add(btnV);
        btnV.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('V');
            }
        });

        JButton btnB = new JButton("B");
        btnB.setBounds(305, 132, 50, 50);
        panel.add(btnB);
        btnB.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('B');
            }
        });

        JButton btnN = new JButton("N");
        btnN.setBounds(360, 132, 50, 50);
        panel.add(btnN);
        btnN.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('N');
            }
        });

        JButton btnM = new JButton("M");
        btnM.setBounds(415, 132, 50, 50);
        panel.add(btnM);
        btnM.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateGuess('M');
            }
        });
    }

    private void setKeyboardVisibility(boolean isVisible) {
        for (java.awt.Component component : panel.getComponents()) {
            component.setVisible(isVisible);
        }
    }

    /**
     * Load words from CSV file into listOfWords.
     */
    private void loadWordsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                List<String> wordList = new ArrayList<>();
                for (int i = 1; i < words.length; i++) {
                    wordList.add(words[i].trim());
                }
                listOfWords.add(wordList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pick a word based on the current level.
     */
    private String pickWord() {
        Random rand = new Random();
        int wordIndex = rand.nextInt(listOfWords.get(level - 1).size());
        pickedWord = listOfWords.get(level - 1).get(wordIndex).toUpperCase();

        givenLetterIndex1 = rand.nextInt(pickedWord.length());
        givenLetterIndex2 = rand.nextInt(pickedWord.length());
        while (givenLetterIndex1 == givenLetterIndex2) {
            givenLetterIndex2 = rand.nextInt(pickedWord.length());
        }

        for (int ctr = 0; ctr < pickedWord.length(); ctr++) {
            completeAnswer = completeAnswer + pickedWord.charAt(ctr);
        }

        for (int ctr = 0; ctr < pickedWord.length(); ctr++) {
            displayedWord = displayedWord + "_ ";
        }
        displayedWord = displayedWord.substring(0, givenLetterIndex1 * 2) + pickedWord.charAt(givenLetterIndex1) + displayedWord.substring(givenLetterIndex1 * 2 + 1);
        displayedWord = displayedWord.substring(0, givenLetterIndex2 * 2) + pickedWord.charAt(givenLetterIndex2) + displayedWord.substring(givenLetterIndex2 * 2 + 1);
        return displayedWord;
    }

    /**
     * Validate the guessed letter.
     */
    private void validateGuess(char guessedLetter) {
        boolean isCorrect = false;
        String newDisplayedWord = "";
        for (int i = 0; i < pickedWord.length(); i++) {
            if (pickedWord.charAt(i) == guessedLetter) {
                newDisplayedWord += guessedLetter + " ";
                isCorrect = true;
            } else {
                newDisplayedWord += displayedWord.charAt(i * 2) + " ";
            }
        }
        displayedWord = newDisplayedWord.trim();
        lblLblword.setText(displayedWord);
        if (isCorrect) {
            if (!displayedWord.contains("_")) {
                handleChoiceSelection();
            }
        } else {
            mistakes++;
            JOptionPane.showMessageDialog(frame, "Wrong guess! Try again.");
        }
    }

    private void handleChoiceSelection() {
        Object[] options = {"4-letter word", "5-letter word", "6-letter word", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Choose Your Word:", "Word Length",
                                                  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                  null, options, options[0]);
        if (choice == 0) {
            level = 1;
        } else if (choice == 1) {
            level = 2;
        } else if (choice == 2) {
            level = 3;
        } else if (choice == 3) {
            System.exit(0);
        }
        resetGame();
    }

    /**
     * Reset the game for a new round.
     */
    private void resetGame() { 
    	pickedWord = ""; 
    	completeAnswer = ""; 
    	displayedWord = ""; 
    	mistakes = 0; 
    	lblLblword.setText(pickWord()); 
    } 
}
