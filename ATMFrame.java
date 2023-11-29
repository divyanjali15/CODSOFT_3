import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ATMFrame extends Frame implements ActionListener {

    private TextField amountField;
    private Button withdrawButton;
    private Button depositButton;
    private Button checkBalanceButton;
    private Label resultLabel;

    private ATM atm;

    public ATMFrame() {
        super("ATM");

        atm = new ATM(new BankAccount(1000.0)); // Initial balance

        // Create a label for the heading
        Label headingLabel = new Label("ATM MACHINE");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 36));

        // Create a label to instruct the user
        Label enterAmountLabel = new Label("Select your choice:");
        enterAmountLabel.setFont(new Font("Arial", Font.PLAIN,30));
        Panel amountPanel = new Panel();
        amountPanel.setLayout(new GridLayout(4, 1)); // 1 row, 2 columns
        amountPanel.add(enterAmountLabel);


        // Create the amount field
        amountField = new TextField("Enter amount", 20);
        amountField.setFont(new Font("Arial", Font.PLAIN, 24));
        amountField.setText("Enter Your Amount Here!");
        amountField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (amountField.getText().equals("Enter amount")) {
                    amountField.setText(""); // Clear the default text when the user starts typing
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (amountField.getText().isEmpty()) {
                    amountField.setText("Enter amount");
                }
            }
        });

        // Create the result label
        resultLabel = new Label("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 26));

        // Create a panel for the buttons using GridLayout
        Panel buttonPanel = new Panel(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with padding

        // Create labels for the operation buttons
        Label withdrawLabel = new Label("Withdraw");
        Label depositLabel = new Label("Deposit");
        Label checkBalanceLabel = new Label("Check Balance");

        // Create the withdraw button
        withdrawButton = new Button("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 18)); // Larger font
        withdrawButton.addActionListener(this);

        // Create the deposit button
        depositButton = new Button("Deposit");
        depositButton.setFont(new Font("Arial", Font.BOLD, 18)); // Larger font
        depositButton.addActionListener(this);

        // Create the check balance button
        checkBalanceButton = new Button("Check Balance");
        checkBalanceButton.setFont(new Font("Arial", Font.BOLD, 18)); // Larger font
        checkBalanceButton.addActionListener(this);

        // Add labels and buttons to the button panel
        
        buttonPanel.add(withdrawButton);
        buttonPanel.add(withdrawLabel);
        
      
        buttonPanel.add(depositButton);
          buttonPanel.add(depositLabel);
        
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(checkBalanceLabel);

        // Set the layout of the frame using GridLayout
        setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 column with padding

        // Add the components to the frame
        add(headingLabel);
        add(enterAmountLabel);
        add(amountField);
        add(buttonPanel);
        add(resultLabel);

        // Pack the frame and make it visible
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == withdrawButton) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                double withdrawalAmount = atm.withdraw(amount);

                if (withdrawalAmount > 0) {
                    resultLabel.setText("Withdrawal successful");
                } else {
                    resultLabel.setText("Insufficient balance.");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input.");
            } finally {
                amountField.setText("Enter amount");
            }
        } else if (source == depositButton) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                atm.deposit(amount);
                resultLabel.setText("Deposit successful");
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input.");
            } finally {
                amountField.setText("Enter amount");
            }
        } else if (source == checkBalanceButton) {
            double balance = atm.checkBalance();
            resultLabel.setText("Your balance is " + balance);
        }
    }

    public static void main(String[] args) {
        new ATMFrame();
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double withdraw(double amount) {
        if (bankAccount.getBalance() < amount) {
            System.out.println("Insufficient balance.");
            return 0.0;
        }

        bankAccount.withdraw(amount);
        System.out.println("Withdrawal successful.");
        return amount;
    }

    public void deposit(double amount) {
        bankAccount.deposit(amount);
        System.out.println("Deposit successful");
    }

    public double checkBalance() {
        return bankAccount.getBalance();
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}