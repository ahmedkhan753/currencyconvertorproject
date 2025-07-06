import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class CurrencyConverterGUI {

    // Exchange rates
    private static final double USD_TO_PKR = 278.0;
    private static final double USD_TO_INR = 83.0;
    private static final double USD_TO_EUR = 0.93;
    private static final double USD_TO_GBP = 0.80;
    private static final double USD_TO_CAD = 1.37;
    private static final double USD_TO_AUD = 1.52;
    private static final double USD_TO_CNY = 7.24;
    private static final double USD_TO_SAR = 3.75;
    private static final double USD_TO_JPY = 155.0;

    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel mainPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Currency Converter App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 500);
        frame.setLocationRelativeTo(null);

        mainPanel.add(createLoginPanel(frame), "Login");
        mainPanel.add(createConverterPanel(), "Converter");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 60));

        JLabel title = new JLabel("Welcome to Currency Converter");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(100, 30, 350, 30);
        panel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.LIGHT_GRAY);
        userLabel.setBounds(120, 100, 80, 25);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(200, 100, 200, 30);
        userField.setBorder(new RoundedBorder(10));
        panel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.LIGHT_GRAY);
        passLabel.setBounds(120, 150, 80, 25);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(200, 150, 200, 30);
        passField.setBorder(new RoundedBorder(10));
        panel.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(200, 200, 100, 35);
        loginButton.setBackground(new Color(72, 133, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new RoundedBorder(15));
        panel.add(loginButton);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setBounds(100, 250, 350, 25);
        panel.add(statusLabel);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.equals("admin") && password.equals("1234")) {
                cardLayout.show(mainPanel, "Converter");
            } else {
                statusLabel.setText("Invalid login credentials. Try again.");
            }
        });

        return panel;
    }

    private static JPanel createConverterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 248, 255));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel header = new JLabel("Currency Converter");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(33, 50, 80));
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(header, gbc);

        String[] currencies = {"USD", "PKR", "INR", "EUR", "GBP", "CAD", "AUD", "CNY", "SAR", "JPY"};

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(10);
        amountField.setBorder(new RoundedBorder(10));

        JLabel fromLabel = new JLabel("From Currency:");
        JComboBox<String> fromCombo = new JComboBox<>(currencies);

        JLabel toLabel = new JLabel("To Currency:");
        JComboBox<String> toCombo = new JComboBox<>(currencies);

        JButton convertButton = new JButton("Convert");
        convertButton.setBackground(new Color(0, 128, 128));
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(false);
        convertButton.setBorder(new RoundedBorder(12));

        JLabel resultLabel = new JLabel("Result will appear here", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultLabel.setForeground(new Color(25, 100, 100));

        // Layout
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(amountLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(fromLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(fromCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panel.add(toLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(toCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(convertButton, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(15, 10, 10, 10);
        panel.add(resultLabel, gbc);

        convertButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String from = (String) fromCombo.getSelectedItem();
                String to = (String) toCombo.getSelectedItem();

                double usdAmount = convertToUSD(amount, from);
                double converted = convertFromUSD(usdAmount, to);

                resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, from, converted, to));
            } catch (Exception ex) {
                resultLabel.setText("Enter a valid number!");
            }
        });

        return panel;
    }

    // Border class for rounded fields and buttons
    static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // Conversion logic
    private static double convertToUSD(double amount, String from) {
        switch (from) {
            case "USD": return amount;
            case "PKR": return amount / USD_TO_PKR;
            case "INR": return amount / USD_TO_INR;
            case "EUR": return amount / USD_TO_EUR;
            case "GBP": return amount / USD_TO_GBP;
            case "CAD": return amount / USD_TO_CAD;
            case "AUD": return amount / USD_TO_AUD;
            case "CNY": return amount / USD_TO_CNY;
            case "SAR": return amount / USD_TO_SAR;
            case "JPY": return amount / USD_TO_JPY;
            default: return 0;
        }
    }

    private static double convertFromUSD(double usdAmount, String to) {
        switch (to) {
            case "USD": return usdAmount;
            case "PKR": return usdAmount * USD_TO_PKR;
            case "INR": return usdAmount * USD_TO_INR;
            case "EUR": return usdAmount * USD_TO_EUR;
            case "GBP": return usdAmount * USD_TO_GBP;
            case "CAD": return usdAmount * USD_TO_CAD;
            case "AUD": return usdAmount * USD_TO_AUD;
            case "CNY": return usdAmount * USD_TO_CNY;
            case "SAR": return usdAmount * USD_TO_SAR;
            case "JPY": return usdAmount * USD_TO_JPY;
            default: return 0;
        }
    }
}