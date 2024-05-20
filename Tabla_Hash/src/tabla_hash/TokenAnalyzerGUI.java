package tabla_hash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TokenAnalyzerGUI extends JFrame {
    private JTextArea textArea;
    private JButton analyzeButton;
    private JTextArea resultArea;

    public TokenAnalyzerGUI() {
        setTitle("Token Analyzer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea(10, 50);
        analyzeButton = new JButton("Analyze");
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeTokens();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(analyzeButton, BorderLayout.SOUTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.EAST);

        add(panel);
    }

    private void analyzeTokens() {
        String text = textArea.getText();
        TokenAnalyzer analyzer = new TokenAnalyzer();
        String result = analyzer.analyze(text);
        resultArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TokenAnalyzerGUI().setVisible(true);
            }
        });
    }
}

class TokenAnalyzer {
    private Map<String, String> tokenMap;

    public TokenAnalyzer() {
        tokenMap = new HashMap<>();
    }

    public String analyze(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text, " =+;\n", true);
        int row = 0;
        int col = 0;
        StringBuilder result = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.equals("\n")) {
                row++;
                col = 0;
                continue;
            }

            if (!token.trim().isEmpty()) {
                String key = "(" + row + ", " + col + ")";
                tokenMap.put(key, token);
                result.append("Key: ").append(key).append(" - Token: ").append(token).append("\n");
            }

            col += token.length();
        }

        return result.toString();
    }
}
