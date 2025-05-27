import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SymbolApp extends JFrame implements ActionListener {
    private JLabel[] symbolLabels = new JLabel[99];
    private JButton submitButton;
    private JButton previousButton; // Add this
    private String specialSymbol;
    private String selectedSymbol;
    private JTextArea instruction;
    private JPanel symbolPanel;
    private JPanel controlPanel;

    public SymbolApp() {
        // Generate a random special symbol
        Random rand = new Random();
        specialSymbol = Character.toString((char) (rand.nextInt(94) + 33));
        selectedSymbol = specialSymbol;

        setLayout(new BorderLayout());
        setSize(800, 700);
        setTitle("Symbol App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        instruction = new JTextArea(
            "Think of any two digit number. Now reverse it and find the difference of them.\n" +
            "Now find the number you got and remember the symbol from the panel below.\n" +
            "Don't tell me, I'll read your mind! Hit the below button when you are ready to see the magic!",
            5, 60);
        instruction.setEditable(false);
        instruction.setFont(new Font("Arial", Font.PLAIN, 16));
        instruction.setLineWrap(true);
        instruction.setWrapStyleWord(true); 
        add(instruction, BorderLayout.NORTH); 

        symbolPanel = new JPanel(new GridLayout(11, 9));
        for (int i = 0; i < 99; i++) {
            // All multiples of 9 (except 0) get the special symbol
            String symbol = ((i != 0) && (i % 9 == 0)) ? specialSymbol : Character.toString((char) (33 + (i % 94)));
            JLabel label = new JLabel(i + ": " + symbol, JLabel.CENTER);
            symbolLabels[i] = label;
            symbolPanel.add(label);
        }
        add(symbolPanel, BorderLayout.CENTER);

        controlPanel = new JPanel(new FlowLayout());
        submitButton = new JButton("Chin Tapak Dum Dum ");
        submitButton.setFont(new Font("Arial", Font.BOLD, 22));
        submitButton.setPreferredSize(new Dimension(320, 50));
        submitButton.setBackground(new Color(0, 102, 204));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(new RoundedBorder(25)); // Rounded border
        submitButton.addActionListener(this);
        controlPanel.add(submitButton);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Custom rounded border class
    private static class RoundedBorder extends AbstractBorder {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(new Color(0, 102, 204));
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = this.radius+1;
            insets.right = insets.bottom = this.radius;
            return insets;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            getContentPane().removeAll();
            setLayout(new BorderLayout());
            JLabel resultLabel = new JLabel(selectedSymbol, JLabel.CENTER);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 50));
            add(resultLabel, BorderLayout.CENTER);

            // Add Previous button
            previousButton = new JButton("Previous");
            previousButton.setFont(new Font("Arial", Font.PLAIN, 20));
            previousButton.setPreferredSize(new Dimension(160, 40));
            previousButton.setBackground(new Color(0, 102, 204));
            previousButton.setForeground(Color.WHITE);
            previousButton.setFocusPainted(false);
            previousButton.setBorder(new RoundedBorder(20));
            previousButton.addActionListener(this);

            JPanel bottomPanel = new JPanel(new FlowLayout());
            bottomPanel.add(previousButton);
            add(bottomPanel, BorderLayout.SOUTH);

            validate();
            repaint();
        } else if (ae.getSource() == previousButton) {
            // Generate a new special symbol
            Random rand = new Random();
            specialSymbol = Character.toString((char) (rand.nextInt(94) + 33));
            selectedSymbol = specialSymbol;

            // Update all multiples of 9 (except 0) in the symbol panel
            for (int i = 0; i < 99; i++) {
                String symbol = ((i != 0) && (i % 9 == 0)) ? specialSymbol : Character.toString((char) (33 + (i % 94)));
                symbolLabels[i].setText(i + ": " + symbol);
            }

            // Restore the original view
            getContentPane().removeAll();
            setLayout(new BorderLayout());
            add(instruction, BorderLayout.NORTH);
            add(symbolPanel, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.SOUTH);
            validate();
            repaint();
        }
    }

    public static void main(String[] args) {
        new SymbolApp();
    }
}

