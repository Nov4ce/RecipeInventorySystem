package Assignment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleSolver extends JFrame {
    
    private JTextArea textArea;
    
    public PuzzleSolver() {
        // Set up the frame
        setTitle("Puzzle Solver");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a panel for the problem description and solution button
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Text area to display the problem
        JTextArea problemArea = new JTextArea();
        problemArea.setEditable(false);
        problemArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        problemArea.setText(getProblemDescription());
        problemArea.setLineWrap(true);
        problemArea.setWrapStyleWord(true);
        
        // Add the problem text area to the panel
        panel.add(new JScrollPane(problemArea), BorderLayout.CENTER);
        
        // Button to solve the problem
        JButton solveButton = new JButton("Solve the Problem");
        panel.add(solveButton, BorderLayout.SOUTH);
        
        // Add the panel to the frame
        add(panel, BorderLayout.NORTH);
        
        // Create a text area to display the solution
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // Add the text area to the frame
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        // Button action to display the solution
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(getSolution());
            }
        });
    }
    
    private String getProblemDescription() {
        return "1. The Filipino lives in the red house.\n" +
               "2. The Korean has dogs as pets.\n" +
               "3. The Indian drinks tea.\n" +
               "4. The green house is the immediate left of the white house.\n" +
               "5. The green house owner drinks coffee.\n" +
               "6. The owner who has a laptop owns a bird.\n" +
               "7. The owner of the yellow house uses Oppo.\n" +
               "8. The owner living in the center house drinks milk.\n" +
               "9. The American lives in the first house.\n" +
               "10. The one who has a desktop lives next to who owns a cat.\n" +
               "11. The owner who keeps the horse lives next to who has Oppo.\n" +
               "12. The owner who has an iPhone drinks root beer.\n" +
               "13. The German has a tablet.\n" +
               "14. The American lives next to the blue house.\n" +
               "15. The owner who owns a desktop lives next to the one who drinks water.\n" +
               "\nWho owns the fish?";
    }

    private String getSolution() {
        StringBuilder solution = new StringBuilder();

        solution.append("House 1: American, Yellow, Oppo, Drinks Water, Pet: Cat\n");
        solution.append("House 2: Indian, Blue, Desktop, Drinks Tea, Pet: Horse\n");
        solution.append("House 3: Filipino, Red, Laptop, Drinks Milk, Pet: Bird\n");
        solution.append("House 4: German, Green, Tablet, Drinks Coffee, Pet: Fish\n");
        solution.append("House 5: Korean, White, iPhone, Drinks Root Beer, Pet: Dogs\n");
        solution.append("\nThe German owns the fish.");

        return solution.toString();
    }

    public static void main(String[] args) {
        // Create and display the form
        SwingUtilities.invokeLater(() -> {
            new PuzzleSolver().setVisible(true);
        });
    }
}
