
package app;

import control.Command;
import control.ExchangeCommand;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Currency;
import persistance.CurrencyListLoader;
import persistance.ExchangeRateLoader;
import persistence.file.FileCurrencyListLoader;
import persistence.file.FileExchangeRateLoader;
import view.ExchangeRateDialog;
import view.MoneyDialog;
import view.MoneyDisplay;

public class MoneyCalculator extends JFrame {
    
    public static void main(String[] args) {
        new MoneyCalculator().execute();     
    }
    private final List<Currency> currencies;
    private final CurrencyListLoader currencyListLoader;
    private final ExchangeRateLoader exchangeRateLoader;
    private MoneyDialog moneyDialog;
    private MoneyDisplay moneyDisplay;
    private ExchangeRateDialog exchangeRateDialog;
    private Command exchangeCommand;

    public MoneyCalculator() {
        this.setTitle("Money Calculator");
        this.setSize(400, 100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.currencyListLoader = new FileCurrencyListLoader("Currencies.txt");
        this.exchangeRateLoader = new FileExchangeRateLoader();              
        this.currencies = currencyListLoader.currencies();
        
        
        this.add(moneyDialog(), BorderLayout.CENTER);
        this.add(toolbar(),BorderLayout.SOUTH);
        this.add(moneyDisplay(),BorderLayout.WEST);
        this.add(exchangeRateDialog(),BorderLayout.EAST);
    }
    
    
    private void execute() {
        this.exchangeCommand = new ExchangeCommand(moneyDialog,moneyDisplay,exchangeRateDialog);
        this.setVisible(true);
    }

    private Component moneyDialog() {
        SwingMoneyDialog swingMoneyDialog = new SwingMoneyDialog(currencies);
        this.moneyDialog = swingMoneyDialog;
        return swingMoneyDialog;
    }

    private Component toolbar() {
        JPanel toolBarPanel = new JPanel();
        toolBarPanel.add(calculateButton());
        return toolBarPanel;
    }

    private Component moneyDisplay() {
        SwingMoneyDisplay swingMoneyDisplay = new SwingMoneyDisplay();
        this.moneyDisplay = swingMoneyDisplay;
        return swingMoneyDisplay;
    }
    
    private Component exchangeRateDialog() {
        SwingExchangeRateDialog swingExchangeRateDialog = new SwingExchangeRateDialog(currencies);
        this.exchangeRateDialog = swingExchangeRateDialog;
        return swingExchangeRateDialog; 
    }
    
    private JButton calculateButton() {
        JButton button = new JButton("calculate");
        button.addActionListener(calculate());
        return button;
    }

    private ActionListener calculate() {
        return new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent actionEvent){
                exchangeCommand.execute();
            }
        };
    }


}
